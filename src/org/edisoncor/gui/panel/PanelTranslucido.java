/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author edison
 */
public class PanelTranslucido extends Panel{

    protected float tran= 0.5f;
    private float valor = tran;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Composite oldComposite = g2.getComposite();
        AlphaComposite newComposite =
	    AlphaComposite.getInstance(AlphaComposite.SRC_OVER,valor);
        g2.setComposite(newComposite);
        super.paintComponent(g);
        g2.setComposite(oldComposite);
    }

    public float getTran() {
        return tran;
    }

    public void setTran(float tran) {
        this.tran = tran;
        valor=tran;
    }

    

}
