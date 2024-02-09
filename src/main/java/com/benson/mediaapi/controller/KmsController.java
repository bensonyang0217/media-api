package com.benson.mediaapi.controller;

import com.google.cloud.spring.kms.KmsTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@RestController
@RequestMapping("/api/test")
public class KmsController {
    @Autowired
    private KmsTemplate kmsTemplate;

    @Value("${kms-project-id}")
    private String keyId;

    @PostMapping("/encrypt")
    public String encryt(@RequestParam("text") String text) {

        byte[] encryptedBytes = kmsTemplate.encryptText(keyId, text);
        String encryptedText = encodeBase64(encryptedBytes);
        return encryptedText;
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestParam("text") String encryptedText) {

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
