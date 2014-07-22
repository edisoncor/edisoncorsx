package org.edisoncor.gui.varios;



public interface Temporizador {
    public void timerArrancado( Timer timer );   
    public void timerParado( Timer timer );   
    public void timerMuerto( Timer timer );
    public void timerIntervalo( Timer timer );
    }

