package com.benson.mediaapi.service.logservice;
import com.benson.mediaapi.service.logservice.enums.AlertLevel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
public class LoggerServiceImpl implements LoggerService {
    private static final Logger gcpLog = LoggerFactory.getLogger("GCPLog");
//    private static final Logger logger = LoggerFactory.getLogger("STDOUT");
    @Override
    public void error(AlertLevel alertLevel, String subject, String message) {
        JSONObject logMessage = new JSONObject();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        logMessage.put("SystemTime", formattedDateTime);
        logMessage.put("APID","123");
        logMessage.put("TraceId", UUID.randomUUID().toString().replace("-", ""));
        logMessage.put("LogKey", alertLevel.toString());
        JSONObject dataMessage = new JSONObject();
        dataMessage.put("Subject", subject);
        dataMessage.put("message", message);
        logMessage.put("Data", dataMessage);
        gcpLog.error(logMessage.toString());
//        logger.error(logMessage.toString());
    }

}
