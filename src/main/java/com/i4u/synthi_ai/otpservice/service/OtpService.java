package com.i4u.synthi_ai.otpservice.service;


public interface OtpService {
    boolean sendOtp(String phoneNumber, String providerName);
    boolean verifyOtp(String phoneNumber, String otp, String providerName);
    boolean resendOtp(String providerName);
}