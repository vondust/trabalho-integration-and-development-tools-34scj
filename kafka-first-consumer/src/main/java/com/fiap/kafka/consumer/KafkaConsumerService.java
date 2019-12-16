package com.fiap.kafka.consumer;

import com.fiap.kafka.consumer.model.Message;
import com.fiap.kafka.consumer.model.StateDetail;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.*;

public class KafkaConsumerService {

    private static final Logger logger = Logger.getLogger(KafkaConsumerService.class);

    private KafkaConsumer<String, String> kafkaConsumer;

    public KafkaConsumerService(String topicName, Properties consumerProperties) {
        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Collections.singletonList(topicName));
    }

    private List<StateDetail> stateDetails = new ArrayList<>();
    private Timer timer = new Timer();

    public void runWorker() {
        Boolean alreadyFirstTime = Boolean.FALSE;
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {

                String message = record.value();
                logger.info("Received message: " + message);

                try {
                    Message messageObject = new Gson().fromJson(message, Message.class);
                    StateDetail stateDetail = stateDetails.stream()
                            .filter(x -> x.getState().equals(messageObject.getState()))
                            .findAny()
                            .orElse(null);

                    StateDetail detail = null;
                    if (stateDetail == null) {
                        detail = new StateDetail(
                                messageObject.getState(),
                                Double.valueOf(messageObject.getInstallmentAmount()),
                                1
                        );
                        stateDetails.add(detail);
                    } else {
                        detail = new StateDetail(
                                stateDetail.getState(),
                                stateDetail.getInstallmentSum() +
                                        Double.valueOf(messageObject.getInstallmentAmount()),
                                stateDetail.getGranteeQuantity() + 1);

                        stateDetails.set(stateDetails.indexOf(stateDetail), detail);
                    }
                    printDetail(detail);

                    logger.info("Index of deserialized JSON object: " + messageObject);
                } catch (JsonSyntaxException e) {
                    logger.error(e.getMessage());
                }
            }

            if (!alreadyFirstTime) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        String messageFormat = "|%1$-2s|%2$-17s|%3$-22s|\n";
                        String headerFormat = "|%1$-43s|\n";
                        System.out.format(headerFormat, new String(new char[43]).replace("\0", "-"));
                        System.out.format(messageFormat, "UF", "Soma das parcelas", "Total de beneficiários");
                        System.out.format(messageFormat, "--", "-----------------", "----------------------");
                        for (StateDetail stateDetail : stateDetails) {
                            System.out.format(messageFormat, stateDetail.getState(), stateDetail.getInstallmentSum(), stateDetail.getGranteeQuantity());
                        }
                    }
                }, 0, 20000);
                alreadyFirstTime = Boolean.TRUE;
            }
        }
    }

    private void printDetail(StateDetail detail) {
        System.out.println(String.format("UF: %s, Soma das parcelas: %s, Total de beneficiários: %s",
                detail.getState(), detail.getInstallmentSum(), detail.getGranteeQuantity()));
    }
}
