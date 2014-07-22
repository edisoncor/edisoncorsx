/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.button;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import org.edisoncor.gui.toolTip.ToolTipRound;
import org.edisoncor.gui.util.GraphicsUtil;
import org.edisoncor.gui.util.ShadowFactory;

/**
 *
 * @author Edison
 */
public class ButtonShadow extends JButton{

    private Image buttonHighlight;
    private float shadowOffsetX;
    private float shadowOffsetY;
    private int direccionDeSombra=60;
    private int distanciaDeSombra=1;
    private boolean vertical=true;
    private boolean foco=false;
    private ShadowFactory factory;
    private Float opacidad=1f;

    public ButtonShadow(Icon icon) {
        super(icon);
    }

    public ButtonShadow() {
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        buttonHighlight = loadImage("/resources/header-halo.png");
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                foco=true;
            }

            public void focusLost(FocusEvent e) {
                foco=false;
            }
        });
        factory = new ShadowFactory(2,1,Color.BLACK);

    }

    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(ButtonShadow.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }

    private void computeShadow() {
        double rads = Math.toRadians(direccionDeSombra);
        shadowOffsetX = (float) Math.cos(rads) * distanciaDeSombra;
        shadowOffsetY = (float) Math.sin(rads) * distanciaDeSombra;
    }

    @Override
    public JToolTip createToolTip() {
        ToolTipRound tip = new ToolTipRound();
        tip.setComponent(this);
        return tip;
    }


    @Override
    protected void paintComponent(Graphics g) {
        computeShadow();
        float x1=0,x2=0,y1=0,y2=getHeight();
        if(!vertical){
            x1=0;
            y1=0;
            x2=getWidth();
            y2=0;
        }
        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();

        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        g2.clip(rect);

        ButtonModel modelo = getModel();

        if(modelo.isArmed()|modelo.isPressed())
            g2.setPaint(new GradientPaint(x1,y1,getBackground().darker(), x2, y2, getBackground()));
        else
            g2.setPaint(new GradientPaint(x1,y1,getBackground(), x2, y2, getBackground().darker()));
       
        g2.fill(rect);

        if(modelo.isRollover() | foco){
            g2.drawRect(0, 0, getWidth(), getHeight());
            g2.drawImage(buttonHighlight,
                    0,0,
                    getWidth(), getHeight(), null);
        }


        if(getIcon()!=null){
            factory = new ShadowFactory(2, opacidad, isEnabled()?getForeground():getForeground().darker());
            BufferedImage shadow = factory.createShadow(GraphicsUtil.toBufferedImage(((ImageIcon)getIcon()).getImage()));
            g2.drawImage(shadow, 
                    (getWidth()/2) - (shadow.getWidth()/2),
                    (getHeight()/2) - (shadow.getHeight()/2),
                    shadow.getWidth(),
                    shadow.getHeight(),
                    null);
        }

        g2.setPaint(oldPaint);

    }

    public Float getOpacidad() {
        return opacidad;
    }

    public void setOpacidad(Float opacidad) {
        if(opacidad>=0 & opacidad<=1)
            this.opacidad = opacidad;
    }
  
    public int getDireccionDeSombra() {
        return direccionDeSombra;
    }

    public void setDireccionDeSombra(int direccionDeSombra) {
        this.direccionDeSombra = direccionDeSombra;
        repaint();
    }

    public int getDistanciaDeSombra() {
        return distanciaDeSombra;
    }

    public void setDistanciaDeSombra(int distanciaDeSombra) {
        this.distanciaDeSombra = distanciaDeSombra;
        repaint();
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        repaint();
    }

}
