/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author edison
 */
public class PanelTranslucidoComplete2 extends Panel{

    protected float tran= 0.5f;
    private float inicial = 0.5f;
    private float superior = 0.9f;
    private MouseListener ml;
    private Thread downloader;


    public PanelTranslucidoComplete2() {
        
    }
    


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        AlphaComposite newComposite =
	    AlphaComposite.getInstance(AlphaComposite.SRC_OVER,tran);
        g2.setComposite(newComposite);
        super.paintComponent(g);
    }

    public float getTran() {
        return tran;
    }

    public void setTran(float tran) {
        if(tran>=0 & tran<=1){
            this.tran = tran;
            repaint();
        }
    }

    public float getInicial() {
        return inicial;
    }

    public void setInicial(float inicial) {
        if(inicial>=0 & inicial<=1)
            this.inicial = inicial;
    }

    public float getSuperior() {
        return superior;
    }

    public void setSuperior(float superior) {
        if(superior>=0 & superior<=1)
            this.superior = superior;
    }

    

}
