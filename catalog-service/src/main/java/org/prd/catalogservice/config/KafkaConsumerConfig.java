package org.prd.catalogservice.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.prd.catalogservice.model.dto.BookEventDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    public Map<String,Object> consumerConfig(){
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        //otra alt props.put(JsonDeserializer.TRUSTED_PACKAGES, "your.package.name");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, jsonDeserializer());

        return  properties;
    }

    //json deserializer bean
    @Bean
    public JsonDeserializer<BookEventDto> jsonDeserializer(){
        JsonDeserializer<BookEventDto> deserializer = new JsonDeserializer<>(BookEventDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    @Bean
    public ConsumerFactory<String, BookEventDto> consumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig(),new StringDeserializer(),jsonDeserializer());
    }
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, BookEventDto>> listenerContainerFactory(ConsumerFactory consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, BookEventDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }//esto es para que se ejecute el consumidor de kafka y se pueda escuchar los mensajes
}