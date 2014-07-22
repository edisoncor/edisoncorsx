/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author EdisonCor
 */
public class PanelLlamada extends Panel{

    protected Color colorDeBorde = new Color(173,173,173);

    public enum Orientacion{
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
    protected Orientacion orientacion= Orientacion.LEFT;
    protected int distancia = 10;
    protected int ancho = 10;
    private float anchoDeBorde=1f;
    
    public PanelLlamada() {
        setOpaque(false);
    }

    @Override
     protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(anchoDeBorde));
        g2.setColor(colorDeBorde);
        g2.draw(getShape());
        g2.dispose();
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
         g2.setPaint(getGradientePaint());
         g2.fill(getShape());
    }


   public Shape getShape(){
        switch (orientacion){
            case LEFT:{
                Shape shape = new RoundRectangle2D.Float(distancia, 0,
                        getWidth()-distancia-1, getHeight()-1,
                        getHeight()/3, getHeight()/3);
                Area area = new Area(shape);
                GeneralPath path = new GeneralPath();
                path.moveTo(0, getHeight()/2);
                path.lineTo(distancia, (getHeight()/2)-ancho);
                path.lineTo(distancia, (getHeight()/2)+ancho);
                path.closePath();
                area.add(new Area(path));
                return area;
            }
            case RIGHT:{
                Shape shape = new RoundRectangle2D.Float(0, 0,
                        getWidth()-distancia, getHeight()-1,
                        getHeight()/3, getHeight()/3);
                Area area = new Area(shape);
                GeneralPath path = new GeneralPath();
                path.moveTo(getWidth(), getHeight()/2);
                path.lineTo(getWidth()-distancia, (getHeight()/2)-ancho);
                path.lineTo(getWidth()-distancia, (getHeight()/2)+ancho);
                path.closePath();
                area.add(new Area(path));
                return area;
            }
            case BOTTOM:{
                Shape shape = new RoundRectangle2D.Float(0, 0,
                        getWidth()-1, getHeight()-distancia,
                        getHeight()/3, getHeight()/3);
                Area area = new Area(shape);
                GeneralPath path = new GeneralPath();
                path.moveTo(getWidth()/2, getHeight());
                path.lineTo(getWidth()/2-ancho, getHeight()-distancia);
                path.lineTo(getWidth()/2+ancho, getHeight()-distancia);
                path.closePath();
                area.add(new Area(path));
                return area;
            }
            case TOP:{
                Shape shape = new RoundRectangle2D.Float(0, distancia,
                        getWidth()-1, getHeight()-distancia-1,
                        getHeight()/3, getHeight()/3);
                Area area = new Area(shape);
                GeneralPath path = new GeneralPath();
                path.moveTo(getWidth()/2, 0);
                path.lineTo(getWidth()/2-ancho, distancia);
                path.lineTo(getWidth()/2+ancho, distancia);
                path.closePath();
                area.add(new Area(path));
                return area;
            }
            default:{
                Shape shape = new RoundRectangle2D.Float(distancia, 0,
                        getWidth()-distancia, getHeight(),
                        getHeight()/3, getHeight()/3);
                Area area = new Area(shape);
                GeneralPath path = new GeneralPath();
                path.moveTo(0, getHeight()/2);
                path.lineTo(distancia, (getHeight()/2)-ancho);
                path.lineTo(distancia, (getHeight()/2)+ancho);
                path.closePath();
                area.add(new Area(path));
                return area;
            }
        }
        
    }

    public int getAncho() {
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Orientacion getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(Orientacion orientacion) {
        this.orientacion = orientacion;
    }

    public float getAnchoDeBorde() {
        return anchoDeBorde;
    }

    public void setAnchoDeBorde(float anchoDeBorde) {
        this.anchoDeBorde = anchoDeBorde;
    }

}

