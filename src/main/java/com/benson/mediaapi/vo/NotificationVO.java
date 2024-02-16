package com.benson.mediaapi.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class NotificationVO {

    private Message message;
    private String subscription;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }


    public static class Message {
        private Attributes attributes;
        private String data;

        @JsonProperty("messageId")
        private String messageId;
        @JsonProperty("publishTime")
        private Date publishTime;

        public Attributes getAttributes() {
            return attributes;
        }

        public void setAttributes(Attributes attributes) {
            this.attributes = attributes;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getMessageId() {
            return messageId;
        }

        public void setMessageId(String messageId) {
            this.messageId = messageId;
        }

        public Date getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(Date publishTime) {
            this.publishTime = publishTime;
        }

        public static class Attributes {

            private String bucketId;
            private Date eventTime;
            private String eventType;
            private String notificationConfig;
            private String objectGeneration;
            private String objectId;
            private String payloadFormat;

            public String getBucketId() {
                return bucketId;
            }

            public void setBucketId(String bucketId) {
                this.bucketId = bucketId;
            }

            public Date getEventTime() {
                return eventTime;
            }

            public void setEventTime(Date eventTime) {
                this.eventTime = eventTime;
            }

            public String getEventType() {
                return eventType;
            }

            public void setEventType(String eventType) {
                this.eventType = eventType;
            }

            public String getNotificationConfig() {
                return notificationConfig;
            }

            public void setNotificationConfig(String notificationConfig) {
                this.notificationConfig = notificationConfig;
            }

            public String getObjectGeneration() {
                return objectGeneration;
            }

            public void setObjectGeneration(String objectGeneration) {
                this.objectGeneration = objectGeneration;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public String getPayloadFormat() {
                return payloadFormat;
            }

            public void setPayloadFormat(String payloadFormat) {
                this.payloadFormat = payloadFormat;
            }

        }
    }
}
