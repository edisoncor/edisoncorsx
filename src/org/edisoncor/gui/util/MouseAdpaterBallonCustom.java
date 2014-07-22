/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.util;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Popup;

/**
 *
 * @author edisoncor
 */
public class MouseAdpaterBallonCustom extends MouseAdapter{

    private Component content;
    private Popup popup;

    public MouseAdpaterBallonCustom(Component content) {
        this.content = content;
    }

    private MouseListener listener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getButton()==3)
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
