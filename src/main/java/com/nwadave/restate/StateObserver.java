package com.nwadave.restate;

public interface StateObserver {
    void onStateChanged( int processorId, State oldState, State newState );
}
