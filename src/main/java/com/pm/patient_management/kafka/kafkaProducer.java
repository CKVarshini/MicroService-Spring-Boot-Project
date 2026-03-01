package com.pm.patient_management.kafka;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.pm.patient_management.model.Patient;

import patient_events.PatientEvent;


// This class is intended to be a Kafka producer that will send messages to a Kafka topic.

@Service
public class kafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(kafkaProducer.class);
    // KafkaTemplate is a helper class that simplifies the process of sending messages to Kafka topics.
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    // Constructor to initialize the KafkaTemplate
    public kafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;

    }

    // Method to send a message to a specified Kafka topic
    public void sendEvent(Patient patinet) {
        PatientEvent event = PatientEvent.newBuilder()
        .setPatientId(patinet.getId().toString())
        .setName(patinet.getName())
        .setEmail(patinet.getEmail())
        .setEventType("PATIENT_CREATED")
        .build();

        // Convert the PatientEvent to a byte array before sending it to Kafka
        try {
            kafkaTemplate.send("patient", event.toByteArray());
            log.info("PatientCreated event sent to kafka : {} " , event);
            
        } catch (Exception e) {
            log.error("Error sending PatientCreated event to kafka : {} " , event);
        }
   }
}
