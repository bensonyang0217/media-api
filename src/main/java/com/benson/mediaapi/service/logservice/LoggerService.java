package com.benson.mediaapi.service.logservice;

import com.benson.mediaapi.service.logservice.enums.AlertLevel;

public interface LoggerService {
    <T> void error(T logObject, AlertLevel alertLevel);
    void error(AlertLevel alertLevel, String subject, String message);
}
