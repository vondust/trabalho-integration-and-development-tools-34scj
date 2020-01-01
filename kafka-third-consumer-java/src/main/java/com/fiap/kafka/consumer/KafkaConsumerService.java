package com.fiap.kafka.consumer;

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

    private Long count = 0L;
    private Timer timer = new Timer();

    public void runWorker() {
        Boolean alreadyFirstTime = Boolean.FALSE;
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);

            for (ConsumerRecord<String, String> record : records) {
                logger.info("Received message: " + record.value());
                count += 1;
            }

            if (!alreadyFirstTime) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        System.out.println(String.format("Total de mensagens consumidas: %s", count));
                    }
                }, 0, consolePeriodInSeconds * 1000);
                alreadyFirstTime = Boolean.TRUE;
            }
        }
    }
}
