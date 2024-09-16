package com.i4u.synthi_ai.otpservice.service;

import com.i4u.synthi_ai.otpservice.provider.D7Provider;
import com.i4u.synthi_ai.otpservice.provider.OtpProvider;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class OtpServiceImpl implements OtpService {

    private final Map<String, OtpProvider> providers;

    public OtpServiceImpl(Map<String, OtpProvider> providers) {
        this.providers = providers;
    }

    @Override
    public boolean sendOtp(String phoneNumber, String providerName) {
        OtpProvider provider = providers.get(providerName);
        if (provider == null) {
            throw new IllegalArgumentException("Invalid provider name: " + providerName);
        }
        return provider.sendOtp(phoneNumber, null);
    }

    @Override
    public boolean verifyOtp(String phoneNumber, String otp, String providerName) {
        OtpProvider provider = providers.get(providerName);
        if (provider == null) {
            throw new IllegalArgumentException("Invalid provider name: " + providerName);
        }
        return provider.verifyOtp(phoneNumber, otp);
    }

    @Override
    public boolean resendOtp(String providerName) {
        OtpProvider provider = providers.get(providerName);
        if (provider == null) {
            throw new IllegalArgumentException("Invalid provider name: " + providerName);
        }
        if (provider instanceof D7Provider) {
            return ((D7Provider) provider).resendOtp();
        }
        throw new UnsupportedOperationException("Resend OTP not supported for provider: " + providerName);
    }
}