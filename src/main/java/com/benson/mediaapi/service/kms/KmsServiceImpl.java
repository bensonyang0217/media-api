package com.benson.mediaapi.service.kms;
import com.google.cloud.spring.kms.KmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class KmsServiceImpl implements KmsService{
    @Autowired
    private KmsTemplate kmsTemplate;

    @Value("${kms-project-id}")
    private String keyId;
    @Override
    public String encrypt(String text) {
        byte[] encryptedBytes = kmsTemplate.encryptText(keyId, text);
        String encryptedText = encodeBase64(encryptedBytes);
        return encryptedText;
    }

    @Override
    public String decrypt(String encryptedText) {
        byte[] encryptedBytes = decodeBase64(encryptedText);
        String encrypted = kmsTemplate.decryptText(keyId, encryptedBytes);
        return encrypted;
    }

    private String encodeBase64(byte[] bytes) {
        byte[] encoded = Base64.getEncoder().encode(bytes);
        return new String(encoded);
    }

    private byte[] decodeBase64(String encryptedText) {
        byte[] bytes = encryptedText.getBytes();
        return Base64.getDecoder().decode(bytes);
    }
}
