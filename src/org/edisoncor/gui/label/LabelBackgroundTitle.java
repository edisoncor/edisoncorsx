package org.edisoncor.gui.label;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicLabelUI;


public class LabelBackgroundTitle extends JLabel {
    
    private Image titleImage;
    private int titleHeight;
    private int titleWidth;
    
    private float shadowOffsetX;
    private float shadowOffsetY;
    
    private float shadowOpacity=0.5f;
    private Color shadowColor= new Color(0,0,0);
    private int shadowDistance = 2;
    private int shadowDirection = 60;
    private Font titleFont = new Font("Arial Blank",Font.PLAIN,36);
    private Color titleColor= new Color(255,255,255);
    private float titleOpacity = .8f;
    private Dimension labelDimension= new Dimension(125, 35);

    public LabelBackgroundTitle(){
        this("BackGround");
    }
    
    public LabelBackgroundTitle(final String text) {
        setOpaque(false);
        setText(text);
        setUI(new BasicLabelUI() {
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
                Dimension d = new Dimension(labelDimension);
                d.width += insets.left + insets.right;
                d.height += insets.top + insets.bottom;
                return d;
            }
        });
        computeShadow();
    }
    
    
    private void computeShadow() {
        double rads = Math.toRadians(shadowDirection);
        shadowOffsetX = (float) Math.cos(rads) * shadowDistance;
        shadowOffsetY = (float) Math.sin(rads) * shadowDistance;
    }

    @Override
    protected void paintComponent(Graphics g) {
        computeShadow();
        Graphics2D g2 = (Graphics2D) g;
        if (titleImage == null) {
            titleImage = createTitleImage(g2);
        }
        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                   titleOpacity));
        if(getHorizontalAlignment()==CENTER)
            g2.drawImage(titleImage,
                     (getWidth() - titleWidth) / 2,
                     (getHeight() - titleHeight) / 2, null);
        else
            g2.drawImage(titleImage,
                     0,
                     (getHeight() - titleHeight) / 2, null);
        g2.setComposite(composite);
    }
    
    private Image createTitleImage(Graphics2D g2) {
        FontRenderContext context = g2.getFontRenderContext();
        if((getText()==null)||getText().length()<=0)
            setText(new String("Title"));
        TextLayout layout = new TextLayout(getText(), titleFont, context);
        Rectangle2D bounds = layout.getBounds();
        
        BufferedImage image = new BufferedImage(getWidth() - 120,
                                                getHeight() ,
                                                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        setupGraphics(g2d);

        int[] arrowX  = { getWidth() - 135,
                          getWidth() - 125,
                          getWidth() - 135 };
        int[] arrowY  = { 3 + (int) bounds.getHeight() + 7,
                          7 + (int) bounds.getHeight() + 7,
                          12 + (int) bounds.getHeight() + 7 };
        int   npoints = 3;
        Polygon arrow = new Polygon(arrowX, arrowY, npoints); 
        
        Composite composite = g2d.getComposite();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    shadowOpacity));
        g2d.setColor(shadowColor);
        layout.draw(g2d,
                    shadowOffsetX + 5.0f,
                    layout.getAscent() - layout.getDescent() + shadowOffsetY + 5.0f);
        g2d.fillRect(5 + (int) shadowOffsetX,
                     (int) shadowOffsetY + 5 + (int) bounds.getHeight() + 7,
                     getWidth() - 135, 5);
        g2d.translate(0, shadowOffsetY);
        g2d.fill(arrow);
        g2d.translate(0, -shadowOffsetY);
        g2d.setComposite(composite);
        
        g2d.setColor(titleColor);
        layout.draw(g2d, 5.0f, 5.0f + layout.getAscent() - layout.getDescent());

        g2d.fillRect(5, 5 + (int) bounds.getHeight() + 7, getWidth() - 135, 5);
        g2d.fill(arrow);
        g2d.dispose();
        
        titleWidth = image.getWidth();
        titleHeight = image.getHeight();
        
        Kernel kernel = new Kernel(3, 3, new float[] { 1f/9f, 1/9f, 1f/9f,
                                                       1f/9f, 1/9f, 1f/9f,
                                                       1f/9f, 1/9f, 1f/9f });
        ConvolveOp op = new ConvolveOp(kernel);

        return op.filter(op.filter(image, null), null);
    }

    private static void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        computeShadow();
        repaint();
    }

    public int getShadowDistance() {
        return shadowDistance;
    }

    public void setShadowDistance(int shadowDistance) {
        this.shadowDistance = shadowDistance;
        computeShadow();
        titleImage=null;
        repaint();
    }

    public Color getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(Color titleColor) {
        this.titleColor = titleColor;
        computeShadow();
        titleImage=null;
        repaint();
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
        computeShadow();
        titleImage=null;
        repaint();
    }

    public int getTitleHeight() {
        return titleHeight;
    }

    public void setTitleHeight(int titleHeight) {
        this.titleHeight = titleHeight;
        computeShadow();
        titleImage=null;
        repaint();
    }

    
}
