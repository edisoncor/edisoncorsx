/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.label;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicLabelUI;
import org.edisoncor.gui.toolTip.ToolTipRound;
import org.edisoncor.gui.util.GraphicsUtil;
import org.edisoncor.gui.util.HyperlinkHandler;
import org.edisoncor.gui.util.Reflection;

/**
 *
 * @author Edisoncor
 */
public class LabelTask extends JLabel {

    private BufferedImage image;
    private Rectangle clickable;
    private float ghostValue = 0.0f;
    private String description="Descripcion";
    private String imageName="/resources/task-view-trip.png";
    private Font categoryFont = new Font("Arial", Font.PLAIN, 20);
    private Font categorySmallFont = new Font("Arial", Font.PLAIN, 14);
    private Color shadowColor=new Color(0,0,0);;
    private float shadowOffsetX=0f;
    private float shadowOffsetY=0f;
    private float shadowOpacity=.5f;
    private float categorySmallOpacity=.8f;
    private Image buttonHighlight;
    private MouseAdapter ml;
    protected float anchoDeBorde=4f;
    protected Color colorDeBorde = new Color(173,173,173);
    protected Color colorDeSegundoBorde = Color.WHITE;
    private boolean redondeado=false;
    private boolean foco=false;
    private Dimension buttonDimension= new Dimension(116, 35);
    private Thread zoommer;

    public LabelTask(){
        this("Nombre", "Descripcion", "/resources/task-view-trip.png");
        computeDimension();
    }

    public LabelTask(String name) {
        this(name, "Descripcion", "/resources/task-view-trip.png");
    }

    public LabelTask(String name, String description) {
        this(name, description, "/resources/task-view-trip.png");
    }

    public LabelTask(String name, String description, Icon icon) {
        this(name, description, "/resources/task-view-trip.png");
        setIcon(icon);
    }

    public LabelTask(String name, Icon icon) {
        this(name, "Descripcion", "/resources/task-view-trip.png");
        setIcon(icon);
    }


   public LabelTask(String name, String description, String imageName) {
       setText(name);
       this.description = description;
       this.imageName = imageName;

       setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
       setOpaque(false);

       getImage();


       setSize(new Dimension(242, 64));
       setPreferredSize(new Dimension(242, 64));
       buttonHighlight = loadImage("/resources/header-halo.png");
       ml = new MouseAdapter() {
           @Override
           public void mouseEntered(MouseEvent e) {
                zoom();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                zoom();
            }
        };
        setUI(new BasicLabelUI() {
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
        addMouseListener(ml);
        addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                foco=true;
            }
            public void focusLost(FocusEvent e) {
                foco=false;
            }
        });
   }

   @Override
    public JToolTip createToolTip() {
        ToolTipRound tip = new ToolTipRound();
        tip.setComponent(this);
        return tip;
    }
   
    private static Image loadImage(String fileName) {
        try {
            return ImageIO.read(LabelTask.class.getResource(fileName));
        } catch (IOException ex) {
            return null;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void getImage() {

        if(getIcon()!=null){
            image=GraphicsUtil.toBufferedImage(((ImageIcon)getIcon()).getImage());
        }else
            try {
                this.image = ImageIO.read(getClass().getResource(imageName));
            } catch (IOException e) {
            }
        BufferedImage mask = Reflection.createGradientMask(image.getWidth(),
                image.getHeight());
        this.image = Reflection.createReflectedPicture(image, mask);
    }

    private Dimension computeDimension() {
        Insets insets = getInsets();

        FontMetrics metrics = getFontMetrics(categoryFont);
        Rectangle2D bounds = metrics.getMaxCharBounds(getGraphics());
        int height = (int) bounds.getHeight() + metrics.getLeading();
        int nameWidth = SwingUtilities.computeStringWidth(metrics, getText());

        metrics = getFontMetrics(categorySmallFont);
        bounds = metrics.getMaxCharBounds(getGraphics());
        height += bounds.getHeight();
        int descWidth = SwingUtilities.computeStringWidth(metrics,
                                                          description == null ? "" : description);

        int width = Math.max(nameWidth, descWidth);
        width += image.getWidth() + 10;

        clickable = new Rectangle(insets.left, insets.top /*+ 4*/,
                                  width /*+ insets.left + insets.right*/,
                                  height /*+ insets.top + insets.bottom*/);
        HyperlinkHandler.add(this, clickable);

        height = Math.max(height, image.getHeight());
        height += 4;

        return new Dimension(width + insets.left + insets.right,
                             height + insets.top + insets.bottom);
        }

    @Override
    protected void paintComponent(Graphics g) {
            if (!isVisible()) {
                return;
            }

            Graphics2D g2 = (Graphics2D) g;
            setupGraphics(g2);

            Paint oldpaint = g2.getPaint();
            if(isOpaque()){
                float x1=0,x2=0,y1=0,y2=getHeight();
                int x = 2, y = 2;
                int w = getWidth() - 6, h = getHeight() - 6;
                g2.setPaint(new GradientPaint(x1,y1,getBackground(), x2, y2, getBackground().darker()));
                if(redondeado)
                    g2.fillRoundRect(x , y , w , h , h / 3, h / 3);
                else
                    g2.fillRect(x, y, w, h);
                g2.setPaint(oldpaint);
                
            }

            float y = paintText(g2);
            getImage();
            paintImage(g2, y);
        }

    @Override
    protected void paintBorder(Graphics g) {
        if(!isOpaque())
            return ;
        int x = 2, y = 2;
        int w = getWidth() - 6, h = getHeight() - 5;
        if(redondeado){
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setStroke(new BasicStroke(anchoDeBorde));
            g2.setColor(colorDeBorde);
            g2.drawRoundRect(x, y, w, h, h / 3, h / 3);

            g2.setStroke(new BasicStroke(1.7f));
            g2.setColor(colorDeSegundoBorde);
            g2.drawRoundRect(x + 2, y + 2, w - 4, h - 4, h / 3, h / 3);

            g2.dispose();
        }else{
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setStroke(new BasicStroke(anchoDeBorde));
            g2.setColor(colorDeBorde);
            g2.drawRect(x, y, w, h);

            g2.setStroke(new BasicStroke(1.7f));
            g2.setColor(colorDeSegundoBorde);
            g2.drawRect(x + 2, y + 2, w - 4, h - 4);

            g2.dispose();
        }

    }

    private void paintImage(Graphics2D g2, float y) {
        Insets insets = getInsets();

        if (ghostValue > 0.0f) {
            int newWidth = (int) (image.getWidth() * (1.0 + ghostValue / 2.0));
            int newHeight = (int) (image.getHeight() * (1.0 + ghostValue / 2.0));

            Composite composite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       0.7f * (1.0f - ghostValue)));
            g2.drawImage(image,
                         insets.left + (image.getWidth() - newWidth) / 2,
                         4 + (int) (y - newHeight / (5.0 / 3.0)) -
                         (image.getWidth() - newWidth) / 2,
                         newWidth, newHeight, null);
            g2.setComposite(composite);
        }

        g2.drawImage(image, null,
                     insets.left,
                     4 + (int) (y - image.getHeight() / (5.0 / 3.0)));
        if(foco){
            g2.drawImage(buttonHighlight,
                0,0,
                getWidth(), getHeight(), null);
        }
    }

    private float paintText(Graphics2D g2) {
        g2.setFont(categoryFont);

        Insets insets = getInsets();

        FontRenderContext context = g2.getFontRenderContext();
        TextLayout layout = new TextLayout(getText(),
                                           categoryFont, context);

        float x = image.getWidth() + 10.0f;
        x += insets.left;
        float y = 4.0f + layout.getAscent() - layout.getDescent();
        y += insets.top;

        g2.setColor(shadowColor);
        Composite composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                   shadowOpacity));
        layout.draw(g2, shadowOffsetX + x, shadowOffsetY + y);
        g2.setComposite(composite);
        g2.setColor(getForeground());
        layout.draw(g2, x, y);
        y += layout.getDescent();

        layout = new TextLayout(description == null ? " " : description,
                                categorySmallFont, context);
        y += layout.getAscent();
        composite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                categorySmallOpacity));
        layout.draw(g2, x, y);
        g2.setComposite(composite);

        return y;
    }

    private void setupGraphics(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    }

    private void zoom(){
        if(zoommer!=null){
            if(zoommer.isAlive())
            return;
        }
     zoommer = new Thread(new Runnable() {
        public void run() {
            for(float i=0;i<=1;i+=0.1){
                try {
                    Thread.sleep(30 + (int) (Math.random() * 100));
                    ghostValue=i;
                    repaint();
                } catch (InterruptedException ex) {
                }
            }
            try {
                Thread.sleep(30 + (int) (Math.random() * 100));
            } catch (InterruptedException ex) {
                Logger.getLogger(LabelTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            ghostValue=0;
            repaint();
        }
    }
    );
    zoommer.start();
}

    public Font getCategoryFont() {
        return categoryFont;
    }

    public void setCategoryFont(Font categoryFont) {
        this.categoryFont = categoryFont;
    }

    public Font getCategorySmallFont() {
        return categorySmallFont;
    }

    public void setCategorySmallFont(Font categorySmallFont) {
        this.categorySmallFont = categorySmallFont;
    }

    public float getAnchoDeBorde() {
        return anchoDeBorde;
    }

    public void setAnchoDeBorde(float anchoDeBorde) {
        this.anchoDeBorde = anchoDeBorde;
    }

    public Color getColorDeBorde() {
        return colorDeBorde;
    }

    public void setColorDeBorde(Color colorDeBorde) {
        this.colorDeBorde = colorDeBorde;
    }

    public Color getColorDeSegundoBorde() {
        return colorDeSegundoBorde;
    }

    public void setColorDeSegundoBorde(Color colorDeSegundoBorde) {
        this.colorDeSegundoBorde = colorDeSegundoBorde;
    }

    public boolean isRedondeado() {
        return redondeado;
    }

    public void setRedondeado(boolean redondeado) {
        this.redondeado = redondeado;
    }
}

