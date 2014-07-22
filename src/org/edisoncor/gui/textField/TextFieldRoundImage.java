/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.textField;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Edison
 */
public class TextFieldRoundImage extends JTextField{


    private Integer angle=20;
    private Icon icon;
    private float alfa=.7f;
    protected float anchoDeBorde=2f;
    protected Color colorDeBorde = new Color(173,173,173);

    public TextFieldRoundImage() {
        setOpaque(false);
        setBorder(new EmptyBorder(0,5,0,2));
        setPreferredSize(new Dimension(69, 20));
    }

    public float getAlfa() {
        return alfa;
    }

    public void setAlfa(float alfa) {

        if(alfa>1)
            alfa=1;
        if(alfa<0)
            alfa=0;
        this.alfa = alfa;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(
                0,0,getWidth(),getHeight(),angle,angle);
        g2.clip(r2d);

        g2.setPaint(new GradientPaint(0.0f, 0.0f, getBackground(),
                0.0f, getHeight(), getBackground()));
        g2.fillRoundRect(0,0,getWidth(),getHeight(),angle,angle);

        if(icon!=null){
            Composite oldComposite = g2.getComposite();
            AlphaComposite newComposite =
                    AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alfa);
            g2.setComposite(newComposite);
            g2.drawImage(((ImageIcon)icon).getImage(), 0, 0, getWidth(), getHeight(), null);
            g2.setComposite(oldComposite);
        }

        g2.setPaint(oldPaint);
        super.paintComponent(g);

    }

    @Override
    protected void paintBorder(Graphics g) {
        int x = 1, y = 1;
        int w = getWidth() - 2, h = getHeight() - 2;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(anchoDeBorde));
        g2.setColor(colorDeBorde);
        g2.drawRoundRect(x, y, w, h, angle, angle);
        g2.dispose();
    }

  

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon){
        this.icon=icon;
        repaint();
    }

    public Integer getAngle() {
        return angle;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
        repaint();
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


}
