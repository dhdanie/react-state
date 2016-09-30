package com.nwadave.restate.logging;

import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private static LogManager manager;

    public static LogManager getManager() {
        if( manager == null ) {
            manager = new LogManager();
        }
        return manager;
    }

    private List<LogEntry> entries;

    private LogManager() {
        this.entries = new ArrayList<>();
    }

    public void error( Object entry ) {
        this.addEntry( Severity.ERROR, entry );
    }

    public void warn( Object entry ) {
        this.addEntry( Severity.WARNING, entry );
    }

    public void info( Object entry ) {
        this.addEntry( Severity.INFO, entry );
    }

    public void debug( Object entry ) {
        this.addEntry( Severity.DEBUG, entry );
    }

    public void trace( Object entry ) {
        this.addEntry( Severity.TRACE, entry );
    }

    private void addEntry( Severity severity, Object entry ) {
        this.entries.add( new LogEntry().setTimestamp( System.currentTimeMillis() ).setSeverity( severity ).setMessage( entry.toString() ) );
    }
}
