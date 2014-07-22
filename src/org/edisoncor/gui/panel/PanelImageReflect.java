/*
 * JEImagePanel.java
 *
 * Created on 17 de septiembre de 2007, 08:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import org.edisoncor.gui.util.GraphicsUtil;
import org.edisoncor.gui.util.Reflection;

/**
 *
 * @author Edisoncor
 */
public class PanelImageReflect extends JComponent{
    
    private Icon icon;
    private boolean reflejar=true;
    
    /** Creates a new instance of JEImagePanel */
    public PanelImageReflect() {
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 =(Graphics2D) g;
        if(icon!=null)
            g2.drawImage(getImage(), 0, 0, getWidth(), getHeight(), null);
    }

    public BufferedImage getImage() {
        BufferedImage bufimage = GraphicsUtil.toBufferedImage(((ImageIcon)icon).getImage());
        if(reflejar){
            BufferedImage mask = Reflection.createGradientMask(bufimage.getWidth(),
                                                                   bufimage.getHeight());
            bufimage = Reflection.createReflectedPicture(bufimage, mask);
        }
        return bufimage;
    }

   

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon){
        this.icon=icon;
    }

    public boolean isReflejar() {
        return reflejar;
    }

    public void setReflejar(boolean reflejar) {
        this.reflejar = reflejar;
        repaint();
    }

}
