/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.varios;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author edisoncor
 */
public class PanelDeslizante extends JScrollPane{

    private List<JPanel> paneles = new ArrayList<JPanel>();
    private Integer panelactivo;
    private JPanel panel;

    public PanelDeslizante() {
        super();
        panel = new JPanel();
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        panel.setLayout(new java.awt.GridLayout());
        setViewportView(panel);
    }

    public void addPanel(JPanel panel){
        this.panel.add(panel);
        this.panel.setSize(getWidth()*2, getHeight());
    }



}
