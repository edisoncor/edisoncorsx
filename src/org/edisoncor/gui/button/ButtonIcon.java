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
import java.awt.geom.RoundRectangle2D;
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

/**
 *
 * @author Edison
 */
public class ButtonIcon extends JButton{

    private Image buttonHighlight;
    private float shadowOffsetX;
    private float shadowOffsetY;
    private Color colorDeSombra=new Color(0,0,0);
    private int direccionDeSombra=60;
    private int distanciaDeSombra=1;
    private int angulo=20;
    private boolean vertical=true;
    private boolean foco=false;

    public ButtonIcon(Icon icon) {
        super(icon);
    }

    public ButtonIcon() {
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(new Color(255,255,255));
        buttonHighlight = loadImage("/resources/header-halo.png");
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                foco=true;
            }

            public void focusLost(FocusEvent e) {
                foco=false;
            }
        });
    }

    @Override
    public JToolTip createToolTip() {
        ToolTipRound tip = new ToolTipRound();
        tip.setComponent(this);
        return tip;
    }

    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(ButtonIcon.class.getResource(fileName));
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

        RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(
                0,0,getWidth(),getHeight(),angulo,angulo);
        g2.clip(r2d);

        ButtonModel modelo = getModel();

        RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),angulo,angulo);
        if(modelo.isArmed()|modelo.isPressed())
            g2.setPaint(new GradientPaint(x1,y1,getBackground().darker(), x2, y2, getBackground()));
        else
            g2.setPaint(new GradientPaint(x1,y1,getBackground(), x2, y2, getBackground().darker()));
       
        g2.fill(rect);


        if(getIcon()!=null){
            BufferedImage image = GraphicsUtil.toBufferedImage(((ImageIcon)getIcon()).getImage());
            g2.drawImage(image,
                    5,
                    5,
                    getWidth()-10,
                    getHeight()-10,
                    null);
        }

        if(modelo.isRollover() | foco){
            g2.drawRect(0, 0, getWidth(), getHeight());
            g2.drawImage(buttonHighlight,
                    0,0,
                    getWidth(), getHeight(), null);
        }


        g2.setPaint(oldPaint);

    }

   public Color getColorDeSombra() {
        return colorDeSombra;
    }

    public void setColorDeSombra(Color colorDeSombra) {
        this.colorDeSombra = colorDeSombra;
        repaint();
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

    public int getAngulo() {
        return angulo;
    }

    public void setAngulo(int angulo) {
        this.angulo = angulo;
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
