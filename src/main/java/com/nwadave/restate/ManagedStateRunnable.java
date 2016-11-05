package com.nwadave.restate;

import com.nwadave.restate.exceptions.ReStateException;
import com.nwadave.restate.logging.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ManagedStateRunnable implements Runnable {
    private static LogManager log = LogManager.getManager();

    private int                         id;
    private Map<Integer,State>          states;
    private State                       initialState;
    private State                       terminalState;
    private State                       currentState;
    private Map<Integer,StateProcessor> stateProcessors;
    private List<StateObserver>         observers;


    public State getCurrentState() {
        return currentState;
    }

    public ManagedStateRunnable addState(State state, boolean isInitial, boolean isTerminal ) {
        this.states.put( state.getId(), state );
        if( isInitial ) {
            this.initialState = state;
        }
        if( isTerminal ) {
            this.terminalState = state;
        }
        return this;
    }

    public ManagedStateRunnable addStateProcessor( State forState, StateProcessor processor ) {
        if( forState == null ) {
            log.warn( "Invalid state (null) - No processor added" );
            return this;
        }

        if( this.states.get( forState.getId() ) == null ) {
            log.warn( "Invalid state (State not defined for this runnable) - No processor added" );
            return this;
        }

        if( this.stateProcessors == null ) {
            this.stateProcessors = new HashMap<>();
        }
        this.stateProcessors.put( forState.getId(), processor );

        return this;
    }

    public ManagedStateRunnable addObserver( StateObserver observer ) {
        if( this.observers == null ) {
            this.observers = new ArrayList<>();
        }

        this.observers.add( observer );

        return this;
    }

    public ManagedStateRunnable( int id ) {
        this.id              = id;
        this.states          = new HashMap<>();
        this.initialState    = null;
        this.terminalState   = null;
        this.currentState    = null;
        this.observers       = null;
        this.stateProcessors = null;
    }

    public final void run() {
        try {
            this.assertReadyToRun();
        } catch( ReStateException e ) {
            log.error( e.getMessage() );
            this.setState( States.STATE_ABORTED );
        }

        this.setState( this.initialState );
        boolean resumeStateChanged = true;
        while( true ) {
            State beforeState = this.currentState;
            if( resumeStateChanged ) {
                beforeState = this.currentState;
                StateProcessor processor = this.stateProcessors.get(this.currentState.getId());
                if( processor != null ) {
                    processor.processState(this.id, this.currentState);
                }
                if( this.currentState.equals(this.terminalState) ) {
                    log.info(this.id + " - Terminal state reached - exiting");
                    return;
                }
            }

            //If resumed because of wait interruption, beforeState MUST
            // equal currentState since no opportunity to change prior to here
            if( this.currentState.equals( beforeState ) ) {
                resumeStateChanged = true;
                try {
                    this.doWait();
                } catch( InterruptedException e ) {
                    resumeStateChanged = false;
                }
            }
        }
    }

	private synchronized void doWait() throws InterruptedException {
		this.wait();
	}

    public synchronized void setState( State newState ) {
        if( this.currentState != null ) {
            if( this.currentState.equals( newState ) ) {
                return;
            }
        }

        State oldState    = this.currentState;
        this.currentState = newState;

        this.processObservers( oldState, newState );

        this.notifyAll();
    }

    private void assertReadyToRun() throws ReStateException {
        if( this.states.isEmpty() ) {
            throw new ReStateException( "No states defined" );
        }

        if( this.initialState == null ) {
            throw new ReStateException( "No initial state specified " );
        }

        if( this.terminalState == null ) {
            throw new ReStateException( "No terminal state specified " );
        }
    }

    private void processObservers( State oldState, State newState ) {
        if( this.observers != null ) {
            for( StateObserver observer : this.observers ) {
                this.processObserver(observer, oldState, newState);
            }
        }
    }

    private void processObserver( StateObserver observer, State oldState, State newState ) {
        new Thread( () -> {
            observer.onStateChanged( ManagedStateRunnable.this.id, oldState, newState );
        } ).start();
    }
}
