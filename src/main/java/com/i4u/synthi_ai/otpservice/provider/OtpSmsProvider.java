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
public class OtpSmsProvider implements OtpProvider {

    @Value("${otpsms.api-key}")
    private String apiKey;

    @Value("${otpsms.api-host}")
    private String apiHost;

    @Value("${otpsms.twilio-account-sid}")
    private String twilioAccountSid;

    @Value("${otpsms.twilio-auth-token}")
    private String twilioAuthToken;

    @Value("${otpsms.verify-service-sid}")
    private String verifyServiceSid;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean sendOtp(String phoneNumber, String otp) {
        String url = "https://otp-sms-verification.p.rapidapi.com/sendOtp";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-rapidapi-host", apiHost);
        headers.set("x-rapidapi-key", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("phoneNumber", phoneNumber);
        body.put("TWILIO_ACCOUNT_SID", twilioAccountSid);
        body.put("TWILIO_AUTH_TOKEN", twilioAuthToken);
        body.put("VERIFY_SERVICE_SID", verifyServiceSid);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return "pending".equals(responseBody.get("status"));
        }
        return false;
    }

    @Override
    public boolean verifyOtp(String phoneNumber, String otp) {
        String url = "https://otp-sms-verification.p.rapidapi.com/verify";

        HttpEntity<Map<String, Object>> entity = getMapHttpEntity(phoneNumber, otp);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();
            return "approved".equals(responseBody.get("status"));
        }
        return false;
    }

    private HttpEntity<Map<String, Object>> getMapHttpEntity(String phoneNumber, String otp) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("x-rapidapi-host", apiHost);
        headers.set("x-rapidapi-key", apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("phoneNumber", phoneNumber);
        body.put("otp", otp);
        body.put("TWILIO_ACCOUNT_SID", twilioAccountSid);
        body.put("TWILIO_AUTH_TOKEN", twilioAuthToken);
        body.put("VERIFY_SERVICE_SID", verifyServiceSid);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        return entity;
    }
}