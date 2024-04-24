package com.benson.mediaapi.service.logservice;

import com.benson.mediaapi.service.logservice.enums.AlertLevel;
import com.benson.mediaapi.vo.CriticalLogVO;
import com.benson.mediaapi.vo.MajorVO;
import com.benson.mediaapi.vo.MessageVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LoggerServiceImpl implements LoggerService {
    private static final Logger logger = LoggerFactory.getLogger("CriticalLog");
    private static final Logger majorLog = LoggerFactory.getLogger("MajorLog");
    private static final Logger minorLog = LoggerFactory.getLogger("MinorLog");

    @Override
    public <T> void error(T logObject, AlertLevel alertLevel) {
        switch (alertLevel) {
            case CRITICAL_ALERT:
                Optional.ofNullable(logObject)
                        .filter(CriticalLogVO.class::isInstance)
                        .map(CriticalLogVO.class::cast)
                        .ifPresent(this::logCritical);
                break;
            case MAJOR_ALERT:
                Optional.ofNullable(logObject)
                        .filter(MajorVO.class::isInstance)
                        .map(MajorVO.class::cast)
                        .ifPresent(this::logMajor);
                break;
            case MINOR_ALERT:
                Optional.ofNullable(logObject)
                        .filter(MessageVO.class::isInstance)
                        .map(MessageVO.class::cast)
                        .ifPresent(this::logMinor);
                break;
            default:
                break;
        }
    }

    @Override
    public void error(AlertLevel alertLevel, String subject, String message) {
//        MDC.put("TRACE_ID", UUID.randomUUID().toString().replace("-", ""));
//        MDC.put("LogKey", alertLevel.toString());
//        MDC.put("Subject", subject);
        Map<Object,String> logMessage = new HashMap<>();
        logMessage.put("APID","123");
        logMessage.put("TraceId", UUID.randomUUID().toString().replace("-", ""));
        logMessage.put("LOG_KEY", alertLevel.toString());
        Map<String, String> dataMessage = new HashMap<>();
        dataMessage.put("Subject", subject);
        dataMessage.put("message", message);
        logMessage.put("Data", dataMessage.toString());
        logger.error(logMessage.toString());
//        MDC.clear();
    }

    private void logCritical(CriticalLogVO vo) {
        putMDC(vo);
        logger.error(vo.getMessage());
        MDC.clear();
    }

    private void logMajor(MajorVO vo) {
        putMDC(vo);
        majorLog.error(vo.getMessage());
        MDC.clear();
    }

    private void logMinor(MessageVO vo) {
        minorLog.error(vo.getMessage());
    }

    private void putMDC(CriticalLogVO vo) {
        MDC.put("mcParameter", vo.getMcParameter());
        MDC.put("mcObject", vo.getMcObject());
        MDC.put("txnSeq", vo.getTxnSeq());
        MDC.put("subject", vo.getSubject());
        MDC.put("textContent", vo.getTextContent());
    }

    private void putMDC(MajorVO vo) {
        MDC.put("txnSeq", vo.getTxnSeq());
        MDC.put("subject", vo.getSubject());
        MDC.put("textContent", vo.getTextContent());
    }

}
