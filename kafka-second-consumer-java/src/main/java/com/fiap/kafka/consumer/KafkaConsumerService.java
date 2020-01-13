package com.fiap.kafka.consumer;

import com.fiap.kafka.consumer.model.GranteeDetail;
import com.fiap.kafka.consumer.model.Message;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

public class KafkaConsumerService {

    private static final Logger logger = Logger.getLogger(KafkaConsumerService.class);

    private KafkaConsumer<String, String> kafkaConsumer;

    private Long consolePeriodInSeconds;

    public KafkaConsumerService(String topicName, Properties consumerProperties,
                                Long consolePeriodInSeconds) {
        kafkaConsumer = new KafkaConsumer<>(consumerProperties);
        kafkaConsumer.subscribe(Collections.singletonList(topicName));
        this.consolePeriodInSeconds = consolePeriodInSeconds;
    }

    private GranteeDetail granteeDetail = new GranteeDetail();
    private Timer timer = new Timer();

    public void runWorker() {
        Boolean alreadyFirstTime = Boolean.FALSE;
        granteeDetail.setInstallmentAmount(0.0);

        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {

                String message = record.value();
                logger.info("Received message: " + message);

                try {
                    Message messageObject = new Gson().fromJson(message, Message.class);
                    if (Double.valueOf(messageObject.getInstallmentAmount()) > granteeDetail.getInstallmentAmount()) {
                        granteeDetail.setGranteeNumber(messageObject.getGranteeNumber());
                        granteeDetail.setGranteeName(messageObject.getGranteeName());
                        granteeDetail.setInstallmentAmount(Double.valueOf(messageObject.getInstallmentAmount()));
                        granteeDetail.setCityName(messageObject.getCityName());
                        granteeDetail.setState(messageObject.getState());
                        printDetail(granteeDetail);
                    }

                    logger.info("Index of deserialized JSON object: " + messageObject);
                } catch (JsonSyntaxException e) {
                    logger.error(e.getMessage());
                }
            }

            if (!alreadyFirstTime) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (granteeDetail.getInstallmentAmount() > 0)
                            printDetail(granteeDetail);
                    }
                }, 0, consolePeriodInSeconds * 1000);
                alreadyFirstTime = Boolean.TRUE;
            }
        }
    }

    private void printDetail(GranteeDetail detail) {
        String headerFormat = "%1$-45s\n";
        System.out.format(headerFormat, new String(new char[50]).replace("\0", "-"));
        System.out.println(String.format("NIS_FAVORECIDO: %s", detail.getGranteeNumber()));
        System.out.println(String.format("NOME_FAVORECIDO: %s", detail.getGranteeName().trim()));
        System.out.println(String.format("VALOR_PARCELA: %s", detail.getInstallmentAmount()));
        System.out.println(String.format("NOME_MUNICIPIO: %s", detail.getCityName()));
        System.out.println(String.format("UF: %s", detail.getState()));

    }
}
