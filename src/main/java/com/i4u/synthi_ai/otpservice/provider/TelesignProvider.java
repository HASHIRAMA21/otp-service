package com.i4u.synthi_ai.otpservice.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TelesignProvider implements OtpProvider {

    @Value("${telesign.customer-id}")
    private String customerId;

    @Value("${telesign.api-key}")
    private String apiKey;

    @Override
    public boolean sendOtp(String phoneNumber, String otp) {
        // Implement Telesign API call here
        // This is a placeholder implementation
        System.out.println("Sending OTP via Telesign: " + otp + " to " + phoneNumber);
        return true;
    }

    @Override
    public boolean verifyOtp(String phoneNumber, String otp) {
        // Implement verification logic here
        // For demonstration, always return true
        return true;
    }
}