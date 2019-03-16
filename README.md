# Kafva
To create Kafka producer consumer with java

## Kafka step by step 
* Run zookeeper (from kakfa installation directory
* bin/zookeeper-server-start.sh config/zookeeper.properties
* Start kafka server
* bin/kafka-server-start.sh config/server.properties
* Create a topic
* bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
* List the topics
* bin/kafka-topics.sh --list --zookeeper localhost:2181
* Run the producer and then type a few messages into the console to send to the server.
* bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
* Run the consumer to get the produced message
* bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning

