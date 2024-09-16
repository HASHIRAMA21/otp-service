package com.i4u.synthi_ai.otpservice.dto;

import lombok.Data;

@Data
public class OtpRequest {
    private String phoneNumber;
    private String otp;
    private String provider;
}