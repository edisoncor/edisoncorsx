/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.edisoncor.gui.util;

import java.awt.Image;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 *
 * @author edisoncor
 */
public class Avatar {

    protected String id;
    public static final String PROP_ID = "id";
    protected String titulo;
    public static final String PROP_TITULO = "titulo";
    protected Image image;
    public static final String PROP_IMAGE = "Image";

    public Avatar(String id) {
        this.id = id;
    }

    public Avatar(String id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Avatar(String id, String titulo, Image image) {
        this.id = id;
        this.titulo = titulo;
        this.image = image;
    }

    public Avatar(String titulo, Image image) {
        this.titulo = titulo;
        this.image = image;
    }

    public Avatar(Image image) {
        this.image = image;
    }

    
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        Image oldImage = this.image;
        this.image = image;
        propertyChangeSupport.firePropertyChange(PROP_IMAGE, oldImage, image);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        String oldTitulo = this.titulo;
        this.titulo = titulo;
        propertyChangeSupport.firePropertyChange(PROP_TITULO, oldTitulo, titulo);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        String oldId = this.id;
        this.id = id;
        propertyChangeSupport.firePropertyChange(PROP_ID, oldId, id);
    }
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

}
