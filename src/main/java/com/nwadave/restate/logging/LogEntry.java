package com.nwadave.restate.logging;

public class LogEntry {
    private long     timestamp;
    private Severity severity;
    private String   message;

    public long getTimestamp() {
        return timestamp;
    }

    public LogEntry setTimestamp( long timestamp ) {
        this.timestamp = timestamp;

        return this;
    }

    public Severity getSeverity() {
        return severity;
    }

    public LogEntry setSeverity( Severity severity ) {
        this.severity = severity;

        return this;
    }

    public String getMessage() {
        return message;
    }

    public LogEntry setMessage( String message ) {
        this.message = message;

        return this;
    }
}
