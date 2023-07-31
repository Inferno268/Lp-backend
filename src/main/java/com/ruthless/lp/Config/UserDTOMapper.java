package com.ruthless.lp.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDTOMapper {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
