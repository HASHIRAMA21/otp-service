package com.i4u.synthi_ai.otpservice.config;

import com.i4u.synthi_ai.otpservice.provider.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class OtpConfig {

    @Bean
    public Map<String, OtpProvider> otpProviders(TwilioProvider twilioProvider,
                                                 D7Provider d7Provider,
                                                 TelesignProvider telesignProvider,
                                                 OtpSmsProvider otpSmsProvider
                                                 ) {
        return Map.of(
                "twilio", twilioProvider,
                "d7", d7Provider,
                "telesign", telesignProvider,
                "otpsms",otpSmsProvider
        );
    }
}