/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.label;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JLabel;
import org.edisoncor.gui.tabbedPane.TabbedPaneHeader;
import org.edisoncor.gui.util.ColorTintFilter;

/**
 *
 * @author Edison
 */
public class LabelHeader extends JLabel{

    private float shadowOffsetX;
    private float shadowOffsetY;
    private Color colorDeSombra=new Color(0,0,0);
    private Color color=new Color(0,0,0);
    private int direccionDeSombra=60;
    private int distanciaDeSombra=1;
    private boolean vertical=true;
    protected BufferedImage background;
    private boolean colored=false;


    public LabelHeader(Icon icon) {
        super(icon);
    }

    public LabelHeader() {
        setOpaque(false);
        background = loadImage("/resources/header-background.png");
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(new Color(255,255,255));
    }

    private void computeShadow() {
        double rads = Math.toRadians(direccionDeSombra);
        shadowOffsetX = (float) Math.cos(rads) * distanciaDeSombra;
        shadowOffsetY = (float) Math.sin(rads) * distanciaDeSombra;
    }

    private static BufferedImage loadImage(String fileName) {
        try {
            return ImageIO.read(TabbedPaneHeader.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        computeShadow();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Paint oldPaint = g2.getPaint();

        g2.drawImage(background, 0, 0, getWidth(), getHeight(), null);

        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();

        int x = (int) (getWidth() - bounds.getWidth()) / 2;
        //x -= 2;
        int y = (getHeight() -  fm.getMaxAscent() - fm.getMaxDescent()) / 2;
        y += fm.getAscent() - 1;

        g2.setColor(colorDeSombra);
        layout.draw(g2,
                x + (int) Math.ceil(shadowOffsetX),
                y + (int) Math.ceil(shadowOffsetY));
        g2.setColor(getForeground());
        layout.draw(g2, x, y);

        g2.setPaint(oldPaint);

    }

    @Override
    protected void paintBorder(Graphics g) {
            return ;
    }

    public Color getColorDeSombra() {
        return colorDeSombra;
    }

    public void setColorDeSombra(Color colorDeSombra) {
        this.colorDeSombra = colorDeSombra;
        repaint();
    }

    public int getDireccionDeSombra() {
        return direccionDeSombra;
    }

    public void setDireccionDeSombra(int direccionDeSombra) {
        this.direccionDeSombra = direccionDeSombra;
        repaint();
    }

    public int getDistanciaDeSombra() {
        return distanciaDeSombra;
    }

    public void setDistanciaDeSombra(int distanciaDeSombra) {
        this.distanciaDeSombra = distanciaDeSombra;
        repaint();
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        repaint();
    }

    public boolean isColored() {
        return colored;
    }

    public void setColored(boolean colored) {
        this.colored = colored;
        ColorTintFilter tint = new ColorTintFilter(getColor(), .5f);
        background = loadImage("/resources/header-background.png");
        if(colored)
            tint.filter(background, background);
        repaint();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
