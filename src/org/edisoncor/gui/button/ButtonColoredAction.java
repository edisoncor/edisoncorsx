package org.edisoncor.gui.button;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JToolTip;
import javax.swing.plaf.basic.BasicButtonUI;
import org.edisoncor.gui.toolTip.ToolTipRound;
import org.edisoncor.gui.util.ColorTintFilter;
import org.edisoncor.gui.util.GraphicsUtil;


/**
 *
 * @author rbair
 */
public class ButtonColoredAction extends JButton {

    private float shadowOffsetX;
    private float shadowOffsetY;
    
    private Color colorDeSombra=new Color(0,0,0);
    private int direccionDeSombra=60;
    private int distanciaDeSombra=1;
    
    private BufferedImage mainButton, mainButtonPressed,
                  normalButton, normalButtonPressed, buttonHighlight;

    private Insets sourceInsets = new Insets(6, 7, 6, 8);
    private Dimension buttonDimension= new Dimension(116, 35);
    private Float profundidad=.5f;
    
    private float ghostValue;
    
    private boolean main = false;
    private boolean foco = false;

    public ButtonColoredAction(String text) {
        this();
        setText(text);
    }
    
    public ButtonColoredAction(Action a) {
        this();
        setAction(a);
    }

    @Override
    public JToolTip createToolTip() {
        ToolTipRound tip = new ToolTipRound();
        tip.setComponent(this);
        return tip;
    }

    public ButtonColoredAction() {
        
        
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setForeground(new Color(255,255,255));
        setFont(new Font("Arial", Font.BOLD, 14));
        setContentAreaFilled(false);
        setBorderPainted(false);
        normalButton = (BufferedImage) loadImage("/resources/button-normal.png");
        normalButtonPressed = (BufferedImage) loadImage("/resources/button-normal-pressed.png");
        buttonHighlight = (BufferedImage) loadImage("/resources/header-halo.png");
        mainButton = (BufferedImage) loadImage("/resources/button-main.png");
        mainButtonPressed = (BufferedImage) loadImage("/resources/button-main-pressed.png");

        ColorTintFilter tint = new ColorTintFilter(getBackground(), profundidad);
        normalButton=tint.filter(normalButton, normalButton);
        normalButtonPressed=tint.filter(normalButtonPressed, normalButtonPressed);

        
        // Hacky? Hacky!
        setUI(new BasicButtonUI() {
            @Override
            public Dimension getMinimumSize(JComponent c) {
                return getPreferredSize(c);
            }
            
            @Override
            public Dimension getMaximumSize(JComponent c) {
                return getPreferredSize(c);
            }
            
            @Override
            public Dimension getPreferredSize(JComponent c) {
                Insets insets = c.getInsets();
                Dimension d = new Dimension(buttonDimension);
                d.width += insets.left + insets.right;
                d.height += insets.top + insets.bottom;
                return d;
            }
        });
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                foco=true;
            }

            public void focusLost(FocusEvent e) {
                foco=false;
            }
        });
    }

    private  void cargaImagenes() {
        normalButton = (BufferedImage) loadImage("/resources/button-normal.png");
        normalButtonPressed = (BufferedImage) loadImage("/resources/button-normal-pressed.png");
        ColorTintFilter tint = new ColorTintFilter(getBackground(), profundidad);
        normalButton=tint.filter(normalButton, normalButton);
        normalButtonPressed=tint.filter(normalButtonPressed, normalButtonPressed);
    }

    public Float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(Float profundidad) {
        this.profundidad = profundidad;
        normalButton = (BufferedImage) loadImage("/resources/button-normal.png");
        normalButtonPressed = (BufferedImage) loadImage("/resources/button-normal-pressed.png");

        ColorTintFilter tint = new ColorTintFilter(getBackground(), profundidad);
        normalButton=tint.filter(normalButton, normalButton);
        normalButtonPressed=tint.filter(normalButtonPressed, normalButtonPressed);
        repaint();
    }

    public void setMain(boolean main) {
        boolean old = isMain();
        this.main = main;
        firePropertyChange("main", old, isMain());
    }
    
    public boolean isMain() {
        return main;
    }
    
    private void computeShadow() {
        double rads = Math.toRadians(direccionDeSombra);
        shadowOffsetX = (float) Math.cos(rads) * distanciaDeSombra;
        shadowOffsetY = (float) Math.sin(rads) * distanciaDeSombra;
    }


    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(ButtonColoredAction.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }
    private Image getImage(boolean armed) {
        if (isMain()) {
            return armed ? mainButtonPressed : mainButton;
        } else {
            return armed ? normalButtonPressed : normalButton;
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        computeShadow();
        cargaImagenes();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        ButtonModel modelo = getModel();
        Insets insets = getInsets();
        
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        
        GraphicsUtil.tileStretchPaint(g2,this,(BufferedImage) getImage(modelo.isArmed()), sourceInsets);

//        if (ghostValue > 0.0f) {
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            float alphaValue = ghostValue;
            Composite composite = g2.getComposite();
            if (composite instanceof AlphaComposite) {
                alphaValue *= ((AlphaComposite) composite).getAlpha();
            }
            
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                    1));
            if(modelo.isRollover() | foco){
                g2.drawImage(buttonHighlight,
                        insets.left + 2, insets.top + 2,
                        width - 4, height - 4, null);
            }
            g2.setComposite(composite);
//        }
        
        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();
        
        int x = (int) (getWidth() - insets.left - insets.right -
                bounds.getWidth()) / 2;
        //x -= 2;
        int y = (getHeight() - insets.top - insets.bottom -
                 fm.getMaxAscent() - fm.getMaxDescent()) / 2;
        y += fm.getAscent() - 1;
        
        if (modelo.isArmed()) {
            x += 1;
            y += 1;
        }
        
        g2.setColor(colorDeSombra);
        layout.draw(g2,
                x + (int) Math.ceil(shadowOffsetX),
                y + (int) Math.ceil(shadowOffsetY));
        if(isEnabled())
            g2.setColor(getForeground());
        else
            g2.setColor(getForeground().darker());
        layout.draw(g2, x, y);
    }

    public Color getColorDeSombra() {
        return colorDeSombra;
    }

    public void setColorDeSombra(Color colorDeSombra) {
        this.colorDeSombra = colorDeSombra;
        repaint();
    }

    public Dimension getButtonDimension() {
        return buttonDimension;
    }

    public void setButtonDimension(Dimension buttonDimension) {
        this.buttonDimension = buttonDimension;
        repaint();
    }

    public int getDireccionDeSombra() {
        return direccionDeSombra;
    }

    public void setDireccionDeSombra(int direccionDeSombra) {
        this.direccionDeSombra = direccionDeSombra;
        repaint();
    }

    public int getDistanciaDeSombra() {
        return distanciaDeSombra;
    }

    public void setDistanciaDeSombra(int distanciaDeSombra) {
        this.distanciaDeSombra = distanciaDeSombra;
        repaint();
    }
    
    
}
