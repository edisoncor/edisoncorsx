/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.edisoncor.gui.util.GraphicsUtil;
import org.edisoncor.gui.util.Reflection;


/**
 *
 * @author Edisoncor
 */
public class PanelReflect extends JPanel{

    
    public PanelReflect() {
       setOpaque(false);
    }

    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Component[] c = getComponents();
        for (Component component : c) {
            BufferedImage image = GraphicsUtil.getImage(component);
            Point p=component.getLocation();
            BufferedImage mask = Reflection.createGradientMask(image.getWidth(),
                                                               image.getHeight());
            image = Reflection.createReflectedPicture(image, mask);

            g2.drawImage(image,(int) p.getX(),(int)p.getY(),
                    component.getWidth()+6,(int)(component.getHeight()*1.67),null);
            
           
        }
    }
    
    
}
