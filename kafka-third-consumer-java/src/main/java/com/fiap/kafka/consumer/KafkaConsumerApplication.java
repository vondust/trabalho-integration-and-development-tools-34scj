package com.fiap.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

@SpringBootApplication
public class KafkaConsumerApplication implements CommandLineRunner {

    @Value("${kafka.topic.name}")
    private String topicName;

    @Value("${kafka.bootstrap.servers}")
    private String kafkaBootstrapServers;

    @Value("${kafka.groupId}")
    private String kafkaGroupId;

    @Value("${console.period.seconds}")
    private String consolePeriodInSeconds;

    private static final Logger logger = Logger.getLogger(KafkaConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(KafkaConsumerApplication.class, args);
    }

    @Override
    public void run(String... args) {

        Properties consumerProperties = new Properties();
        consumerProperties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBootstrapServers);
        consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        consumerProperties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
        consumerProperties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        Thread consumerThread = new Thread(() -> {
            logger.info("Starting Kafka consumer thread.");
            new KafkaConsumerService(topicName, consumerProperties,
                    Long.parseLong(consolePeriodInSeconds)).runWorker();
        });

        consumerThread.start();
    }
}
