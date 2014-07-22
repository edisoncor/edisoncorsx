/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.progressBar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JProgressBar;

/**
 *
 * @author hmna. Mabel Romero
 */
public class ProgressBarRound extends JProgressBar{

    private float borde=2f;
    private Color colorDeBorde = new Color(0, 51, 51);
    private Color colorDeSombra=new Color(0,0,0);
    private Color colorDeTexto=new Color(255,255,255);
    private float shadowOffsetX;
    private float shadowOffsetY;
    private int distanciaDeSombra=1;
    private int direccionDeSombra=60;

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Stroke st = g2.getStroke();
        g2.setStroke(new BasicStroke(borde));
        g2.setColor(colorDeBorde);
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, getHeight()/3, getHeight()/3);
        g2.setStroke(st);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();

        RoundRectangle2D rect =
                new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),getHeight()/3, getHeight()/3 );
        g2.clip(rect);

        g2.setPaint(new GradientPaint(0,0,getBackground(), 0, getHeight(), getBackground().darker()));

        g2.fill(rect);


        LinearGradientPaint gr  = new LinearGradientPaint(0, 0, 0, getHeight(),
                new float[]{0f, 0.3f, 0.5f, 1f},
                new Color[]{getForeground().brighter(),
                            getForeground(),
                            getForeground().darker().darker(),
                            getForeground().darker()});

        g2.setPaint(gr);

        g2.fillRoundRect(
                0, 0, getWidth()*getValue()/100, getHeight(),getHeight()/3, getHeight()/3);

        if(paintString){
            computeShadow();
            Insets insets = getInsets();
            FontMetrics fm = getFontMetrics(getFont());
            TextLayout layout = new TextLayout(getString(),
                    getFont(),
                    g2.getFontRenderContext());
            Rectangle2D bounds = layout.getBounds();

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
                g2.setColor(colorDeTexto);
            else
                g2.setColor(colorDeTexto.darker());
            layout.draw(g2, x, y);
        }

        g2.setPaint(oldPaint);


    }

    private void computeShadow() {
        double rads = Math.toRadians(direccionDeSombra);
        shadowOffsetX = (float) Math.cos(rads) * distanciaDeSombra;
        shadowOffsetY = (float) Math.sin(rads) * distanciaDeSombra;
    }

    public float getBorde() {
        return borde;
    }

    public void setBorde(float borde) {
        this.borde = borde;
    }

    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    public Color getColorDeSombra() {
        return colorDeSombra;
    }

    public void setColorDeSombra(Color colorDeSombra) {
        this.colorDeSombra = colorDeSombra;
    }

    public Color getColorDeTexto() {
        return colorDeTexto;
    }

    public void setColorDeTexto(Color colorDeTexto) {
        this.colorDeTexto = colorDeTexto;
    }

    public int getDireccionDeSombra() {
        return direccionDeSombra;
    }

    public void setDireccionDeSombra(int direccionDeSombra) {
        this.direccionDeSombra = direccionDeSombra;
    }

    public int getDistanciaDeSombra() {
        return distanciaDeSombra;
    }

    public void setDistanciaDeSombra(int distanciaDeSombra) {
        this.distanciaDeSombra = distanciaDeSombra;
    }

    

    

}
