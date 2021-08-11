package com.bank.transaction;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import proto.schema.TransactionProto;

import java.util.Properties;

public class TransactionProducer {
    public static void main(String args[]){
//        System.out.println("Hello There");

        Properties kafkaProps = new Properties();
      kafkaProps.put("bootstrap.servers", "localhost:9092");
//        kafkaProps.put("bootstrap.servers", "broker:9092");
//        kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
//        kafkaProps.put("value.serializer", "io.confluent.kafka.serializers.protobuf.KafkaProtobufSerializer");

        kafkaProps.put("key.serializer","org.apache.kafka.common.serialization.ByteArraySerializer");
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        kafkaProps.put("schema.registry.url", "http://localhost:8081");
//        kafkaProps.put("schema.registry.url", "http://schema-registry:8081");
//        KafkaProducer<String, TransactionProto.Transaction> producer = new KafkaProducer<String, TransactionProto.Transaction>(kafkaProps);
        KafkaProducer<byte[], byte[]> producer = new KafkaProducer<byte[], byte[]>(kafkaProps);



        System.out.println("******** Starting producer ********");

        try {
            while(true) {

                RandomTransactionGenerator rtg = new RandomTransactionGenerator();

                TransactionProto.Transaction ATMTransaction = TransactionProto.Transaction.newBuilder()
                        .setAmount(rtg.getAmount())
                        .setAtmMachineId(rtg.getAtm_machine_id())
                        .setCustomerId(rtg.getCustomer_id())
                        .setEventTimestamp(rtg.getEvent_timestamp()).build();

                System.out.println("Producer:   " + rtg.toString());


                byte[] keyArr = "ATMTransactions".getBytes();

                ProducerRecord<byte[], byte[]> record = new ProducerRecord<byte[], byte[]>("ATMTransactions" , keyArr, ATMTransaction.toByteArray());
//                ProducerRecord<String, TransactionProto.Transaction> record = new ProducerRecord<String, TransactionProto.Transaction>("ATMTransactions".getBytes(), ATMTransaction.toByteArray());
                producer.send(record);

                Thread.sleep(100);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
