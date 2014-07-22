/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author edisoncor
 */
public class MouseAdpaterBallon extends MouseAdapter{

    private JLabel content;
    private Popup popup;



    public MouseAdpaterBallon(String texto) {
        content = new JLabel(texto);
        content.setBorder(new EmptyBorder(0, 10, 0, 10));
    }

    private MouseListener listener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            hidePopup(e);
        }
    };


    private void hidePopup(MouseEvent e) {
        e.getComponent().removeMouseListener(listener);
        if (popup != null)
            popup.hide();
    }


    @Override
    public void mousePressed(MouseEvent e) {
        hidePopup(e);
        popup = BalloonManager.getBalloon(e.getComponent(), content, e.getX(), e.getY());
        popup.show();
        content.setSize(content.getPreferredSize());
        content.getParent().addMouseListener(listener);
    }

}
