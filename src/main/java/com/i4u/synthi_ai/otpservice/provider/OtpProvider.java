package com.i4u.synthi_ai.otpservice.provider;

public interface OtpProvider {
    boolean sendOtp(String phoneNumber, String otp);
    boolean verifyOtp(String phoneNumber, String otp);
}