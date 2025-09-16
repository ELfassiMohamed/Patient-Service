package com.patient_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


public class RabbitMQConfig {
	    @Value("${rabbitmq.queue.patient-activation:patient-activation-queue}")
	    private String patientActivationQueue;
	    
	    @Value("${rabbitmq.queue.medical-updates:medical-updates-queue}")
	    private String medicalUpdatesQueue;
	    
	    @Value("${rabbitmq.queue.patient-registration:patient-registration-queue}")
	    private String patientRegistrationQueue;
	    
	    // Queue declarations - Simple approach for Spring Boot 3.5.5
	    @Bean
	    public Queue patientActivationQueue() {
	        return QueueBuilder.durable(patientActivationQueue).build();
	    }
	    
	    @Bean
	    public Queue medicalUpdatesQueue() {
	        return QueueBuilder.durable(medicalUpdatesQueue).build();
	    }
	    
	    @Bean
	    public Queue patientRegistrationQueue() {
	        return QueueBuilder.durable(patientRegistrationQueue).build();
	    }
	    
	    // Exchange declaration
	    @Bean
	    public TopicExchange exchange() {
	        return ExchangeBuilder.topicExchange("patient-care-exchange").durable(true).build();
	    }
	    
	    // Bindings
	    @Bean
	    public Binding patientActivationBinding(Queue patientActivationQueue, TopicExchange exchange) {
	        return BindingBuilder.bind(patientActivationQueue).to(exchange).with("patient.activation");
	    }
	    
	    @Bean
	    public Binding medicalUpdatesBinding(Queue medicalUpdatesQueue, TopicExchange exchange) {
	        return BindingBuilder.bind(medicalUpdatesQueue).to(exchange).with("medical.updates");
	    }
	    
	    @Bean
	    public Binding patientRegistrationBinding(Queue patientRegistrationQueue, TopicExchange exchange) {
	        return BindingBuilder.bind(patientRegistrationQueue).to(exchange).with("patient.registration");
	    }
	    
	    // Message converter for JSON
	    @Bean
	    public Jackson2JsonMessageConverter messageConverter() {
	        return new Jackson2JsonMessageConverter();
	    }
	    
	    // RabbitTemplate configuration
	    @Bean
	    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
	        RabbitTemplate template = new RabbitTemplate(connectionFactory);
	        template.setMessageConverter(messageConverter());
	        return template;
	    }
}
