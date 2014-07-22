/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.tabbedPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 *
 * @author EdisonCor
 */
public class TabbedPaneHeader extends JTabbedPane{

    private static final Insets NO_INSETS = new Insets(0, 0, 0, 0);
    protected Color colorDeSombra = new Color(173,173,173);
    protected BufferedImage left, right, buttonHighlight, background;

    public TabbedPaneHeader() {
        left=loadImage("/resources/header-slash-left.png");
        right=loadImage("/resources/header-slash-right.png");
        buttonHighlight = loadImage("/resources/header-halo.png");
        background = loadImage("/resources/header-gradient.png");
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        setUI(new TabbedPaneUI());
    }

    private static BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(TabbedPaneHeader.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }

    public Color getColorDeBorde() {
        return colorDeSombra;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeSombra = colorDeBorde;
    }


    @Override
    public void setTabPlacement(int tabPlacement) {
        if(tabPlacement==TOP | tabPlacement==BOTTOM)
            super.setTabPlacement(tabPlacement);
    }



    private class TabbedPaneUI extends BasicTabbedPaneUI{
        private int buttonHeight = background.getHeight();
        private int leftInset = 0;

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
                return buttonHeight;
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
            g2.drawImage(background, x, y, tw, th, null);
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

            int count = getTabCount();
            Graphics2D g2d = (Graphics2D) g;
            if(tabPlacement==JTabbedPane.TOP)
                g2d.translate(tx, 0);
            if(tabPlacement==JTabbedPane.BOTTOM)
                g2d.translate(tx, ty);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.drawImage(background, 0,0,tw,th,null);
            if(tabIndex!=count-1)
                g2.drawImage(left, tw-left.getWidth(),0,null);
            if(tabIndex!=0)
                g2.drawImage(right, 0,0,null);

            if (isSelected){
                g2.drawImage(buttonHighlight, 0,0,tw,th,null);
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


            FontMetrics fm = getFontMetrics();
            TextLayout layout = new TextLayout(title,
                    getFont(),
                    g2d.getFontRenderContext());
            Rectangle2D bounds = layout.getBounds();
            g2d.setColor(colorDeSombra);
            int x = (int) (r.width -  bounds.getWidth()) / 2;
            int y = (r.height - fm.getMaxAscent() - fm.getMaxDescent()) / 2;
            y += fm.getAscent() - 1;
            layout.draw(g2d,
                x,
                y);
            

            if (isSelected){
                g2d.setColor(getForeground());
            }
            else{
                g2d.setColor(getForeground().darker());
            }
            layout.draw(g2d, x, y);
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
