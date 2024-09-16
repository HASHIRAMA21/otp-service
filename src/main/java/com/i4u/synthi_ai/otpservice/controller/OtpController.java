package com.i4u.synthi_ai.otpservice.controller;

import com.i4u.synthi_ai.otpservice.dto.OtpRequest;
import com.i4u.synthi_ai.otpservice.dto.OtpResponse;
import com.i4u.synthi_ai.otpservice.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<OtpResponse> sendOtp(@RequestBody OtpRequest request) {
        boolean sent = otpService.sendOtp(request.getPhoneNumber(), request.getProvider());
        return ResponseEntity.ok(new OtpResponse(sent, sent ? "OTP sent successfully" : "Failed to send OTP"));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OtpResponse> verifyOtp(@RequestBody OtpRequest request) {
        boolean verified = otpService.verifyOtp(request.getPhoneNumber(), request.getOtp(), request.getProvider());
        return ResponseEntity.ok(new OtpResponse(verified, verified ? "OTP verified successfully" : "Invalid OTP"));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<OtpResponse> resendOtp(@RequestBody OtpRequest request) {
        boolean resent = otpService.resendOtp(request.getProvider());
        return ResponseEntity.ok(new OtpResponse(resent, resent ? "OTP resent successfully" : "Failed to resend OTP"));
    }
}