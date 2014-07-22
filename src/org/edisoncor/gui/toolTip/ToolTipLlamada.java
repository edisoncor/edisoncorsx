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
import java.awt.Shape;
import java.awt.font.TextLayout;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.basic.BasicToolTipUI;

/**
 *
 * @author edison
 */
public class ToolTipLlamada extends JToolTip{

   protected Color colorDeBorde = new Color(173,173,173);
   private Dimension buttonDimension= new Dimension(116, 30);

    public ToolTipLlamada() {
        this(Color.white, new Color(32,39,55));
        setForeground(Color.white);
        setBackground(new Color(32,39,55));
        setOpaque(false);
    }

    public ToolTipLlamada(Color background, Color foreground) {
        setForeground(foreground);
        setBackground(background);
        setOpaque(false);
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


     @Override
     protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(colorDeBorde);
        g2.draw(getShape());


        g2.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        int x = 0, y = 0;
        int w = getWidth() - 1, h = getHeight() -1 ;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);



        Paint gp = getGradientePaint();
        g2.setPaint(gp);

        
         g2.fill(getShape());

//        g2.fillRoundRect(x, y, w , h , getHeight() / 3, h / 3);
         paintText(g2);
         g2.setColor(getBackground());
         g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                 RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
         g2.dispose();
//        super.paintComponent(g);
    }

    private void paintText(Graphics2D g2){
        Insets insets = getInsets();
        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getTipText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();

        int x = (int) (getWidth() - bounds.getWidth()) / 2;
        //x -= 2;
        int y = getHeight() / 2;


        g2.setColor(Color.BLACK);
        layout.draw(g2,
                x + (int) Math.ceil(1),
                y + (int) Math.ceil(1));

        g2.setColor(getForeground());
        layout.draw(g2, x, y);
    }


    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    

    public Paint getGradientePaint(){
        return new GradientPaint(0,0,getBackground(), 0, getHeight(),getBackground().darker());
    }

    public Shape getShape(){
        Shape shape = new RoundRectangle2D.Float(10, 0, getWidth()-11, getHeight()-1,
                                                 getHeight()/3, getHeight()/3);
        Area area = new Area(shape);
        GeneralPath path = new GeneralPath();
        path.moveTo(0, getHeight()/2);
        path.lineTo(10, (getHeight()/2)-5);
        path.lineTo(10, (getHeight()/2)+5);
        path.closePath();
        area.add(new Area(path));
        return area;
    }

}
