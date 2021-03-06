package org.edisoncor.gui.varios;

import java.awt.Color;
import java.awt.Font;
import java.util.Date;
/*
 * NewJPanel.java
 *
 * Created on 13 de julio de 2006, 03:19 PM
 */

/**
 *
 * @author  Edison
 */
public class ClockDigital extends javax.swing.JComponent implements Temporizador{
    
    private Timer timer;
    private Color fondo;
    
    /** Creates new form NewJPanel */
    public ClockDigital() {
        Timer tim = new Timer( this );
        initComponents();
        setHoraPrevia();
        tim.start();
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color);
        horaActual.setForeground(color);
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        horaActual.setFont(font);
    }

    @Override
    public Font getFont() {
        return horaActual.getFont();
    }


    private void setHoraPrevia(){
        Date actual = new Date();
        int horas = actual.getHours();
        int minutos = actual.getMinutes();
        int segundos = actual.getSeconds();
        int hora;
        String tiempo = "";

        // Los "ifs" que siguen son para formatear la hora
        // correctamente y que siempre ocupe lo mismo en la
        // Etiqueta, para que no salten las horas de posicion al
        // presentarse en pantalla
        if( horas > 12 ) 
            hora = horas - 12;
        else
            hora = horas;
        if( hora < 10 )
            tiempo += "0";
        tiempo += hora;
        tiempo += ":";

        if( minutos < 10 )
            tiempo += "0";
        tiempo += minutos + ":";

        if( segundos < 10 )
            tiempo += "0";
        tiempo += segundos;

        if( horas > 12 )
            tiempo += " pm";
        else
            tiempo += " am";

        horaActual.setText( tiempo );
    }
    
    public void timerIntervalo( Timer t ) {
        setHoraPrevia();
    }


    // Sobrecargamos los metodos que no nos interesan de la interface
    // Temporizador, porque solamente vamos a utilizar la que genera
    // un evento cada cierto intervalo de tiempo, las demas no tienen
    // interes en este ejemplo
    public void timerArrancado( Timer t ) { }

    public void timerParado( Timer t ) { }
      
    public void timerMuerto( Timer t ) { }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        horaActual = new javax.swing.JLabel();

        horaActual.setFont(new java.awt.Font("Microsoft Sans Serif", 1, 14)); // NOI18N
        horaActual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        horaActual.setText("La Hora");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(horaActual, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(horaActual, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel horaActual;
    // End of variables declaration//GEN-END:variables
    
}



 class Timer implements Runnable {
    private Date inicio,parada;
    private Thread thread = null;
    private int duracion = 0;
    private int duracionAnt = 0;
    private int intervalo = 1000;
    private boolean repeticion = false;
    private boolean enEjecucion = false;
    private Temporizador handler = null;

    Timer() { }

    Timer( int tiempo ) {
        setDuracion( tiempo );
        }

    Timer( Temporizador Handler ) {
        setHandler( Handler );
        }

    Timer( int tiempo,Temporizador Handler ) {
        setDuracion( tiempo );
        setHandler( Handler );
        }

    public void setDuracion( int tiempo ) {
        duracion = tiempo;
        }

    public void setHandler( Temporizador Handler ) {
        handler = Handler;
        }


    public void setIntervalo( int Intervalo ) {
        intervalo = Intervalo;
        }

    public int getDuration() {
        return( duracion );
        }

    public Temporizador getHandler() {
        return( handler );
        }

    public int getIntervalo() {
        return( intervalo );
        }


    public int getElapsed() {
         return( calculaLapso( new Date() ) );
        }


    public void resetDuracion() {
        duracion = duracionAnt;
        }


    public void start() {
        thread = new Thread( this );
        thread.start();
        }


    public void stop() {
        enEjecucion = false;
        parada = new Date();
        if ( handler != null )
            handler.timerParado( this );
        }


    public void run() {
        enEjecucion = true;
        duracionAnt = duracion;

        inicio = new Date();
        if( handler != null )
            handler.timerArrancado( this );

        while( enEjecucion )
            {
            try {
                esperar( intervalo );
            } catch( InterruptedException e ) {
                return;
                }

            if( handler != null )
                handler.timerIntervalo( this );

            if( duracion > 0 )
                {
                if( estaMuerto() )
                    {
                    if( handler != null )
                        handler.timerMuerto( this );

                    if( repeticion )
                        {
                        enEjecucion = true;
                        inicio = new Date();
                        if( handler != null )
                            handler.timerArrancado( this );
                        }
                    else
                        {
                        enEjecucion = false;
                        }
                    }
                }
            }
        }

    // Metodos que nos informan del estado del Timer
    public boolean estaCorriendo() {
        return( enEjecucion );
        }

    public boolean estaParado() {
        return( !enEjecucion );
        }


    public boolean estaMuerto() {
        int segundos = 0;

        segundos = calculaLapso( new Date() );

        if( segundos >= duracion )
            return( true );
        else
            return( false );
        }


    private int calculaLapso( Date actual ) {
        Date dfinal;
        int  segundos = 0;

        if( enEjecucion )
            dfinal = actual;
        else
            dfinal = parada;

        segundos += ( dfinal.getHours() - inicio.getHours() ) * 3600;
        segundos += ( dfinal.getMinutes() - inicio.getMinutes() ) * 60;
        segundos += ( dfinal.getSeconds() - inicio.getSeconds() );
        return( segundos );
        }

    private synchronized void esperar( int lapso )
        throws InterruptedException {
        this.wait( lapso );
        }
    }
