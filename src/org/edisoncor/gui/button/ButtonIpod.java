/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.button;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolTip;
import org.edisoncor.gui.toolTip.ToolTipLlamada;
import org.edisoncor.gui.toolTip.ToolTipRound;
import org.edisoncor.gui.util.GraphicsUtil;

/**
 *
 * @author Edison
 */
public class ButtonIpod extends JButton{

    private Image buttonHighlight;
    private float shadowOffsetX;
    private float shadowOffsetY;
    private Color colorDeSombra=new Color(0,0,0);
    private int direccionDeSombra=60;
    private int distanciaDeSombra=1;
    private boolean vertical=true;
    private boolean foco=false;
    protected float tran= 0.0f;
    private float valor = tran;
    private MouseAdapter ml;
    private float ghostValue = 0.0f;
    boolean animacion=true;

    protected Color colorDeBorde = new Color(173,173,173);
    protected Color colorDeSegundoBorde = Color.WHITE;
    protected Color colorSelected = Color.BLUE;
    private Thread zoommer;


    public ButtonIpod(Icon icon) {
        super(icon);
    }

    public ButtonIpod() {
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(new Color(255,255,255));
        buttonHighlight = loadImage("/resources/header-halo.png");
        ml = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                zoom();

            }
        };
        addMouseListener(ml);
        setPreferredSize(new Dimension(73, 76));
        addFocusListener(new FocusListener() {

            public void focusGained(FocusEvent e) {
                foco=true;
            }

            public void focusLost(FocusEvent e) {
                foco=false;
            }
        });
        addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                zoom();
            }
        });
    }


    @Override
    protected void paintBorder(Graphics g) {
        int x = 1, y = 1;
        int w = getWidth() - 2, h = getHeight() - 2;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        ButtonModel modelo = getModel();
        g2.setStroke(new BasicStroke(.1f));
        g2.setColor(foco?colorSelected:colorDeBorde);
        g2.drawRect(x, y, w, h);

        g2.setStroke(new BasicStroke(.1f));
        g2.setColor(foco?colorSelected:colorDeSegundoBorde);
        g2.drawRect(x + 1, y + 1, w - 2, h - 2);

        g2.dispose();
    }

    @Override
    public JToolTip createToolTip() {
        ToolTipLlamada tip = new ToolTipLlamada();
        tip.setComponent(this);
        return tip;
    }

    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(ButtonIpod.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }

    private void computeShadow() {
        double rads = Math.toRadians(direccionDeSombra);
        shadowOffsetX = (float) Math.cos(rads) * distanciaDeSombra;
        shadowOffsetY = (float) Math.sin(rads) * distanciaDeSombra;
    }

    @Override
    protected void paintComponent(Graphics g) {
        computeShadow();
        float x1=0,x2=0,y1=0,y2=getHeight();
        if(!vertical){
            x1=0;
            y1=0;
            x2=getWidth();
            y2=0;
        }
        Graphics2D g2 = (Graphics2D) g;
        Paint oldPaint = g2.getPaint();


        ButtonModel modelo = getModel();



        
        if(modelo.isRollover() | foco){
            g2.drawImage(buttonHighlight,
                    0,0,
                    getWidth(), getHeight(), null);
        }

        FontMetrics fm = getFontMetrics(getFont());
        TextLayout layout = new TextLayout(getText(),
                getFont(),
                g2.getFontRenderContext());
        Rectangle2D bounds = layout.getBounds();

        int x = (int) (getWidth() - bounds.getWidth()) / 2;
        //x -= 2;
        int y = (getHeight() -  fm.getMaxAscent() - fm.getMaxDescent()) ;
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
        paintImage(g2,y);
        g2.setPaint(oldPaint);

    }

    private void paintImage(Graphics2D g2, float y) {
        if(getIcon()==null){
            return ;
        }
        Insets insets = getInsets();
        BufferedImage image = GraphicsUtil.toBufferedImage(((ImageIcon)getIcon()).getImage());
        if (ghostValue > 0.0f) {
            int newWidth = (int) (image.getWidth() * (1.0 + ghostValue / 2.0));
            int newHeight = (int) (image.getHeight() * (1.0 + ghostValue / 2.0));
            Composite composite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       0.7f * (1.0f - ghostValue)));
            g2.drawImage(image,
                         10+(image.getWidth() - newWidth) / 2,
                         10+(image.getWidth() - newWidth) / 2,
                         newWidth, newHeight, null);
            g2.setComposite(composite);
        }
        g2.drawImage(image,
                10,
                10,
                getWidth()-20,
                getHeight()-25,
                null);

    }

    public boolean isAnimacion() {
        return animacion;
    }

    public void setAnimacion(boolean animacion) {
        this.animacion = animacion;
    }

   public Color getColorDeSombra() {
        return colorDeSombra;
    }

    public void setColorDeSombra(Color colorDeSombra) {
        this.colorDeSombra = colorDeSombra;
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

    public boolean isVertical() {
        return vertical;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
        repaint();
    }

    private void zoom(){
        if(!animacion)
            return ;
        if(zoommer!=null){
            if(zoommer.isAlive())
            return;
        }
     zoommer = new Thread(new Runnable() {
        public void run() {
            for(float i=0;i<=1;i+=0.1){
                try {
                    Thread.sleep((int) (Math.random() * 100));
                    ghostValue=i;
                    repaint();
                } catch (InterruptedException ex) {
                }
            }
            try {
                Thread.sleep((int) (Math.random() * 100));
            } catch (InterruptedException ex) {
                Logger.getLogger(ButtonTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            ghostValue=0;
            repaint();
        }
    }
    );
    zoommer.start();
}


}
