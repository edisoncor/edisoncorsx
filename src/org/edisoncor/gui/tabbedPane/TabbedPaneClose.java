/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.tabbedPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author EdisonCor
 */
public class TabbedPaneClose extends JTabbedPane{

    public enum Modelo{RECT, ROUND}
    protected Modelo modelo = Modelo.RECT;
    private boolean confirmacion = false;

    public TabbedPaneClose() {
    }

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);
        int i = getTabCount();
        setTabComponentAt(i-1, new PanelTitle(title, this));
    }

    public boolean isConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(boolean confirmacion) {
        this.confirmacion = confirmacion;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }


    private class PanelTitle extends JPanel implements ActionListener{

        private JTabbedPane pane;
        public PanelTitle(String text, JTabbedPane tabbed) {
            this.pane=tabbed;
            JLabel label = new JLabel(text);
            label.getInsets().set(0, 0, 0, 10);
            JButton boton = new JButton();
            setOpaque(false);
            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
            boton.setBorderPainted(false);
            boton.setContentAreaFilled(false);
            boton.setMargin(new Insets(0, 0, 0, 0));
            switch (modelo){
                case RECT:{
                    boton.setIcon(
                            new ImageIcon(getClass()
                            .getResource("/resources/title-close.png")));
                    boton.setPressedIcon(
                            new ImageIcon(getClass()
                            .getResource("/resources/title-close-pressed.png")));
                    boton.setRolloverIcon(
                            new ImageIcon(getClass()
                            .getResource("/resources/title-close-over.png")));
                }break;
                case ROUND:{
                    boton.setIcon(
                            new ImageIcon(getClass()
                            .getResource("/resources/button-close-16.png")));
                    boton.setPressedIcon(
                            new ImageIcon(getClass()
                            .getResource("/resources/button-close-pressed-16.png")));
                    boton.setRolloverIcon(
                            new ImageIcon(getClass()
                            .getResource("/resources/button-close-over-16.png")));
                }break;
            }
            boton.addActionListener(this);
            add(boton, BorderLayout.EAST);
        }

        public void actionPerformed(ActionEvent e) {
            if(isConfirmacion()){
                if(JOptionPane.YES_OPTION==JOptionPane.showConfirmDialog(
                pane, "Desea remover el componente", "Remover",
                JOptionPane.YES_OPTION)){
                    int index = pane.indexOfTabComponent(this);
                    pane.remove(index);
                }
            }else{
                int index = pane.indexOfTabComponent(this);
                pane.remove(index);
            }
        }

    }
}
