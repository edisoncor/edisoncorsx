/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import org.edisoncor.gui.label.LabelMetric;
import org.edisoncor.gui.panel.Panel;

/**
 *
 * @author edison
 */
public class BasicTableCellenderer extends Panel implements TableCellRenderer {

    private LabelMetric label = new LabelMetric();
    private Color backgroundSelected = Color.BLUE;
    private Color backgroundNoSelected = new Color(32,39,55);
    private Color backgroundFocus = Color.YELLOW;
    private Color backgroundSelectedFocus = Color.RED;

    public BasicTableCellenderer() {
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


    public Component getTableCellRendererComponent(JTable table,
            Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        try {
            label.setText(value.toString());
        } catch (Exception e) {
            label.setText("");
        }
        table.setRowHeight(38);
        if(isSelected){
            setColorPrimario(backgroundSelected);
        }else{
            setColorPrimario(backgroundNoSelected);
        }
        if(hasFocus){
            setColorPrimario(backgroundFocus);
        }
        if(isSelected & hasFocus){
            setColorPrimario(backgroundSelectedFocus);
        }

        return this;
    }

    public Color getBackgroundFocus() {
        return backgroundFocus;
    }

    public void setBackgroundFocus(Color backgroundFocus) {
        this.backgroundFocus = backgroundFocus;
    }

    public Color getBackgroundNoSelected() {
        return backgroundNoSelected;
    }

    public void setBackgroundNoSelected(Color backgroundNoSelected) {
        this.backgroundNoSelected = backgroundNoSelected;
    }

    public Color getBackgroundSelected() {
        return backgroundSelected;
    }

    public void setBackgroundSelected(Color backgroundSelected) {
        this.backgroundSelected = backgroundSelected;
    }

    public Color getBackgroundSelectedFocus() {
        return backgroundSelectedFocus;
    }

    public void setBackgroundSelectedFocus(Color backgroundSelectedFocus) {
        this.backgroundSelectedFocus = backgroundSelectedFocus;
    }

   

}
