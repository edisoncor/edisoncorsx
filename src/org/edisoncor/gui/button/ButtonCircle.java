/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.basic.BasicButtonUI;
import org.edisoncor.gui.toolTip.ToolTipRound;
import org.edisoncor.gui.util.ColorTintFilter;

/**
 *
 * @author Edison
 */
public class ButtonCircle extends JButton{

    private Color color=getBackground();
    private Image buttonHighlight;
    private BufferedImage bg, center, bgInv, centerInv;
    private Float profundidad=.5f;
    private boolean foco=false;
    private Dimension buttonDimension= new Dimension(40, 35);

    public ButtonCircle(Icon icon) {
        super(icon);
    }

    public ButtonCircle() {
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        buttonHighlight = loadImage("/resources/header-halo.png");
        bg = (BufferedImage) loadImage("/resources/shinybutton.png");
        center = (BufferedImage) loadImage("/resources/shinybuttoncenter.png");
        bgInv = (BufferedImage) loadImage("/resources/shinybuttoninv.png");
        centerInv = (BufferedImage) loadImage("/resources/shinybuttoncenterinv.png");
        ColorTintFilter tint = new ColorTintFilter(Color.GREEN, 0.7f);
        center=tint.filter(center, center);
        centerInv=tint.filter(centerInv, centerInv);
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                foco=true;
            }

            public void focusLost(FocusEvent e) {
                foco=false;
            }
        });
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
    }

    @Override
    public JToolTip createToolTip() {
        ToolTipRound tip = new ToolTipRound();
        tip.setComponent(this);
        return tip;
    }

    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(ButtonCircle.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        center = (BufferedImage) loadImage("/resources/shinybuttoncenter.png");
        ColorTintFilter tint = new ColorTintFilter(color, profundidad);
        center=tint.filter(center, center);
        centerInv = (BufferedImage) loadImage("/resources/shinybuttoncenterinv.png");
        centerInv=tint.filter(centerInv, centerInv);
        repaint();
    }

    public Float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Float profundidad) {
        this.profundidad = profundidad;
        center = (BufferedImage) loadImage("/resources/shinybuttoncenter.png");
        ColorTintFilter tint = new ColorTintFilter(color, profundidad);
        center=tint.filter(center, center);
        centerInv = (BufferedImage) loadImage("/resources/shinybuttoncenterinv.png");
        centerInv=tint.filter(centerInv, centerInv);
        repaint();
    }

    @Override
    public void setText(String text) {
        super.setText("");
    }


    @Override
    protected void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();

        ButtonModel modelo = getModel();

        g2.drawImage(bg,
                    0,0,
                    getWidth(), getHeight(), null);
        g2.drawImage(center,
                    0,0,
                    getWidth(), getHeight(), null);
        if(modelo.isRollover() | foco){
            g2.drawImage(buttonHighlight,
                    0,0,
                    getWidth(), getHeight(), null);
        }
        if(modelo.isPressed()){
            g2.drawImage(bgInv,
                    0,0,
                    getWidth(), getHeight(), null);
            g2.drawImage(centerInv,
                    0,0,
                    getWidth(), getHeight(), null);
        }
        

        g2.setPaint(oldPaint);
        super.paintComponent(g);

    }

    




}
