package com.benson.mediaapi.controller;

import com.benson.mediaapi.service.kms.KmsService;
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
    private KmsService kmsService;

    @PostMapping("/encrypt")
    public String encryt(@RequestParam("text") String text) {

        String encryptedText = kmsService.encryt(text);
        return encryptedText;
    }

    @PostMapping("/decrypt")
    public String decrypt(@RequestParam("text") String encryptedText) {

        String encrypted = kmsService.decrypt(encryptedText);;
        return encrypted;
    }

}
