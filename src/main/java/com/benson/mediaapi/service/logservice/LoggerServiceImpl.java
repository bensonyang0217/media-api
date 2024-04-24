package com.benson.mediaapi.service.logservice;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.benson.mediaapi.service.logservice.enums.AlertLevel;
import com.benson.mediaapi.vo.CriticalLogVO;
import com.benson.mediaapi.vo.MajorVO;
import com.benson.mediaapi.vo.MessageVO;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service
@Slf4j
public class LoggerServiceImpl implements LoggerService {
    private static final Log gcpLog = LogFactory.getLog("GCPLog");

//    private static final Log LOGGER = LogFactory.getLog(LoggerServiceImpl.class);

    @Override
    public void error(AlertLevel alertLevel, String subject, String message) {
        JSONObject logMessage = new JSONObject();
        logMessage.put("APID","123");
        logMessage.put("TraceId", UUID.randomUUID().toString().replace("-", ""));
        logMessage.put("LogKey", alertLevel.toString());
        JSONObject dataMessage = new JSONObject();
        dataMessage.put("Subject", subject);
        dataMessage.put("message", message);
        logMessage.put("Data", dataMessage);
        gcpLog.error(logMessage.toString());
//        LOGGER.error(logMessage.toString());

    }

}
