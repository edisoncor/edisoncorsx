/*
 
 */

package org.edisoncor.gui.panel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.edisoncor.gui.util.ColorTintFilter;
import org.edisoncor.gui.util.GaussianBlurFilter;
import org.edisoncor.gui.util.GraphicsUtilities;


public class PanelGlassGaussian extends JComponent {

    private JFrame mainFrame;
    private JDialog mainDialog;
    private boolean dialogo=false;
    private Color tinte = Color.BLACK;

    private BufferedImage backDrop = null;
    private int radius = 12;

    public PanelGlassGaussian(JFrame frame, int radio) {
        mainFrame= frame;
        radius=radio;
        setBackground(Color.WHITE);
        setOpaque(true);
        dialogo=false;
    }

    public PanelGlassGaussian(JDialog frame, int radio) {
        mainDialog= frame;
        radius=radio;
        setBackground(Color.WHITE);
        setOpaque(true);
        dialogo=true;
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
                if(!dialogo)
                    backDrop = GraphicsUtilities.createCompatibleImage(mainFrame.getRootPane().getWidth(),
                                                                   mainFrame.getRootPane().getHeight());
                else
                    backDrop = GraphicsUtilities.createCompatibleImage(mainDialog.getRootPane().getWidth(),
                                                                   mainDialog.getRootPane().getHeight());
                Graphics2D g2 = backDrop.createGraphics();
                if(!dialogo)
                    mainFrame.getRootPane().paint(g2);
                else
                    mainDialog.getRootPane().paint(g2);
                g2.dispose();

                if(!dialogo)
                    backDrop = GraphicsUtilities.createThumbnail(backDrop,
                                                             mainFrame.getRootPane().getWidth() / 2);
                else
                    backDrop = GraphicsUtilities.createThumbnail(backDrop,
                                                             mainDialog.getRootPane().getWidth() / 2);
                backDrop = new ColorTintFilter(tinte, 0.10f).filter(backDrop, null);
                backDrop = new GaussianBlurFilter(radius).filter(backDrop, null);
                
        } else {
            if (backDrop != null) {
                backDrop.flush();
            }
            backDrop = null;
        }
        super.setVisible(visible);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if(dialogo){
            int width = mainDialog.getRootPane().getWidth();
            int height = mainDialog.getRootPane().getHeight();
            g2.drawImage(backDrop, 0, 0, width, height, null);
        }else{
            int width = mainFrame.getRootPane().getWidth();
            int height = mainFrame.getRootPane().getHeight();
            g2.drawImage(backDrop, 0, 0, width, height, null);
        }

    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public Color getTinte() {
        return tinte;
    }

    public void setTinte(Color tinte) {
        this.tinte = tinte;
    }

}
