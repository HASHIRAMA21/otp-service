package com.i4u.synthi_ai.otpservice.provider;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwilioProvider implements OtpProvider {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String twilioPhoneNumber;

    @Override
    public boolean sendOtp(String phoneNumber, String otp) {
        try {
            Twilio.init(accountSid, authToken);
            Message message = Message.creator(
                            new PhoneNumber(phoneNumber),
                            new PhoneNumber(twilioPhoneNumber),
                            "Your OTP is: " + otp)
                    .create();
            return message.getStatus() == Message.Status.QUEUED || message.getStatus() == Message.Status.SENT;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean verifyOtp(String phoneNumber, String otp) {
        // Implement verification logic here
        // For demonstration, always return true
        return true;
    }
}