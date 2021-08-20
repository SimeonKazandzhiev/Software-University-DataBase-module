package com.example.automappingexercise.config;


import com.example.automappingexercise.models.dtos.GameAddDto;
import com.example.automappingexercise.models.entities.Game;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper mapper(){

//        ModelMapper modelMapper = new ModelMapper();
//
//        modelMapper
//                .typeMap(GameAddDto.class, Game.class)
//                .addMappings(mapper -> mapper.map(GameAddDto::getImageThumbnail,Game::setImageThumbnail));

//        Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
//            @Override
//            public LocalDate convert(MappingContext<String, LocalDate> mappingContext) {
//                return  LocalDate.parse(mappingContext.getSource(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            }
//        };
//
//        modelMapper.addConverter(localDateConverter);

        return new ModelMapper();
    }

}
