/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.varios;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Calendar;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 *
 * @author edison
 */
public  class ClockFace extends JComponent {
        private Stroke border;
        private Stroke secondHand;
        private Stroke minuteHand;
        private Stroke hourHand;
        private Stroke ticks;
        private boolean romano = true;

        public ClockFace() {
            setPreferredSize(new Dimension(150, 150));
            setSize(new Dimension(150, 150));
            setOpaque(false);
            Timer timer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    repaint();
                }
            });
            timer.setRepeats(true);
            timer.start();
        }

        private String getRomanNumeral(int number) {
            switch(number) {
            case 1: return "I";
            case 2: return "II";
            case 3: return "III";
            case 4: return "IV";
            case 5: return "V";
            case 6: return "VI";
            case 7: return "VII";
            case 8: return "VIII";
            case 9: return "IX";
            case 10: return "X";
            case 11: return "XI";
            case 12:
            default: return "XII";
            }
        }

        private String getGregorianNumeral(int number) {
            switch(number) {
            case 1: return "1";
            case 2: return "2";
            case 3: return "3";
            case 4: return "4";
            case 5: return "5";
            case 6: return "6";
            case 7: return "7";
            case 8: return "8";
            case 9: return "9";
            case 10: return "10";
            case 11: return "11";
            case 12:
            default: return "12";
            }
        }

    public boolean isRomano() {
        return romano;
    }

    public void setRomano(boolean romano) {
        this.romano = romano;
    }
        

        @Override
        protected void paintComponent(Graphics graphics) {
            paintFace(graphics, Math.min(getWidth(), getHeight()));
        }

        protected void paintFace(Graphics graphics, int size) {
            Point center = new Point(size/2, size/2);
            int radius = center.x;
            int margin = radius / 20;

            int w = size;
            border = new BasicStroke(Math.max(1f, w/150f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            secondHand = new BasicStroke(Math.max(1f, w/75f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            minuteHand = new BasicStroke(Math.max(1f, w/38f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            hourHand = new BasicStroke(Math.max(1.5f, w/20f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
            ticks = new BasicStroke(1f);

            Graphics2D g = (Graphics2D)graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                               RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING,
                               RenderingHints.VALUE_RENDER_QUALITY);
            Color bg = getBackground();
            g.setColor(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));

            g.fill(new Ellipse2D.Float(0,0,size,size));
            Font font = getFont();
            g.setFont(font.deriveFont(Font.BOLD, size/12));
            g.setColor(new Color(0,0,0,128));
            g.setStroke(border);
            g.draw(new Ellipse2D.Float(0,0,size-1,size-1));
            g.draw(new Ellipse2D.Float(margin,margin,size-margin*2-1,size-margin*2-1));

            Calendar c = Calendar.getInstance();
            int minute = c.get(Calendar.MINUTE);
            int hour = c.get(Calendar.HOUR);
            int second = c.get(Calendar.SECOND);
            g.translate(center.x, center.y);
            g.setColor(getForeground());
            int numbers = radius * 3 / 4;
            for (int i=0;i < 12;i++) {
                double theta = Math.PI*2*i/12;
                String str = getRomanNumeral((i+2)%12+1);
                if (romano)
                    str = getRomanNumeral((i+2)%12+1);
                else
                    str = getGregorianNumeral((i+2)%12+1);
                Rectangle2D rect = g.getFontMetrics().getStringBounds(str, g);
                g.drawString(str, Math.round(numbers*Math.cos(theta)-rect.getWidth()/2),
                             Math.round(numbers*Math.sin(theta)+margin*2));
            }
            for (int i=0;i < 60;i++) {
                g.setColor(getForeground());
                g.setStroke(ticks);
                g.drawLine(radius-margin*2, 0, radius-margin, 0);
                if ((i % 5) == 0) {
                    g.drawLine(radius-margin*3, 0, radius-margin, 0);
                }
                if ((i + 15) % 60 == minute) {
                    g.setStroke(minuteHand);
                    g.drawLine(0, 0, radius-margin*4, 0);
                }
                if ((i + 15) % 60 == (hour * 5 + minute * 5 / 60)) {
                    g.setStroke(hourHand);
                    g.drawLine(0, 0, radius/2, 0);
                }
                if ((i + 15) % 60 == second) {
                    g.setColor(new Color(255, 0, 0, 128));
                    g.setStroke(secondHand);
                    g.drawLine(0, 0, radius-margin*4, 0);
                }
                g.rotate(Math.PI*2/60);
            }
            g.dispose();
        }


    }
