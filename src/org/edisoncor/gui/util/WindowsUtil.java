/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Shape;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

/**
 *
 * @author MABEL ROMERO FLORES
 */
public class WindowsUtil {
    private static final String FRAME_ACTIVE_PROPERTY = "Frame.active";

    
    public static void makeWindowNonOpaque(Window window) {
        // on the mac, simply setting the window's background color to be fully transparent makes the window non-opaque.
        window.setBackground(new Color(0, 0, 0, 0));
        quietlyTryToMakeWindowNonOqaque(window);


    }


    public static void makeWindowsOpacity(Window window, float factor){
        if(factor>=0 & factor<=1)
//            com.sun.awt.AWTUtilities.setWindowOpacity(window, factor);
            window.setOpacity(factor);
        
    }

    public static void makeWindowsShape(Window window, Shape shape){
        //com.sun.awt.AWTUtilities.setWindowShape(window, shape);
        window.setShape(shape);
    }

    public static void makeWindowsShape(Window window,
            float x, float y, float w, float h, float arcw, float arch){
        Shape s = new RoundRectangle2D.Float(x, y, w, h, arcw, arch);
        //com.sun.awt.AWTUtilities.setWindowShape(window, s);
        window.setShape(s);
    }

    public static void makeWindowsShape(Window window,
            float x, float y, float w, float h){
        Shape s = new Rectangle2D.Float(x, y, w, h);
        //com.sun.awt.AWTUtilities.setWindowShape(window, s);
        window.setShape(s);
    }
  


    private static void quietlyTryToMakeWindowNonOqaque(Window window) {
        try {
            Class clazz = Class.forName("com.sun.awt.AWTUtilities");
            Method method =
                    clazz.getMethod("setWindowOpaque", java.awt.Window.class, Boolean.TYPE);
            method.invoke(clazz, window, false);
        } catch (ClassNotFoundException e) {
        } catch (IllegalAccessException e) {
        } catch (IllegalArgumentException e) {
        } catch (NoSuchMethodException e) {
        } catch (SecurityException e) {
        } catch (InvocationTargetException e) {
        }
    }



    public static WindowFocusListener createAndInstallRepaintWindowFocusListener(Window window) {

        // create a WindowFocusListener that repaints the window on focus
        // changes.
        WindowFocusListener windowFocusListener = new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent e) {
                e.getWindow().repaint();
            }

            public void windowLostFocus(WindowEvent e) {
                e.getWindow().repaint();
            }
        };

        window.addWindowFocusListener(windowFocusListener);

        return windowFocusListener;
    }

    
    public static boolean isParentWindowFocused(Component component) {
        Window window = SwingUtilities.getWindowAncestor(component);
        return window != null && window.isFocused();
    }

    public static void installWindowFocusListener(
            WindowFocusListener focusListener, JComponent component) {
        component.addPropertyChangeListener(FRAME_ACTIVE_PROPERTY,
                createFrameFocusPropertyChangeListener(focusListener, component));
    }

    
    public static void installJComponentRepainterOnWindowFocusChanged(JComponent component) {
        component.addAncestorListener(createAncestorListener(component,
                createWindowListener(component)));
    }

    private static AncestorListener createAncestorListener(
            final JComponent component, final WindowListener windowListener) {
        return new AncestorListener() {
            public void ancestorAdded(AncestorEvent event) {
                Window window = SwingUtilities.getWindowAncestor(component);
                if (window != null) {
                    window.removeWindowListener(windowListener);
                    window.addWindowListener(windowListener);
                }
            }

            public void ancestorRemoved(AncestorEvent event) {
                // no implementado aún.
            }

            public void ancestorMoved(AncestorEvent event) {
                // no implementado aún.
            }
        };
    }

    private static WindowListener createWindowListener(final JComponent component) {
        return new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                component.repaint();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                component.repaint();
            }
        };
    }

    private static PropertyChangeListener createFrameFocusPropertyChangeListener(
            final WindowFocusListener focusListener, final JComponent component) {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                Window window = SwingUtilities.getWindowAncestor(component);
                // use the client property that initiated this this
                // property change event, as the actual window's
                // isFocused method may not return the correct value
                // because the window is in transition.
                boolean hasFocus = (Boolean) component.getClientProperty(FRAME_ACTIVE_PROPERTY);
                if (hasFocus) {
                    focusListener.windowGainedFocus(
                            new WindowEvent(window, WindowEvent.WINDOW_GAINED_FOCUS));
                } else {
                    focusListener.windowLostFocus(
                            new WindowEvent(window, WindowEvent.WINDOW_LOST_FOCUS));
                }
            }
        };
    }

}