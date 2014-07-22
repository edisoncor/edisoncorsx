/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.tabbedPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.GridBagConstraints;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import org.edisoncor.gui.panel.PanelImage;

/**
 *
 * @author EdisonCor
 */
public class TabbedPaneTask extends JTabbedPane{


    public TabbedPaneTask() {
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);
        int i = getTabCount();
        setTabComponentAt(i-1, new PanelTitle(title,this));
    }

    @Override
    public void addTab(String title, Icon icon, Component cmpnt) {
        super.addTab(title, icon, cmpnt);
        int i = getTabCount();
        setTabComponentAt(i-1, new PanelTitle(title, icon, this));
    }

    @Override
    public void addTab(String title, Icon icon, Component cmpnt, String tooltip) {
        super.addTab(title, icon, cmpnt, tooltip);
        int i = getTabCount();
        setTabComponentAt(i-1, new PanelTitle(title, icon, tooltip, this));
    }

    @Override
    public boolean action(Event event, Object o) {
        return super.action(event, o);
    }

    private class PanelTitle extends JPanel{

        private JTabbedPane pane;
        public PanelTitle(String title, JTabbedPane tabbed) {
            this.pane=tabbed;
            init(title, null, "");
        }

        public PanelTitle(String title, Icon icon, JTabbedPane tabbed) {
            this.pane=tabbed;
            init(title, icon, "");
        }
        public PanelTitle(String title, Icon icon, String tooltip, JTabbedPane tabbed) {
            this.pane=tabbed;
            init(title, icon, tooltip);
        }

        private void init(String title, Icon icon, String tooltip){
            setPreferredSize(new Dimension(196, 64));
            setOpaque(false);
            JLabel lblTitle = new JLabel();
            PanelImage lblIcon = new PanelImage();
            JLabel lblDescripcion = new JLabel();
            setLayout(new java.awt.GridBagLayout());

            GridBagConstraints gridBagConstraints;
            lblTitle.setFont(new java.awt.Font("Liberation Sans", 1, 14)); // NOI18N
            lblTitle.setText(title);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.ipadx = 76;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
            gridBagConstraints.insets = new java.awt.Insets(24, 6, 0, 13);
            add(lblTitle, gridBagConstraints);

            lblIcon.setIcon(icon);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 0;
            gridBagConstraints.gridheight = 2;
            gridBagConstraints.ipadx = 32;
            gridBagConstraints.ipady = 32;
            gridBagConstraints.insets = new java.awt.Insets(24, 13, 24, 0);
            add(lblIcon, gridBagConstraints);

            lblDescripcion.setText(tooltip);
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = 1;
            gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
            gridBagConstraints.ipadx = 76;
            gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
            gridBagConstraints.insets = new java.awt.Insets(0, 6, 24, 13);
            add(lblDescripcion, gridBagConstraints);
        }
    }

}
