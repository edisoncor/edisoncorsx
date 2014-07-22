/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.panel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author edison
 */
public class PanelImage extends JPanel{

    private Image image=null;
    private Icon icon;

    /** Creates a new instance of JEImagePanel */
    public PanelImage() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 =(Graphics2D) g;
        if(image!=null)
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

  
    public void setImage(String fileName){
        setIcon(new ImageIcon(getClass().getResource(fileName))); 
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon){
        this.icon=icon;
        if(icon!=null)
            image=((ImageIcon)icon).getImage();
    }

}
