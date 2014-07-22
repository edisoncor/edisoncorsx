/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class PanelNice extends JPanel {

    private Color borderColor=Color.WHITE;
    private float anchoDeBorde=2;

    public PanelNice() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBackground(new Color(30, 30, 30, 216));
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D graphics2d = (Graphics2D) g.create();
        graphics2d.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint paint = new GradientPaint(0, 0, borderColor, 0, getHeight(), borderColor);
        Stroke oldStroke=graphics2d.getStroke();
        graphics2d.setStroke(new BasicStroke(anchoDeBorde));
        graphics2d.setPaint(paint);
        graphics2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        graphics2d.setStroke(oldStroke);
        graphics2d.dispose();
    }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D graphics2d = (Graphics2D) g.create();
            graphics2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics2d.setComposite(AlphaComposite.Src);

            graphics2d.setColor(getBackground());
            graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(),
                    16, 16);

            graphics2d.dispose();
        }

    public Shape getShape(){
        return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 16, 16);
    }

    public float getAnchoDeBorde() {
        return anchoDeBorde;
    }

    public void setAnchoDeBorde(float anchoDeBorde) {
        this.anchoDeBorde = anchoDeBorde;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

}