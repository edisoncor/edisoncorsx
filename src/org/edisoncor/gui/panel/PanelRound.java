/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;

/**
 *
 * @author edison
 */
public class PanelRound extends Panel{

   protected float anchoDeBorde=4f;
   protected Color colorDeBorde = new Color(173,173,173);
   protected Color colorDeSegundoBorde = Color.WHITE;

     @Override
    protected void paintBorder(Graphics g) {
        int x = 4, y = 4;
        int w = getWidth() - 9, h = getHeight() - 9;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setStroke(new BasicStroke(anchoDeBorde));
        g2.setColor(colorDeBorde);
        g2.drawRoundRect(x, y, w, h, h / 3, h / 3);

        g2.setStroke(new BasicStroke(1.7f));
        g2.setColor(colorDeSegundoBorde);
        g2.drawRoundRect(x + 2, y + 2, w - 4, h - 4, h / 3, h / 3);

        g2.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int x = 7, y = 7;
        int w = getWidth() - 11, h = getHeight() - 11;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);



        Paint gp = getGradientePaint();
        g2.setPaint(gp);
        g2.fillRoundRect(x, y, w - 3, h - 3, getHeight() / 3, h / 3);

        g2.setColor(getForeground());

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);


        g2.dispose();
    }

    public float getAnchoDeBorde() {
        return anchoDeBorde;
    }

    public void setAnchoDeBorde(float anchoDeBorde) {
        this.anchoDeBorde = anchoDeBorde;
    }

    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    public Color getColorDeSegundoBorde() {
        return colorDeSegundoBorde;
    }

    public void setColorDeSegundoBorde(Color colorDeSegundoBorde) {
        this.colorDeSegundoBorde = colorDeSegundoBorde;
    }
    
}
