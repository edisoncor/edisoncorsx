/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.edisoncor.gui.list.cellRenderer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.edisoncor.gui.label.LabelMetric;
import org.edisoncor.gui.panel.Panel;

/**
 *
 * @author edison
 */
public class BasicCellRenderer extends Panel implements ListCellRenderer{

    private Color normal = new Color(32,39,55);
    private Color seleccionado = new Color(98,98,121);
    private Color foco = new Color(93,93,176);
    private Color textoNormal = new Color(102,102,102);
    private Color textoSeleccionado = Color.white;
    private Color colorBorde = Color.white;
    private LabelMetric label = new LabelMetric();
    private boolean degradado = true;

    public BasicCellRenderer() {
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

    public Component getListCellRendererComponent(JList jlist,
            Object value, int index, boolean isSelected, boolean cellhasFocus) {

        label.setText(value.toString());
        if(isSelected){
            setColorPrimario(seleccionado);
            if (!degradado)
                setColorSecundario(seleccionado);
            else
                setColorSecundario(Color.black);
            label.setForeground(textoSeleccionado);
        }else{
            setColorPrimario(normal);
            if (!degradado)
                setColorSecundario(normal);
            else
                setColorSecundario(Color.black);
            label.setForeground(textoNormal);
        }
        if(cellhasFocus){
            setColorPrimario(foco);
            if(!degradado)
                setColorSecundario(foco);
            else
                setColorSecundario(Color.black);
            label.setForeground(textoSeleccionado);
        }

        return this;
    }

    public Color getFoco() {
        return foco;
    }

    public void setFoco(Color foco) {
        this.foco = foco;
    }

    public Color getNormal() {
        return normal;
    }

    public void setNormal(Color normal) {
        this.normal = normal;
    }

    public Color getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Color seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Color getTextoNormal() {
        return textoNormal;
    }

    public void setTextoNormal(Color textoNormal) {
        this.textoNormal = textoNormal;
    }

    public Color getTextoSeleccionado() {
        return textoSeleccionado;
    }

    public void setTextoSeleccionado(Color textoSeleccionado) {
        this.textoSeleccionado = textoSeleccionado;
    }

}
