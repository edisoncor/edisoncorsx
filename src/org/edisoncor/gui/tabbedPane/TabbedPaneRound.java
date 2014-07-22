/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.tabbedPane;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author EdisonCor
 */
public class TabbedPaneRound extends JTabbedPane{

    private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
    protected Color colorDeBorde = new Color(173,173,173);
    protected Color colorDeSegundoBorde = Color.WHITE;

    public TabbedPaneRound() {
        setUI(new TabbedPaneUI());

    }

    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    public Color getColorDeSegundoBorde() {
        return colorDeSegundoBorde;
    }

    public void setColorDeSegundoBorde(Color colorDeSegundoBorde) {
        this.colorDeSegundoBorde = colorDeSegundoBorde;
    }

    @Override
    public void setTabPlacement(int tabPlacement) {
        if(tabPlacement==TOP | tabPlacement==BOTTOM)
            super.setTabPlacement(tabPlacement);
    }



    private class TabbedPaneUI extends BasicTabbedPaneUI{
        private int buttonHeight = 17;
        private int leftInset = 12;

        @Override
        protected void installComponents(){
            super.installComponents();
        }

        @Override
        protected void installDefaults(){
            super.installDefaults();
            tabAreaInsets.left = leftInset;
            selectedTabPadInsets = new Insets(0, 0, 0, 0);
        }

        @Override
        public int getTabRunCount(JTabbedPane pane){
            return super.getTabRunCount(pane);
        }

        @Override
        protected Insets getContentBorderInsets(int tabPlacement){
            return NO_INSETS;
        }

        @Override
        protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight){
            if (tabPlacement == tabIndex){
                    return buttonHeight;
            }
            else{
                return buttonHeight + (buttonHeight / 2) + 6;
            }
        }


        @Override
        protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics){
            return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + buttonHeight;
        }

        @Override
        protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex){
            int x=0;
            int y=0;
            int tw = tabPane.getBounds().width;
            int th = buttonHeight;
            try {
                th=rects[selectedIndex].height;
            } catch (Exception e) {
            }

            if(tabPlacement==JTabbedPane.BOTTOM){
                y=getHeight()-th;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(gradiente(x, y, th));

            g2.fillRect(x, y, tw, th);
            super.paintTabArea(g, tabPlacement, selectedIndex);
	}

        private Paint gradiente(int x, int y, int th){
            LinearGradientPaint gr  = new LinearGradientPaint(x, y, x, y+th,
                    new float[]{0f,.5f,1f},
                    new Color[]{Color.white, getBackground().brighter(),getBackground()});
            return gr;
        }

        @Override
        protected void paintTab(Graphics g,
                int tabPlacement,
                Rectangle[] rects,
                int tabIndex,
                Rectangle iconRect,
                Rectangle textRect) {
            super.paintTab(g, tabPlacement, rects, tabIndex, iconRect, textRect);
        }

        @Override
        protected void paintTabBorder(Graphics g,
                int tabPlacement,
                int tabIndex,
                int tx,
                int ty,
                int tw,
                int th,
                boolean isSelected){

            Graphics2D g2d = (Graphics2D) g;
            if(tabPlacement==JTabbedPane.TOP)
                g2d.translate(tx, 0);
            if(tabPlacement==JTabbedPane.BOTTOM)
                g2d.translate(tx, ty);

            if (isSelected){
                int w = tw - 2, h = th;
                g.setColor(getBackground());
                g.fillRoundRect(0, 0, tw - 2, th-2,h / 3,h / 3);
                int xx = 2, yy = 2;
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(4));
                g2.setColor(colorDeBorde);
                g2.drawRoundRect(xx, yy, w, h, h / 3, h / 3);
                g2.setStroke(new BasicStroke(1.7f));
                g2.setColor(colorDeSegundoBorde);
                g2.drawRoundRect(xx + 2, yy + 2, w - 4, h - 4, h / 3, h / 3);
            }
            else{
                g.setColor(getBackground());
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(colorDeSegundoBorde);
                g2.drawRoundRect(2, 2, tw-2, buttonHeight, th / 3, th / 3);
            }


            if(tabPlacement==JTabbedPane.TOP)
                g2d.translate(-1 * tx, 0);
            if(tabPlacement==JTabbedPane.BOTTOM)
                g2d.translate(-1 * tx, -1 * ty);
            
	}



        @Override
	protected void paintText(Graphics g, 
                int tabPlacement, Font font, FontMetrics metrics,
                int tabIndex, String title, Rectangle textRect,
                boolean isSelected){

            Rectangle r = rects[tabIndex];

            Graphics2D g2d = (Graphics2D) g;
            if(tabPlacement==JTabbedPane.TOP)
                g2d.translate(r.x, 0);
            if(tabPlacement==JTabbedPane.BOTTOM)
                g2d.translate(r.x, r.y);



            if (isSelected){
                FontMetrics fm = getFontMetrics();
                g.setColor(getForeground());
                g.drawString(title, (r.width / 2 - fm.stringWidth(title) / 2) + 1, buttonHeight / 2 + fm.getMaxDescent() + buttonHeight / 2 + 3);
            }
            else{
                FontMetrics fm = getFontMetrics();
                g.setColor(getForeground());
                g.drawString(title, (r.width / 2 - fm.stringWidth(title) / 2) + 1, buttonHeight / 2 + fm.getMaxDescent() + 2);
            }
            if(tabPlacement==JTabbedPane.TOP)
                g2d.translate(-1 * r.x, 0);
            if(tabPlacement==JTabbedPane.BOTTOM)
                g2d.translate(-1 * r.x, -1 * r.y);
	}

	
        @Override
	protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected)
	{
	}

        @Override
	protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected)
	{
	}

        @Override
	protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex)
	{
	}
    }

}
