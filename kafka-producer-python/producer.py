import csv
import os
from datetime import datetime

from kafka import KafkaProducer
from models import LineSchema


def get_brokers():
    broker = "{host}:{port}".format(host=HOST, port=PORT)
    print("Broker: {broker}".format(broker=broker))
    return broker


def get_topic():
    topic = os.getenv('TOPIC', 'integration-topic')
    print("Topic: {topic}".format(topic=topic))
    return topic


def get_timestamp():
    return datetime.now().strftime("%Y-%m-%d %H:%M:%S.%f")[:-3]


HOST = os.getenv("HOST", "localhost")
PORT = os.getenv("PORT", "9092")
TOPIC = get_topic()
BROKERS = get_brokers()


def sender(message):
    try:
        print('Sending message to kafka topic.')
        producer = KafkaProducer(bootstrap_servers=[BROKERS])
        future = producer.send(TOPIC, message.encode('utf-8'))
        record_metadata = future.get(timeout=10)

        print('Message sent successfully. For topic {topic},  partition (partition) and with offset {offset}'
              .format(topic=str(record_metadata.get('topic', '_')),
                      partition=str(record_metadata.get('partition', '_')),
                      offset=str(record_metadata.get('offset', '_'))))

    except Exception as e:
        print('Error when sending message to kafka topic.')
        print(e)
        exit()


if __name__ == '__main__':
    with open("data.csv", 'r') as file:
        csv_reader = csv.DictReader(file, delimiter=';')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                line_count += 1
            data, errors = LineSchema().load(row)
            if errors:
                print(errors)
                raise Exception(errors)

            data, errors = LineSchema().dump(data)
            if errors:
                print(errors)
                raise Exception(errors)

            print('Processing line {line} at {timestamp}'.format(line=line_count, timestamp=get_timestamp()))
            sender(data)
            line_count += 1

        print('Processed {line_count} lines.'.format(line_count=line_count - 1))

