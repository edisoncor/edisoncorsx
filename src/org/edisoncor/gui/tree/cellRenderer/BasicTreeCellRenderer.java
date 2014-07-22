/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.tree.cellRenderer;
/*
 * TreeCellRenderer.java
 *
 * Created on 21/10/2007, 10:50:19 PM
 *
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import org.edisoncor.gui.label.LabelMetric;
import org.edisoncor.gui.panel.PanelLlamada;

/**
 *
 * @author Edisoncor
 */
public class BasicTreeCellRenderer extends PanelLlamada implements  TreeCellRenderer{

    private Color background;
    private Color foreground;
    private Color backgroundExpandido = Color.RED;
    private Color foregroundExpandido = Color.BLUE;
    private Color backgroundSelectedLeaf = Color.GREEN;
    private Color foregroundSelectedLeaf = Color.BLACK;
    private Color backgroundFocus = Color.BLUE;
    private Color foregroundFocus = Color.WHITE;
    private Color backgroundLeaf = Color.ORANGE;
    private Color foregroundLeaf = Color.BLACK;
    private LabelMetric label = new LabelMetric();

    public BasicTreeCellRenderer() {
         javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(this);
        setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    

    public Component getTreeCellRendererComponent(JTree tree,
            Object value,
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

        label.setText(value.toString());
        setOrientacion(Orientacion.LEFT);
        setAncho(7);

         if (expanded) {
             background = backgroundExpandido;
             foreground = foregroundExpandido;
         }
        if (selected && leaf) {
             background = backgroundSelectedLeaf.darker();
             foreground = foregroundSelectedLeaf;
         } else {
             background =  Color.YELLOW;
             foreground = Color.BLACK;
         }

         if (hasFocus){
             background = backgroundFocus.darker();
             foreground = foregroundFocus;
         }
        if (leaf){
            background = backgroundLeaf;
            foreground = foregroundLeaf;
            setOrientacion(Orientacion.TOP);
        }
        JTree.DropLocation dropLocation = tree.getDropLocation();
        if (dropLocation != null
             && dropLocation.getChildIndex() == -1
             && tree.getRowForPath(dropLocation.getPath()) == row) {

         background = backgroundExpandido;
         foreground = foregroundExpandido;
        }

         if (selected && leaf) {
             background = backgroundSelectedLeaf.darker();
             foreground = foregroundSelectedLeaf;
         }


        setBackground(background);
        setForeground(foreground);

         setColorPrimario(background);
         return this;

    }

    public Color getBackgroundExpandido() {
        return backgroundExpandido;
    }

    public void setBackgroundExpandido(Color backgroundExpandido) {
        this.backgroundExpandido = backgroundExpandido;
    }

    public Color getBackgroundFocus() {
        return backgroundFocus;
    }

    public void setBackgroundFocus(Color backgroundFocus) {
        this.backgroundFocus = backgroundFocus;
    }

    public Color getBackgroundLeaf() {
        return backgroundLeaf;
    }

    public void setBackgroundLeaf(Color backgroundLeaf) {
        this.backgroundLeaf = backgroundLeaf;
    }

    public Color getBackgroundSelectedLeaf() {
        return backgroundSelectedLeaf;
    }

    public void setBackgroundSelectedLeaf(Color backgroundSelectedLeaf) {
        this.backgroundSelectedLeaf = backgroundSelectedLeaf;
    }

    public Color getForegroundExpandido() {
        return foregroundExpandido;
    }

    public void setForegroundExpandido(Color foregroundExpandido) {
        this.foregroundExpandido = foregroundExpandido;
    }

    public Color getForegroundFocus() {
        return foregroundFocus;
    }

    public void setForegroundFocus(Color foregroundFocus) {
        this.foregroundFocus = foregroundFocus;
    }

    public Color getForegroundLeaf() {
        return foregroundLeaf;
    }

    public void setForegroundLeaf(Color foregroundLeaf) {
        this.foregroundLeaf = foregroundLeaf;
    }

    public Color getForegroundSelectedLeaf() {
        return foregroundSelectedLeaf;
    }

    public void setForegroundSelectedLeaf(Color foregroundSelectedLeaf) {
        this.foregroundSelectedLeaf = foregroundSelectedLeaf;
    }

    public LabelMetric getLabel() {
        return label;
    }

    public void setLabel(LabelMetric label) {
        this.label = label;
    }



}