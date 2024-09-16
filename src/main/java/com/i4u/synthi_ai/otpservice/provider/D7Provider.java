package com.i4u.synthi_ai.otpservice.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.HashMap;

@Component
public class D7Provider implements OtpProvider {

    @Value("${d7.api-key}")
    private String apiKey;

    @Value("${d7.api-host}")
    private String apiHost;

    private final RestTemplate restTemplate = new RestTemplate();

    private String lastOtpId;

    @Override
    public boolean sendOtp(String phoneNumber, String otp) {
        String url = "https://d7-verify.p.rapidapi.com/verify/v1/otp/send-otp";

        HttpEntity<Map<String, Object>> entity = getMapHttpEntity(phoneNumber);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            lastOtpId = (String) responseBody.get("otp_id");
            return true;
        }
        return false;
    }

    private HttpEntity<Map<String, Object>> getMapHttpEntity(String phoneNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-rapidapi-host", apiHost);
        headers.set("x-rapidapi-key", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("originator", "SignOTP");
        body.put("recipient", phoneNumber);
        body.put("content", "Greetings from D7 API, your mobile verification code is: {}");
        body.put("expiry", "600");
        body.put("data_coding", "text");

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        return entity;
    }

    @Override
    public boolean verifyOtp(String phoneNumber, String otp) {
        String url = "https://d7-verify.p.rapidapi.com/verify/v1/otp/verify-otp";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-rapidapi-host", apiHost);
        headers.set("x-rapidapi-key", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("otp_id", lastOtpId);
        body.put("otp_code", otp);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return "APPROVED".equals(responseBody.get("status"));
        }
        return false;
    }

    public boolean resendOtp() {
        String url = "https://d7-verify.p.rapidapi.com/verify/v1/otp/resend-otp";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-rapidapi-host", apiHost);
        headers.set("x-rapidapi-key", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("otp_id", lastOtpId);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        return response.getStatusCode().is2xxSuccessful();
    }
}