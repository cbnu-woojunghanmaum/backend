#!/bin/bash

echo "Starting Zookeeper"

# start zookeeper
docker compose -f common.yml -f zookeeper.yml up -d

# check zookeeper health
zookeeperCheckResult=$(echo ruok | nc localhost 2181)

#while [[ ! $zookeeperCheckResult == "imok" ]]; do
#  >&2 echo "Zookeeper is not running yet!"
#  sleep 2
#  zookeeperCheckResult=$(echo ruok | nc localhost 2181)
#done
sleep 10

echo "Starting Kafka cluster"

# start kafka
docker compose -f common.yml -f kafka_cluster.yml up -d

# check kafka health
kafkaCheckResult=$(kcat -L -b localhost:19092 | grep '3 brokers:')

while [[ ! $kafkaCheckResult == " 3 brokers:" ]]; do
  >&2 echo "Kafka cluster is not running yet!"
  sleep 2
  kafkaCheckResult=$(kcat -L -b localhost:19092 | grep '3 brokers:')
done

echo "Creating Kafka topics"

# start kafka init
docker compose -f common.yml -f init_kafka.yml up -d

echo "Start MySQL and MongoDB"
docker compose -f common.yml -f docker-compose.yml up -d

echo "Start-up completed"
