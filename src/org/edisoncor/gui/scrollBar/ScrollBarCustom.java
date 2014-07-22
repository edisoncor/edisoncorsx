/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.scrollBar;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author EdisonCor
 */
public class ScrollBarCustom extends JScrollBar{

    private static Icon BUTTON_WEST;
    private static Icon BUTTON_EAST;
    private static Icon BUTTON_NORTH;
    private static Icon BUTTON_SOUTH;
    private Color color = Color.WHITE;

    static {
        BUTTON_WEST = new ImageIcon(ScrollBarCustom.class.getResource("/resources/izquierda.png"));
        BUTTON_EAST = new ImageIcon(ScrollBarCustom.class.getResource("/resources/derecha.png"));
        BUTTON_NORTH = new ImageIcon(ScrollBarCustom.class.getResource("/resources/arriba.png"));
        BUTTON_SOUTH = new ImageIcon(ScrollBarCustom.class.getResource("/resources/abajo.png"));
    }


    public ScrollBarCustom() {
        setUI(new ScrollBarUI());
        setOpaque(false);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private class ScrollBarUI extends BasicScrollBarUI{

        @Override
    protected JButton createDecreaseButton(int orientation) {
        decrButton = new ButtonDirection(orientation);
        return decrButton;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        decrButton = new ButtonDirection(orientation);
        return decrButton;
    }


    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            int width = trackBounds.width - 4;
            int height = trackBounds.height;

            g2.translate(trackBounds.x + 2, trackBounds.y);

            Rectangle2D casing = new Rectangle2D.Double(0, 0, width, height);
            g2.setColor(color);

            float alpha = 0.5f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            g2.drawLine(-1, 0, -1, height);
            g2.drawLine(-2, 0, -2, height);

            g2.drawLine(width, 0, width, height);
            g2.drawLine(width + 1, 0, width + 1, height);

            g2.translate(-trackBounds.x - 2, -trackBounds.y);
        } else {
            int width = trackBounds.width;
            int height = trackBounds.height - 4;

            g2.translate(trackBounds.x, trackBounds.y + 2);

            Rectangle2D casing = new Rectangle2D.Double(0, 0, width, height);
            g2.setColor(color);

            float alpha = 0.5f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            g2.drawLine(0, -1, width, -1);
            g2.drawLine(0, -2, width, -2);

            g2.drawLine(0, height, width, height);
            g2.drawLine(0, height + 1, width, height + 1);

            RoundRectangle2D roundCasing = new RoundRectangle2D.Double(2, 0, width - 4, height, height, height);
//            Area area = new Area(casing);
//            area.subtract(new Area(roundCasing));
//            g2.fill(area);

            g2.translate(-trackBounds.x, -trackBounds.y - 2);
        }

    }

    
    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);

        if (scrollbar.getOrientation() == JScrollBar.VERTICAL) {
            g2.translate(thumbBounds.x + 1, thumbBounds.y + 2);

            int width = thumbBounds.width - 3;
            int height = thumbBounds.height - 4;

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width, height, width, width);
            g2.setColor(color);

            float alpha = 0.7f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(casing);
            g2.setStroke(stroke);

            g2.translate(-thumbBounds.x - 1, -thumbBounds.y - 2);
        } else {
            g2.translate(thumbBounds.x + 2, thumbBounds.y + 1);

            int width = thumbBounds.width - 4;
            int height = thumbBounds.height - 3;

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width, height, height, height);
            g2.setColor(color);

            float alpha = 0.7f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            g2.draw(casing);
            g2.setStroke(stroke);

            g2.translate(-thumbBounds.x - 2, -thumbBounds.y - 1);
        }
    }


    }


    
    private class ButtonDirection extends JButton{

        private int direction;
        

        public ButtonDirection(int direction) {
            this.direction = direction;
            getInsets().set(0, 0, 0, 0);
            setOpaque(false);
            setMargin(getInsets());
            setPreferredSize(new Dimension(16, 16));
        }
        
        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
            int x=0, y=0, w=getWidth()-1, h=getHeight()-1, a=getHeight();

            if(getOrientation()==JScrollBar.HORIZONTAL){
                if(direction==BasicArrowButton.WEST){
                    BUTTON_WEST.paintIcon(null, g, 0, 0);
                }
                if(direction==BasicArrowButton.EAST){
                    BUTTON_EAST.paintIcon(null, g, 0, 0);
                }
                if(direction==BasicArrowButton.NORTH){
                    BUTTON_WEST.paintIcon(null, g, 0, 0);
                    w+=10;
                }
                if(direction==BasicArrowButton.SOUTH){
                    BUTTON_EAST.paintIcon(null, g, 0, 0);
                    x-=10;
                    w+=10;
                }
            }else{
                if(direction==BasicArrowButton.WEST){
                    BUTTON_WEST.paintIcon(null, g, 0, 0);
                }
                if(direction==BasicArrowButton.EAST){
                    BUTTON_EAST.paintIcon(null, g, 0, 0);
                }
                if(direction==BasicArrowButton.NORTH){
                    BUTTON_NORTH.paintIcon(null, g, 0, 0);
                    h+=10;
                }
                if(direction==BasicArrowButton.SOUTH){
                    BUTTON_SOUTH.paintIcon(null, g, 0, 0);
                    y-=10;
                    h+=10;
                }
            }
            RoundRectangle2D casing = new RoundRectangle2D.Double(x, y, w, h, a, a);
            g2.setColor(color);
            float alpha = 0.5f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(x, y, w, h, a, a);
    }
    
        
    }

}
