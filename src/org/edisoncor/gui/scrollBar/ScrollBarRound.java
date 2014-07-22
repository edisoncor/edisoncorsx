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
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 *
 * @author EdisonCor
 */
public class ScrollBarRound extends JScrollBar{

    private static Icon BUTTON_WEST;
    private static Icon BUTTON_EAST;
    private static Icon BUTTON_NORTH;
    private static Icon BUTTON_SOUTH;
    private Color color = Color.WHITE;

    static {
        BUTTON_WEST = new ImageIcon(ScrollBarRound.class.getResource("/resources/izquierda.png"));
        BUTTON_EAST = new ImageIcon(ScrollBarRound.class.getResource("/resources/derecha.png"));
        BUTTON_NORTH = new ImageIcon(ScrollBarRound.class.getResource("/resources/arriba.png"));
        BUTTON_SOUTH = new ImageIcon(ScrollBarRound.class.getResource("/resources/abajo.png"));
    }


    public ScrollBarRound() {
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
            int width = trackBounds.width - 3;
            int height = trackBounds.height;

            g2.translate(trackBounds.x + 1, trackBounds.y);

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width, height, width, width);
            g2.setColor(color);

            float alpha = 0.5f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            g2.setStroke(stroke);
            g2.drawRoundRect(0, 0, width, height-1, width, width);
           

            g2.translate(-trackBounds.x - 1, -trackBounds.y);
        } else {
            int width = trackBounds.width;
            int height = trackBounds.height - 4;

            g2.translate(trackBounds.x, trackBounds.y + 2);

            RoundRectangle2D casing = new RoundRectangle2D.Double(0, 0, width, height, height, height);
            g2.setColor(color);

            float alpha = 0.5f;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alpha *= ((AlphaComposite) composite).getAlpha();
            }
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
            g2.fill(casing);
            g2.setComposite(composite);

            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(2.0f));
            g2.setStroke(stroke);
            g2.drawRoundRect(0, 0, width-1, height, height, height);

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

        public ButtonDirection(int direction) {
            getInsets().set(0, 0, 0, 0);
            setOpaque(false);
            setMargin(getInsets());
            setPreferredSize(new Dimension(0, 0));
        }
        
    }

}
