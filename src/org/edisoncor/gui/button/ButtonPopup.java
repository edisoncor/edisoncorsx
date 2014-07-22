/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.button;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author Edison
 */
public class ButtonPopup extends JToggleButton{

    private JPopupMenu popupMenu = new JPopupMenu();
    private boolean popupInvisible = true;

    public ButtonPopup() {
        setFocusable(false);

        putClientProperty("JButton.buttonType", "textured");

        addMouseListener(createButtonMouseListener());

        popupMenu.addPopupMenuListener(createPopupMenuListener());

        JComboBox box = new JComboBox();
        Object preventHide = box.getClientProperty("doNotCancelPopup");
        putClientProperty("doNotCancelPopup", preventHide);
    }

    public void addItem(JMenuItem item){
        popupMenu.add(item);
    }

    private MouseListener createButtonMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (popupMenu.isShowing()) {
                    hidePopupMenu();
                } else {
                    showPopupMenu();
                }
            }
        };
    }

    private PopupMenuListener createPopupMenuListener() {
        return new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                if (popupInvisible) {
                    setSelected(false);
                }
            }
            public void popupMenuCanceled(PopupMenuEvent e) {
                setSelected(false);
            }
        };
    }

    public void hidePopupMenu() {
        popupInvisible = false;
        popupMenu.setVisible(false);
        popupInvisible = true;
    }

    private void showPopupMenu() {
        popupMenu.show(this, 1, this.getHeight());
    }

    public JComponent getComponent() {
        return this;
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }
}

