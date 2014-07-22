package org.edisoncor.gui.panel;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.edisoncor.gui.util.ShadowFactory;



public class PanelSelector extends JPanel {
    private final DetailsPanel detailsPanel;
    private final SelectorList selectorList;
    private final DefaultListModel selectorListModel;

    private float shadowOffsetX;
    private float shadowOffsetY;


    ////////////////////////////////////////////////////////////////////////////
    // THEME SPECIFIC FIELDS
    ////////////////////////////////////////////////////////////////////////////
    private Color selectionColor= Color.WHITE;
    private Color listColor= Color.WHITE;
    private Color detailsColor= Color.WHITE;
    private Color listItemColor= Color.WHITE;
    private Color screenshotColor= Color.WHITE;
    private Color screenshotTextColor= Color.black;

    private float selectionOpacity=0.2f, selectionBorderOpacity=0.8f, listOpacity=0.05f,
                  listBorderOpacity=0.4f, listUnselectedItemOpacity=0.85f;
    
    private Font listItemFont = new Font("Arial",Font.BOLD,16);
    private Font listSelectedItemFont = new Font("Arial",Font.BOLD,18);
    private Font screenshotFont= new Font("Arial",Font.BOLD,16);
    
    private Color shadowColor = Color.BLACK;
    private float shadowOpacity=0.5f;
    private int shadowDistance=3;
    private int shadowDirection=60;
    private Font detailsBigFont= new Font("Arial",Font.BOLD,30);
    private Font detailsSmallFont= new Font("Arial",Font.BOLD,14);
    private float screenshotOpacity=0.7f;
    private Dimension screenshotDimension= new Dimension(200, 167);



    public PanelSelector() {
        super(new BorderLayout(0, 0));
        setOpaque(false);
        computeShadow();

        selectorListModel = new DefaultListModel();
        selectorList = new SelectorList(selectorListModel);
        detailsPanel = new DetailsPanel();

        add(BorderLayout.CENTER, detailsPanel);
        add(BorderLayout.EAST, selectorList);
        setPreferredSize(new Dimension(200,200));
    }

    private void computeShadow() {
        double rads = Math.toRadians(shadowDirection);
        shadowOffsetX = (float) Math.cos(rads) * shadowDistance;
        shadowOffsetY = (float) Math.sin(rads) * shadowDistance;
    }
   
    void removeAllSelector() {
        selectorListModel.removeAllElements();
    }

    public void addSelector(JPanel selector) {
        if (selectorListModel.size() >= 9) {
            return;
        }
        selectorListModel.addElement(selector);
    }

    void defaultSelection() {
        if (selectorListModel.size() > 0) {
            selectorList.setSelectedIndex(0);
        } else {
            detailsPanel.showSelectorDetails(null);
        }
    }

    public Font getDetailsBigFont() {
        return detailsBigFont;
    }

    public void setDetailsBigFont(Font detailsBigFont) {
        this.detailsBigFont = detailsBigFont;
        repaint();
    }

    public Color getDetailsColor() {
        return detailsColor;
    }

    public void setDetailsColor(Color detailsColor) {
        this.detailsColor = detailsColor;
        repaint();
    }

    public Font getDetailsSmallFont() {
        return detailsSmallFont;
    }

    public void setDetailsSmallFont(Font detailsSmallFont) {
        this.detailsSmallFont = detailsSmallFont;
        repaint();
    }

    public Color getListColor() {
        return listColor;
    }

    public void setListColor(Color listColor) {
        this.listColor = listColor;
        repaint();
    }

    public Color getListItemColor() {
        return listItemColor;
    }

    public void setListItemColor(Color listItemColor) {
        this.listItemColor = listItemColor;
        repaint();
    }

    public Font getListItemFont() {
        return listItemFont;
    }

    public void setListItemFont(Font listItemFont) {
        this.listItemFont = listItemFont;
        repaint();
    }

    public Font getListSelectedItemFont() {
        return listSelectedItemFont;
    }

    public void setListSelectedItemFont(Font listSelectedItemFont) {
        this.listSelectedItemFont = listSelectedItemFont;
        repaint();
    }

    public Font getScreenshotFont() {
        return screenshotFont;
    }

    public void setScreenshotFont(Font screenshotFont) {
        this.screenshotFont = screenshotFont;
        repaint();
    }

    public float getScreenshotOpacity() {
        return screenshotOpacity;
    }

    public void setScreenshotOpacity(float screenshotOpacity) {
        this.screenshotOpacity = screenshotOpacity;
        repaint();
    }

    public Color getScreenshotTextColor() {
        return screenshotTextColor;
    }

    public void setScreenshotTextColor(Color screenshotTextColor) {
        this.screenshotTextColor = screenshotTextColor;
        repaint();
    }

    public Color getSelectionColor() {
        return selectionColor;
    }

    public void setSelectionColor(Color selectionColor) {
        this.selectionColor = selectionColor;
        repaint();
    }

    public Color getShadowColor() {
        return shadowColor;
    }

    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        repaint();
    }

    public Color getScreenshotColor() {
        return screenshotColor;
    }

    public void setScreenshotColor(Color screenshotColor) {
        this.screenshotColor = screenshotColor;
        repaint();
    }

    public float getSelectionBorderOpacity() {
        return selectionBorderOpacity;
    }

    public void setSelectionBorderOpacity(float selectionBorderOpacity) {
        this.selectionBorderOpacity = selectionBorderOpacity;
        repaint();
    }

    public float getSelectionOpacity() {
        return selectionOpacity;
    }

    public void setSelectionOpacity(float selectionOpacity) {
        this.selectionOpacity = selectionOpacity;
        repaint();
    }

    public int getShadowDirection() {
        return shadowDirection;
    }

    public void setShadowDirection(int shadowDirection) {
        this.shadowDirection = shadowDirection;
        repaint();
    }

    public int getShadowDistance() {
        return shadowDistance;
    }

    public void setShadowDistance(int shadowDistance) {
        this.shadowDistance = shadowDistance;
        repaint();
    }

    public float getShadowOpacity() {
        return shadowOpacity;
    }

    public void setShadowOpacity(float shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
        repaint();
    }


    private class SelectorList extends JList {
        private SelectorList(ListModel model) {
            super(model);
            setOpaque(false);
            setFont(listItemFont);
            setCellRenderer(new SelectorsListCellRenderer());

            addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    Rectangle bounds = getCellBounds(e.getFirstIndex(),
                                                     e.getLastIndex());
                    if (bounds == null) {
                        return;
                    }
                    detailsPanel.repaint(detailsPanel.getWidth() - 20,
                                        bounds.y - 6,
                                        20,
                                        bounds.height + 12);

                    if (!e.getValueIsAdjusting()) {
                        JPanel selector = (JPanel) getSelectedValue();
                        detailsPanel.showSelectorDetails(selector);
                    }
                }
            });
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension parentSize = super.getPreferredSize();
            Dimension containerSize = PanelSelector.this.getSize();

            return new Dimension(containerSize.width / 3,
                                 parentSize.height);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            Composite composite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       listOpacity));

            g2.setColor(listColor);

            RoundRectangle2D background;
            background = new RoundRectangle2D.Double(3.0, 3.0,
                                                     (double) getWidth() - 18.0,
                                                     (double) getHeight() - 6.0,
                                                     10, 10);
            g2.fill(background);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       listBorderOpacity));
            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(3.0f));
            g2.draw(background);
            g2.setStroke(stroke);

            g2.setComposite(composite);

            super.paintComponent(g);
        }
    }

    private class SelectorsListCellRenderer extends DefaultListCellRenderer {
        private boolean isSelected;
        private int index;

        private final Icon[] ratings = new Icon[6];

        private SelectorsListCellRenderer() {
            super();
        }

        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            return size;
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            this.index = index;
            this.isSelected = isSelected;
            String titulo = ((JPanel)value).getName()==null? "titulo " + index:((JPanel)value).getName();
            super.getListCellRendererComponent(list,
                                               titulo,
                                               index, isSelected,
                                               cellHasFocus);

            setOpaque(false);
            setForeground(listItemColor);

            int top = index == 0 ? 19 : 5;
            int left = 10;
            int bottom = index == selectorListModel.size() - 1 ? 19 : 5;
            int right = 10;
            setBorder(BorderFactory.createEmptyBorder(top, left, bottom, right));

            if (isSelected) {
                setFont(listSelectedItemFont);
            }
            return this;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            if (isSelected) {
                paintSelection(g2);
            }
            Point p = paintText(g2);
            paintIcon(g2, p.x, p.y);
        }

        private void paintIcon(Graphics2D g2, int x, int y) {
        }

        private void paintSelection(Graphics2D g2) {
            Composite composite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       selectionOpacity));

            g2.setColor(selectionColor);

            RoundRectangle2D background;
            double y = 2.0;
            double height = (double) getHeight() - 6.0;
            if (index == 0 || index == selectorListModel.size() - 1) {
                height -= 14;
            }
            if (index == 0) {
                y += 14.0;
            }
            background = new RoundRectangle2D.Double(-6.0, y,
                                                     (double) getWidth() + 3.0,
                                                     height,
                                                     12, 12);
            g2.fill(background);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       selectionBorderOpacity));
            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(3.0f));
            g2.draw(background);
            g2.setStroke(stroke);

            g2.setComposite(composite);
        }

        private Point paintText(Graphics2D g2) {
            FontMetrics fm = getFontMetrics(getFont());
            int x = getInsets().left;
            int y = getInsets().top + fm.getAscent();

            Composite composite = g2.getComposite();

            if (isSelected) {
                y -= 2;
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                           shadowOpacity));
            } else {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                           listUnselectedItemOpacity));
            }

            g2.setColor(shadowColor);
            TextLayout layout = new TextLayout(getText(),
                                               getFont(),
                                               g2.getFontRenderContext());
            layout.draw(g2,
                        x + (int) Math.ceil(shadowOffsetX),
                        y + (int) Math.ceil(shadowOffsetY));

            if (isSelected) {
                g2.setComposite(composite);
            }

            g2.setColor(getForeground());
            layout.draw(g2, x, y);

            if (!isSelected) {
                g2.setComposite(composite);
            }

            return new Point(x, y + fm.getDescent());
        }
    }

    public class DetailsPanel extends JPanel {
        private JPanel selector;
        private ShadowFactory factory;
        private JPanel head, description;

        private DetailsPanel() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(10, 20, 17, 21));
            setLayout(new BorderLayout());
            factory = new ShadowFactory(shadowDistance + 2,
                                        shadowOpacity,
                                        shadowColor);
        }

        private void showSelectorDetails(final JPanel selector) {
            if (selector == null) {
                return;
            }

                    removeAll();

                    DetailsPanel.this.selector = selector;
                    selector.setOpaque(false);
                    add(selector, BorderLayout.CENTER);
                    

                    revalidate();
                    repaint();
        }

        
       

       
        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            super.paint(g);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;

            Composite composite = g2.getComposite();
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       selectionOpacity));

            g2.setColor(selectionColor);

            RoundRectangle2D background;
            background = new RoundRectangle2D.Double(3.0, 3.0,
                                                     (double) getWidth() - 10.0 - 3.0,
                                                     (double) getHeight() - 6.0,
                                                     12, 12);
            Area area = new Area(background);
            area.add(new Area(getSelectedRectangle()));
            g2.fill(area);

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                       selectionBorderOpacity));
            Stroke stroke = g2.getStroke();
            g2.setStroke(new BasicStroke(3.0f));
            g2.draw(area);
            g2.setStroke(stroke);

            g2.setComposite(composite);
        }

        private Shape getSelectedRectangle() {
            Rectangle bounds = null;
            int selectedIndex = 0;
            try {
                selectedIndex = selectorList.getSelectedIndex();
                bounds = selectorList.getCellBounds(selectedIndex, selectedIndex);
            } catch (Exception e) {
            }
            if (bounds == null) {
                return new RoundRectangle2D.Double(0.0, 0.0, 0.0, 0.0, 1.0, 1.0);
            }

            float x = getWidth() - 10.0f;
            float y = 2.0f + bounds.y;
            float height = bounds.height - 6.0f;

            if (selectedIndex == 0 || selectedIndex == selectorListModel.size() - 1) {
                height -= 14.0f;
            }
            if (selectedIndex == 0) {
                y += 14.0f;
            }

            GeneralPath gp = new GeneralPath();
            gp.moveTo(x - 5.0f, y- 6.0f);
            gp.lineTo(x, y - 6.0f);
            gp.quadTo(x, y, x + 6.0f, y);
            gp.lineTo(x + 20.0f, y);
            gp.lineTo(x + 20.0f, y + height);
            gp.lineTo(x + 6.0f, y + height);
            gp.quadTo(x, y + height, x, y + height + 6.0f);
            gp.lineTo(x - 5.0f, y + height + 6.0f);

            return gp;
        }

        
    }

}
