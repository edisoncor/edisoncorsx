/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.toolTip;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.basic.BasicToolTipUI;

/**
 *
 * @author edison
 */
public class ToolTipRound extends JToolTip{

   protected Color colorDeBorde = new Color(173,173,173);
   private Dimension buttonDimension= new Dimension(116, 30);
   private float shadowOffsetX;
   private int distanciaDeSombra=1;
   private float shadowOffsetY;
   private Color colorDeSombra=new Color(0,0,0);
   private int direccionDeSombra=60;

   public enum Gradiente{
      HORIZONTAL,
      VERTICAL,
      ESQUINA_1,
      ESQUINA_2,
      ESQUINA_3,
      ESQUINA_4,
      CIRCULAR,
      CENTRAL
    };

    protected Gradiente gradiente= Gradiente.HORIZONTAL;

    public ToolTipRound() {
        setForeground(Color.white);
        setBackground(new Color(32,39,55));
        setOpaque(false);
        setFont(new Font("Arial", Font.BOLD, 12));
        setUI(new BasicToolTipUI() {
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

    public ToolTipRound(Color background, Color foreground) {
        setForeground(foreground);
        setBackground(background);
        setOpaque(false);
    }

     @Override
     protected void paintBorder(Graphics g) {
        int x = 0, y = 0;
        int w = getWidth() - 1, h = getHeight() - 1;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(colorDeBorde);
        g2.drawRoundRect(x, y, w, h, h / 3, h / 3);


        g2.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        computeShadow();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);



        Paint gp = getGradientePaint();
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth()-1 , getHeight()-1 , getHeight() / 3, getHeight() / 3);

        g2.setColor(getBackground());

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);


        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getTipText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();

        Insets insets = getInsets();
        int x = (int) (getWidth() - insets.left - insets.right -
                bounds.getWidth()) / 2;
        //x -= 2;
        int y = (getHeight() - insets.top - insets.bottom -
                 fm.getMaxAscent() - fm.getMaxDescent()) / 2;
        y += fm.getAscent() - 1;


        g2.setColor(colorDeSombra);
        layout.draw(g2,
                x + (int) Math.ceil(shadowOffsetX),
                y + (int) Math.ceil(shadowOffsetY));
        if(isEnabled())
            g2.setColor(getForeground());
        else
            g2.setColor(getForeground().darker());
        layout.draw(g2, x, y);
        g2.dispose();
    }


    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    public Gradiente getGradiente() {
        return gradiente;
    }

    public void setGradiente(Gradiente gradiente) {
        this.gradiente = gradiente;
    }

    public Paint getGradientePaint(){
        return new GradientPaint(0,0,getBackground(), 0, getHeight(),getBackground().darker());
    }

    private void computeShadow() {
        double rads = Math.toRadians(direccionDeSombra);
        shadowOffsetX = (float) Math.cos(rads) * distanciaDeSombra;
        shadowOffsetY = (float) Math.sin(rads) * distanciaDeSombra;
    }

    
}
