package com.ruthless.lp.Security;


import com.ruthless.lp.Service.Impl.LpUserServiceImpl;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationConfig  {

    private LpUserServiceImpl userService;

    private AuthenticationManager authenticationManager;

    //Auth manager bean
    @SneakyThrows
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
