/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.basic.BasicButtonUI;
import org.edisoncor.gui.toolTip.ToolTipRound;

/**
 *
 * @author EdisonCor
 */
public class ButtonAqua extends JButton{

    public enum Modelo{ROUND, RECT, ROUND_LEFT, ROUND_RIGHT, ROUND_TOP, ROUND_BOTTOM}

    protected Modelo modelo = Modelo.ROUND;
    private MouseAdapter ml;
    private Dimension buttonDimension= new Dimension(116, 35);
    private boolean foco=false;
    private float shadowOffsetX;
    private float shadowOffsetY;
    private Color colorDeSombra=new Color(0,0,0);
    private int direccionDeSombra=60;
    private int distanciaDeSombra=1;
    private Image buttonHighlight;

    public ButtonAqua() {
       setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
       setOpaque(false);
       setContentAreaFilled(false);
       setFocusPainted(false);
       setForeground(Color.WHITE);
       setBackground(Color.blue);
       setSize(new Dimension(242, 64));
       setPreferredSize(new Dimension(242, 64));
      
        setUI(new BasicButtonUI() {
            @Override
            public Dimension getMinimumSize(JComponent c) {
                return getPreferredSize(c);
            }

            @Override
            public Dimension getMaximumSize(JComponent c) {
                return getPreferredSize(c);
            }

            @Override
            public Dimension getPreferredSize(JComponent c) {
                Insets insets = c.getInsets();
                Dimension d = new Dimension(buttonDimension);
                d.width += insets.left + insets.right;
                d.height += insets.top + insets.bottom;
                return d;
            }
        });
        addMouseListener(ml);
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

    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(ButtonRound.class.getResource(fileName));
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

        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Paint oldPaint = g2.getPaint();

        g2.clip(getShape(0));

        ButtonModel buttonModel = getModel();

        if(buttonModel.isArmed()|buttonModel.isPressed()){
            g2.setPaint(gradienteInv());
            g2.fill(getShape(0));
            g2.setPaint(gradiente2Inv());
            g2.fill(getShape(1));
        }
        else{
            g2.setPaint(gradiente1());
            g2.fill(getShape(0));
            g2.setPaint(gradiente2());
            g2.fill(getShape(1));
        }

        
        if(buttonModel.isRollover() | foco){
            g2.drawImage(buttonHighlight,
                    0,3,
                    getWidth(), getHeight()-3, null);
        }

        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();

        int x = (int) (getWidth() - bounds.getWidth()) / 2;
        int y = (getHeight() -  fm.getMaxAscent() - fm.getMaxDescent()) / 2;
        y += fm.getAscent() - 1;

        if (buttonModel.isArmed()) {
            x += 1;
            y += 1;
        }

        g2.setColor(colorDeSombra);
        layout.draw(g2,
                x + (int) Math.ceil(shadowOffsetX),
                y + (int) Math.ceil(shadowOffsetY));
        if(isEnabled())
            g2.setColor(getForeground());
        else
            g2.setColor(getForeground().darker());
        layout.draw(g2, x, y);


        g2.setPaint(oldPaint);

    }


    protected Shape getShape(int v){
        switch (modelo){
            case RECT: return  new Rectangle2D.Float(
                0+v,0+v,getWidth()-v-v,getHeight()-v-v);
            case ROUND: return new RoundRectangle2D.Float(
                0+v, 0+v, getWidth()-v-v, getHeight()-v-v, getHeight()-v, getHeight()-v);
            case ROUND_BOTTOM :{
                return new RoundRectangle2D.Float(
                        0+v, 0-30+v, getWidth()-v-v, getHeight()+30-v-v, getHeight()-v, getHeight()-v);
            }
            case ROUND_LEFT :{
                return new RoundRectangle2D.Float(
                        0+v, 0+v, getWidth()+30-v-v, getHeight()-v-v, getHeight()-v, getHeight()-v);
            }
            case ROUND_TOP :{
                return new RoundRectangle2D.Float(
                        0+v, 0+v, getWidth()-v-v, getHeight()+30-v-v, getHeight()-v, getHeight()-v);
            }
            case ROUND_RIGHT :{
                return new RoundRectangle2D.Float(
                        0-30+v, 0+v, getWidth()+30-v-v, getHeight()-v-v, getHeight()-v, getHeight()-v);
            }
            default:return  new Rectangle2D.Float(
                0,0,getWidth(),getHeight());
        }
    }

    protected Paint gradiente1(){
        LinearGradientPaint gr  = new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0f,1f},
                new Color[]{ getBackground().darker(),getBackground().brighter()});
        return gr;
    }

    private Paint gradiente2(){
            LinearGradientPaint gr  = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0f,.5f,1f},
                    new Color[]{Color.white, getBackground().brighter(),getBackground().brighter().brighter()});
            return gr;

    }
    protected Paint gradienteInv(){
        LinearGradientPaint gr  = new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0f,1f},
                new Color[]{ getBackground().brighter(),getBackground().darker()});
        return gr;
    }

    private Paint gradiente2Inv(){
            LinearGradientPaint gr  = new LinearGradientPaint(0, 0, 0, getHeight(),
                    new float[]{0f,.5f,1f},
                    new Color[]{getBackground().brighter().brighter(),
                    getBackground().brighter(),
                    Color.white});
            return gr;

    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }


}
