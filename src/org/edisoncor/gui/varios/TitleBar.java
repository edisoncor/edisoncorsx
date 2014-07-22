/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.varios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.edisoncor.gui.util.WindowsUtil;

/**
 *
 * @author MABEL ROMERO FLORES
 */
public class TitleBar extends JPanel {

        private static final Color FONT_COLOR = new Color(255, 255, 255, 255);

        private static final Color UNFOCUSED_FONT_COLOR = new Color(0xcccccc);

        private static final Color HIGHLIGHT = new Color(255, 255, 255, 25);

        private static final Color TOP_BACKGROUND_TOP = new Color(255, 255, 255, 59);

        private static final Color TOP_BACKGROUND_BOTTOM = new Color(196, 196, 196, 59);

        private static final Color BOTTOM_BACKGROUND = new Color(255, 255, 255, 30);

        private static final Color UNFOCUSED_BACKGROUND = new Color(0, 0, 0, 10);

        private static final Icon CLOSE_ICON = new ImageIcon(
                TitleBar.class.getResource(
                        "/resources/button-close-16.png"));

        private static final Icon CLOSE_HOVER_ICON = new ImageIcon(
                TitleBar.class.getResource(
                        "/resources/button-close-over-16.png"));

        private static final Icon CLOSE_PRESSED_ICON = new ImageIcon(
                TitleBar.class.getResource(
                        "/resources/button-close-pressed-16.png"));

        private final JButton fCloseButton = new JButton(CLOSE_ICON);


        private  JLabel fLabel;
        private String titulo="Titulo";


    public TitleBar() {
        this("Titulo");
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
        fLabel.setText(titulo);
    }

    public void addCloseAction(ActionListener action){
        fCloseButton.addActionListener(action);
    }

    public TitleBar(String title ) {
            fLabel = new JLabel(title, SwingConstants.CENTER);
            fLabel.setFont(fLabel.getFont().deriveFont(Font.BOLD, 11.0f));

            setOpaque(false);
            setPreferredSize(new Dimension(-1, 20));
            updateFocusState();

            fCloseButton.setBorder(getCloseButtonBorder());
            fCloseButton.setVerticalAlignment(SwingConstants.CENTER);
            fCloseButton.setOpaque(false);
            fCloseButton.setFocusable(false);
            fCloseButton.setBorderPainted(false);
            fCloseButton.setContentAreaFilled(false);
            fCloseButton.setRolloverIcon(CLOSE_HOVER_ICON);
            fCloseButton.setPressedIcon(CLOSE_PRESSED_ICON);
            



            setLayout(new BorderLayout());
            add(fLabel, BorderLayout.CENTER);
            add(fCloseButton, BorderLayout.EAST);
        }

        public void hideCloseButton() {
            fCloseButton.setVisible(false);
        }

        private Border getCloseButtonBorder() {
             return BorderFactory.createEmptyBorder(0, 0, 0, 5);
        }


        private void updateFocusState() {
            Boolean focused = WindowsUtil.isParentWindowFocused(this);
            fLabel.setForeground(focused == null || focused ? FONT_COLOR : UNFOCUSED_FONT_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            // create a copy of the graphics object and turn on anti-aliasing.
            Graphics2D graphics2d = (Graphics2D) g.create();
            graphics2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // calculate the point in the title bar at which to change the background color.
            int midPointY = 16 / 2 + 3;

                graphics2d.setColor(UNFOCUSED_BACKGROUND);
                Area titleArea = new Area(new Area(new RoundRectangle2D.Double(
                        0, 0, getWidth(), getHeight(), 16, 16)));
                titleArea.subtract(new Area(
                        new Rectangle(0, midPointY, getWidth(), midPointY)));
                graphics2d.fill(titleArea);
                graphics2d.setColor(HIGHLIGHT);
                graphics2d.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

            graphics2d.dispose();
        }

    }

