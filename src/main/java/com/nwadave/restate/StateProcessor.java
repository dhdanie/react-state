package com.nwadave.restate;

public interface StateProcessor {
    void processState( int managedStateRunnableId, State state );
}
