package ru.practicum.ewm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        // Converter<Long, Category> converterToCategory = mappingContext ->
        // propertyMapper.serPostConverter(mappingContext -> {};)
        return mapper;
    }


}
