/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.list;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.TextLayout;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author EdisonCor
 */
public class ListSelector extends JList {

    private Font listItemFont = new Font("Arial",Font.BOLD,16);
    private Color listColor= Color.WHITE;
    private Color listItemColor= Color.WHITE;
    private Color colorBackGround= Color.BLACK;
    private Color selectionColor= Color.WHITE;
    private float selectionOpacity=0.2f, selectionBorderOpacity=0.8f, listOpacity=0.05f,
                  listBorderOpacity=0.4f, listUnselectedItemOpacity=0.85f;
    private Color shadowColor = Color.BLACK;
    private float shadowOpacity=0.5f;
    private int shadowDistance=3;
    private int shadowDirection=60;
    private float shadowOffsetX;
    private float shadowOffsetY;
    private Font listSelectedItemFont = new Font("Arial",Font.BOLD,18);

    public ListSelector() {
        super();
        setOpaque(false);
        setFont(listItemFont);
        setCellRenderer(new SelectorsListCellRenderer());
        computeShadow();
    }

    private void computeShadow() {
        double rads = Math.toRadians(shadowDirection);
        shadowOffsetX = (float) Math.cos(rads) * shadowDistance;
        shadowOffsetY = (float) Math.sin(rads) * shadowDistance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        Composite composite = g2.getComposite();

        g2.setColor(colorBackGround);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                   listOpacity));

        

        g2.setColor(listColor);

        RoundRectangle2D background;
        background = new RoundRectangle2D.Double(13.0, 3.0,
                                                 (double) getWidth() - 26.0,
                                                 (double) getHeight() - 6.0,
                                                 10, 10);
        g2.fill(background);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                   listBorderOpacity));
        Stroke stroke = g2.getStroke();
        g2.setStroke(new BasicStroke(3.0f));
        
        g2.draw(background);
        g2.setStroke(stroke);

        g2.setComposite(composite);

        super.paintComponent(g);
    }

    public Color getColorBackGround() {
        return colorBackGround;
    }

    public void setColorBackGround(Color colorBackGround) {
        this.colorBackGround = colorBackGround;
    }
    


    public class SelectorsListCellRenderer extends DefaultListCellRenderer {

        private boolean isSelected;
        private int index;

        public  SelectorsListCellRenderer() {
            super();
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            return size;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            this.index = index;
            this.isSelected = isSelected;
            String titulo = value.toString()==null? "titulo " + index:value.toString();
            super.getListCellRendererComponent(list,
                                               titulo,
                                               index, isSelected,
                                               cellHasFocus);

            setOpaque(false);
            setForeground(listItemColor);

            int top = index == 0 ? 19 : 5;
            int left = 10;
            int bottom = index ==  - 1 ? 19 : 5;
            int right = 10;
            setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));

            if (isSelected) {
                setFont(listSelectedItemFont);
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                paintSelection(g2);
            }
            paintText(g2);
        }

      
        private void paintSelection(Graphics2D g2) {
            Composite composite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       selectionOpacity));
            g2.setColor(selectionColor);
            RoundRectangle2D background;
            double y = 2.0;
            double height = (double) getHeight() - 6.0;
            if (index == 0 || index ==  - 1) {
                height -= 14;
            }
            if (index == 0) {
                y += 14.0;
            }
            background = new RoundRectangle2D.Double(3.0, y,
                                                     (double) getWidth() - 5.0,
                                                     height,
                                                     12, 12);
            g2.fill(background);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       selectionBorderOpacity));
            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(3.0f));
            g2.draw(background);
            g2.setStroke(stroke);

            g2.setComposite(composite);
        }

        private void paintText(Graphics2D g2) {
            FontMetrics fm = getFontMetrics(getFont());
            int x = getInsets().left+10;
            int y = getInsets().top + fm.getAscent();

            Composite composite = g2.getComposite();

            if (isSelected) {
                y -= 2;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                           shadowOpacity));
            } else {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                           listUnselectedItemOpacity));
            }

            g2.setColor(shadowColor);
            TextLayout layout = new TextLayout(getText(),
                                               getFont(),
                                               g2.getFontRenderContext());
            layout.draw(g2,
                        x + (int) Math.ceil(shadowOffsetX),
                        y + (int) Math.ceil(shadowOffsetY));

            if (isSelected) {
                g2.setComposite(composite);
            }

            g2.setColor(getForeground());
            layout.draw(g2, x, y);

            if (!isSelected) {
                g2.setComposite(composite);
            }

        }
    }

}
