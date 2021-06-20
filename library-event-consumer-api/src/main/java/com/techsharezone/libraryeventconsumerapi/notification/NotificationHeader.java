package com.techsharezone.libraryeventconsumerapi.notification;

/*
 * @created 20/06/2021 - 02:23
 * @project library-event-consumer-api
 * @author saurabhshcs
 */

public enum NotificationHeader {
    MESSAGE_TYPE("messageType"),
    SCHEMA_VERSION("schemaVersion"),
    TIMESTAMP("messageTimeStamp"),
    MESSAGE_ID("messageId");;

    private final String headerValue;

    NotificationHeader(String headerValue) {
        this.headerValue = headerValue;
    }

    public String getHeaderValue() {
        return headerValue;
    }
}
