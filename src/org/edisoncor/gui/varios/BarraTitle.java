package org.edisoncor.gui.varios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputListener;


public class BarraTitle extends JComponent {
    private JButton closeButton;
    private JButton iconifyButton;
    private JButton maximizeButton;

    ////////////////////////////////////////////////////////////////////////////
    // THEME SPECIFIC FIELDS
    ////////////////////////////////////////////////////////////////////////////
    private int preferredHeight;
    private Color lightColor=Color.gray;
    private Color shadowColor= Color.black;
    
    private BufferedImage grip ;
    private BufferedImage backgroundGradient;
    private Color inactiveLightColor= Color.GRAY;
    private Color inactiveShadowColor= Color.DARK_GRAY;
    private BufferedImage inactiveGrip;
    private BufferedImage inactiveBackgroundGradient;
    private BufferedImage close;
    private BufferedImage closeInactive;
    private BufferedImage closeOver;
    private BufferedImage closePressed;
    private BufferedImage minimize;
    private BufferedImage minimizeInactive;
    private BufferedImage minimizeOver;
    private BufferedImage minimizePressed;
    private BufferedImage maximize;
    private BufferedImage maximizeInactive;
    private BufferedImage maximizeOver;
    private BufferedImage maximizePressed;

    public BarraTitle() {
        try {
            loadImages();
            setLayout(new GridBagLayout());
            createButtons();
        } catch (IOException ex) {
            Logger.getLogger(BarraTitle.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void loadImages() throws IOException{
        grip = ImageIO.read(getClass().getResource("/resources/title-grip.png"));
        backgroundGradient= ImageIO.read(getClass().getResource("/resources/title-background.png"));
        inactiveLightColor= Color.blue;
        inactiveShadowColor= Color.black;
        inactiveGrip= ImageIO.read(getClass().getResource("/resources/title-grip-inactive.png"));
        inactiveBackgroundGradient= ImageIO.read(getClass().getResource("/resources/title-background-inactive.png"));
        close= ImageIO.read(getClass().getResource("/resources/title-close.png"));
        closeInactive= ImageIO.read(getClass().getResource("/resources/title-close-inactive.png"));
        closeOver= ImageIO.read(getClass().getResource("/resources/title-close-over.png"));
        closePressed= ImageIO.read(getClass().getResource("/resources/title-close-pressed.png"));
        minimize= ImageIO.read(getClass().getResource("/resources/title-minimize.png"));
        minimizeInactive= ImageIO.read(getClass().getResource("/resources/title-minimize-inactive.png"));
        minimizeOver= ImageIO.read(getClass().getResource("/resources/title-minimize-over.png"));
        minimizePressed= ImageIO.read(getClass().getResource("/resources/title-minimize-pressed.png"));
        maximize= ImageIO.read(getClass().getResource("/resources/title-maximize.png"));
        maximizeInactive= ImageIO.read(getClass().getResource("/resources/title-maximize-inactive.png"));
        maximizeOver= ImageIO.read(getClass().getResource("/resources/title-maximize-over.png"));
        maximizePressed= ImageIO.read(getClass().getResource("/resources/title-maximize-pressed.png"));
        preferredHeight =close.getHeight();
    }
    
    

    public void installListeners() {
        MouseInputHandler handler = new MouseInputHandler();
        Window window = SwingUtilities.getWindowAncestor(this);
        window.addMouseListener(handler);
        window.addMouseMotionListener(handler);

        window.addWindowListener(new WindowHandler());
    }

    private void createButtons() {
        add(Box.createHorizontalGlue(),
            new GridBagConstraints(0, 0,
                                   1, 1,
                                   1.0, 1.0,
                                   GridBagConstraints.EAST,
                                   GridBagConstraints.HORIZONTAL,
                                   new Insets(0, 0, 0, 0),
                                   0, 0));

        add(iconifyButton = createButton(new IconifyAction(),
                                         minimize, minimizePressed, minimizeOver),
            new GridBagConstraints(1, 0,
                                   1, 1,
                                   0.0, 1.0,
                                   GridBagConstraints.NORTHEAST,
                                   GridBagConstraints.NONE,
                                   new Insets(1, 0, 0, 2),
                                   0, 0));
        add(closeButton = createButton(new CloseAction(),
                                       close, closePressed, closeOver),
            new GridBagConstraints(3, 0,
                                   1, 1,
                                   0.0, 1.0,
                                   GridBagConstraints.NORTHEAST,
                                   GridBagConstraints.NONE,
                                   new Insets(1, 0, 0, 2),
                                   0, 0));
        add(maximizeButton = createButton(new MaximizeAction(),
                                       maximize, maximizePressed, maximizeOver),
            new GridBagConstraints(2, 0,
                                   1, 1,
                                   0.0, 1.0,
                                   GridBagConstraints.NORTHEAST,
                                   GridBagConstraints.NONE,
                                   new Insets(1, 0, 0, 2),
                                   0, 0));
    }

    private static JButton createButton(final AbstractAction action,
                                 final BufferedImage image,
                                 final Image pressedImage,
                                 final Image overImage) {
        JButton button = new JButton(action);
        button.setIcon(new ImageIcon(image));
        button.setPressedIcon(new ImageIcon(pressedImage));
        button.setRolloverIcon(new ImageIcon(overImage));
        button.setRolloverEnabled(true);
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusable(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(image.getWidth(),
                                              image.getHeight()));
        return button;
    }

    private void close() {
        Window w = SwingUtilities.getWindowAncestor(this);
        w.dispatchEvent(new WindowEvent(w,
                                        WindowEvent.WINDOW_CLOSING));
    }

    private void iconify() {
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.setExtendedState(frame.getExtendedState() | Frame.ICONIFIED);
        }
    }
    
    private void maximize() {
        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            if (frame.getExtendedState()==Frame.MAXIMIZED_BOTH)
                frame.setExtendedState(Frame.NORMAL);
            else
                frame.setExtendedState(frame.getExtendedState() | Frame.MAXIMIZED_BOTH);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.height = preferredHeight;
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        Dimension size = super.getMinimumSize();
        size.height = preferredHeight;
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        Dimension size = super.getMaximumSize();
        size.height = preferredHeight;
        return size;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (!isVisible()) {
            return;
        }

        boolean active = SwingUtilities.getWindowAncestor(this).isActive();

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_OFF);

        Rectangle clip = g2.getClipBounds();
        g2.drawImage(active ? backgroundGradient : inactiveBackgroundGradient,
                     clip.x, 0, clip.width, getHeight() - 2, null);

        g2.setColor(active ? lightColor : inactiveLightColor);
        g2.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);

        g2.setColor(active ? shadowColor : inactiveShadowColor);
        g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);

        g2.drawImage(active ? grip : inactiveGrip, 0, 0, null);
    }

    private class CloseAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            close();
        }
    }

    private class MaximizeAction extends AbstractAction{
        public void actionPerformed(ActionEvent e){
            maximize();
        }
    }
    
    
    private class IconifyAction extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            iconify();
        }
    }

    private class MouseInputHandler implements MouseInputListener {
        private boolean isMovingWindow;
        private int dragOffsetX;
        private int dragOffsetY;

        private static final int BORDER_DRAG_THICKNESS = 5;

        @Override
        public void mousePressed(MouseEvent ev) {
            Point dragWindowOffset = ev.getPoint();
            Window w = (Window)ev.getSource();
            if (w != null) {
                w.toFront();
            }
            Point convertedDragWindowOffset = SwingUtilities.convertPoint(
                           w, dragWindowOffset, BarraTitle.this);

            Frame f = null;
            Dialog d = null;

            if (w instanceof Frame) {
                f = (Frame)w;
            } else if (w instanceof Dialog) {
                d = (Dialog)w;
            }

            int frameState = (f != null) ? f.getExtendedState() : 0;

            if (BarraTitle.this.contains(convertedDragWindowOffset)) {
                if ((f != null && ((frameState & Frame.MAXIMIZED_BOTH) == 0)
                        || (d != null))
                        && dragWindowOffset.y >= BORDER_DRAG_THICKNESS
                        && dragWindowOffset.x >= BORDER_DRAG_THICKNESS
                        && dragWindowOffset.x < w.getWidth()
                            - BORDER_DRAG_THICKNESS) {
                    isMovingWindow = true;
                    dragOffsetX = dragWindowOffset.x;
                    dragOffsetY = dragWindowOffset.y;
                }
            }
            else if (f != null && f.isResizable()
                    && ((frameState & Frame.MAXIMIZED_BOTH) == 0)
                    || (d != null && d.isResizable())) {
                dragOffsetX = dragWindowOffset.x;
                dragOffsetY = dragWindowOffset.y;
            }
        }

        @Override
        public void mouseReleased(MouseEvent ev) {
            isMovingWindow = false;
        }

        @Override
        public void mouseDragged(MouseEvent ev) {
            Window w = (Window)ev.getSource();

            if (isMovingWindow) {
                Point windowPt = MouseInfo.getPointerInfo().getLocation();
                windowPt.x = windowPt.x - dragOffsetX;
                windowPt.y = windowPt.y - dragOffsetY;
                w.setLocation(windowPt);
            }
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mouseMoved(MouseEvent e) {
        }
    }

    private class WindowHandler extends WindowAdapter {
        @Override
        public void windowActivated(WindowEvent ev) {
            closeButton.setIcon(new ImageIcon(close));
            iconifyButton.setIcon(new ImageIcon(minimize));
            maximizeButton.setIcon(new ImageIcon(maximize));
            getRootPane().repaint();
        }

        @Override
        public void windowDeactivated(WindowEvent ev) {
            closeButton.setIcon(new ImageIcon(closeInactive));
            iconifyButton.setIcon(new ImageIcon(minimizeInactive));
            maximizeButton.setIcon(new ImageIcon(maximizeInactive));
            getRootPane().repaint();
        }
    }
}
