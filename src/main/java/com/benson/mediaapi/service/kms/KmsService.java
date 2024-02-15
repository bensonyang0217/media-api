package com.benson.mediaapi.service.kms;

import org.springframework.web.bind.annotation.RequestParam;

public interface KmsService {
    String encryt(String text);

    String decrypt(String encryptedText);
}
