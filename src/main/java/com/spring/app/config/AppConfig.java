package com.spring.app.config;

import com.fasterxml.jackson.databind.ser.std.NumberSerializers;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.time.format.DateTimeFormatter;

@Configuration
public class AppConfig {

    @Bean
    @Qualifier("converter")
    public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
        var objectMapper = new Jackson2ObjectMapperBuilder()
                .serializers(new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE),
                             new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                             new NumberSerializers.LongSerializer(Long.class))
                .deserializers(new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE),
                               new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .failOnUnknownProperties(false)
                .modules(new JavaTimeModule())
                .build();
        var jsonConverter = new MappingJackson2HttpMessageConverter();
        jsonConverter.setObjectMapper(objectMapper);
        return jsonConverter;
    }
}
