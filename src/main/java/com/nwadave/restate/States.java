package com.nwadave.restate;

public final class States {
    public static final int ID_STATE_ABORTED  =  0;
    public static final int ID_STATE_STARTING = 10;
    public static final int ID_STATE_STARTED  = 20;
    public static final int ID_STATE_PAUSING  = 30;
    public static final int ID_STATE_PAUSED   = 40;
    public static final int ID_STATE_STOPPING = 50;
    public static final int ID_STATE_STOPPED  = 60;

    public static final State STATE_ABORTED  = new State().setId( ID_STATE_ABORTED  ).setName( "ABORTED"  );
    public static final State STATE_STARTING = new State().setId( ID_STATE_STARTING ).setName( "STARTING" );
    public static final State STATE_STARTED  = new State().setId( ID_STATE_STARTED  ).setName( "STARTED"  );
    public static final State STATE_PAUSING  = new State().setId( ID_STATE_PAUSING  ).setName( "PAUSING"  );
    public static final State STATE_PAUSED   = new State().setId( ID_STATE_PAUSED   ).setName( "PAUSED"   );
    public static final State STATE_STOPPING = new State().setId( ID_STATE_STOPPING ).setName( "STOPPING" );
    public static final State STATE_STOPPED  = new State().setId( ID_STATE_STOPPED  ).setName( "STOPPED"  );
}
