package com.nwadave.restate;

import java.io.Serializable;

public class State implements Serializable {
    private int    id;
    private String name;

    public int getId() {
        return id;
    }

    public State setId( int id ) {
        this.id = id;

        return this;
    }

    public String getName() {
        return name;
    }

    public State setName( String name ) {
        this.name = name;

        return this;
    }

    public boolean equals( Object other ) {
        if( other == null ) {
            return false;
        }

        if( other instanceof State ) {
            if( ((State)other).getId() == this.getId() ) {
                return true;
            }
        }
        return false;
    }
}
