/*
 * 
 * Copyright 2001-2004 The Apache Software Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 */

package org.edisoncor.gui.util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ComponentSampleModel;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.awt.image.Kernel;
import java.awt.image.PixelGrabber;
import java.awt.image.SampleModel;
import java.awt.image.SinglePixelPackedSampleModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class GraphicsUtil {
    private static GraphicsConfiguration configuration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

    private GraphicsUtil() {
    }

    public static BufferedImage createCompatibleImage(int width, int height) {
        return configuration.createCompatibleImage(width, height);
    }

    public static BufferedImage createTranslucentCompatibleImage(int width, int height) {
        return configuration.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }
    
    public static BufferedImage loadCompatibleImage(URL resource) throws IOException {
        BufferedImage image = ImageIO.read(resource);
        return toCompatibleImage(image);
    }
    
    public static BufferedImage toCompatibleImage(BufferedImage image) {
        BufferedImage compatibleImage = configuration.createCompatibleImage(image.getWidth(),
                image.getHeight(), Transparency.TRANSLUCENT);
        Graphics g = compatibleImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return compatibleImage;
    }
    
    public static BufferedImage createThumbnail(BufferedImage image, int requestedThumbSize) {
        float ratio = (float) image.getWidth() / (float) image.getHeight();
        int width = image.getWidth();
        BufferedImage thumb = image;
        
        do {
            width /= 2;
            if (width < requestedThumbSize) {
                width = requestedThumbSize;
            }
            
            BufferedImage temp = new BufferedImage(width, (int) (width / ratio), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();

            thumb = temp;
        } while (width != requestedThumbSize);
        
        return thumb;
    }
    
    public static Image loadImage(String fileName){
        try {
            return ImageIO.read(GraphicsUtil.class.getResource(fileName));
        } catch (Exception e) {
            return null;
        }
    }
    
    public static BufferedImage createThumbnail(BufferedImage image, int newWidth, int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage thumb = image;
        
        do {
            if (width > newWidth) {
                width /= 2;
                if (width < newWidth) {
                    width = newWidth;
                }
            }
            
            if (height > newHeight) {
                height /= 2;
                if (height < newHeight) {
                    height = newHeight;
                }
            }
            
            BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = temp.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(thumb, 0, 0, temp.getWidth(), temp.getHeight(), null);
            g2.dispose();

            thumb = temp;
        } while (width != newWidth || height != newHeight);
        
        return thumb;
    }
    
    /**
     * Create a new ColorModel with it's alpha premultiplied state matching
     * newAlphaPreMult.
     * 
     * @param cm
     *            The ColorModel to change the alpha premult state of.
     * @param newAlphaPreMult
     *            The new state of alpha premult.
     * @return A new colorModel that has isAlphaPremultiplied() equal to
     *         newAlphaPreMult.
     */
    public static ColorModel coerceColorModel(ColorModel cm,
                                              boolean newAlphaPreMult) {
        if (cm.isAlphaPremultiplied() == newAlphaPreMult)
            return cm;

        // Easiest way to build proper colormodel for new Alpha state...
        // Eventually this should switch on known ColorModel types and
        // only fall back on this hack when the CM type is unknown.
        WritableRaster wr = cm.createCompatibleWritableRaster(1, 1);
        return cm.coerceData(wr, newAlphaPreMult);
    }

    /**
     * Coerces data within a bufferedImage to match newAlphaPreMult, Note that
     * this can not change the colormodel of bi so you
     * 
     * @param wr
     *            The raster to change the state of.
     * @param cm
     *            The colormodel currently associated with data in wr.
     * @param newAlphaPreMult
     *            The desired state of alpha Premult for raster.
     * @return A new colormodel that matches newAlphaPreMult.
     */
    public static ColorModel coerceData(WritableRaster wr, ColorModel cm,
                                        boolean newAlphaPreMult) {

        // System.out.println("CoerceData: " + cm.isAlphaPremultiplied() +
        // " Out: " + newAlphaPreMult);
        if (!cm.hasAlpha())
            // Nothing to do no alpha channel
            return cm;

        if (cm.isAlphaPremultiplied() == newAlphaPreMult)
            // nothing to do alpha state matches...
            return cm;

        // System.out.println("CoerceData: " + wr.getSampleModel());

        if (newAlphaPreMult) {
            multiplyAlpha(wr);
        } else {
            divideAlpha(wr);
        }

        return coerceColorModel(cm, newAlphaPreMult);
    }

    public static void multiplyAlpha(WritableRaster wr) {
        if (is_BYTE_COMP_Data(wr.getSampleModel()))
            mult_BYTE_COMP_Data(wr);
        else if (is_INT_PACK_Data(wr.getSampleModel(), true))
            mult_INT_PACK_Data(wr);
        else {
            int[] pixel = null;
            int bands = wr.getNumBands();
            float norm = 1f / 255f;
            int x0, x1, y0, y1, a, b;
            float alpha;
            x0 = wr.getMinX();
            x1 = x0 + wr.getWidth();
            y0 = wr.getMinY();
            y1 = y0 + wr.getHeight();
            for (int y = y0; y < y1; y++)
                for (int x = x0; x < x1; x++) {
                    pixel = wr.getPixel(x, y, pixel);
                    a = pixel[bands - 1];
                    if ((a >= 0) && (a < 255)) {
                        alpha = a * norm;
                        for (b = 0; b < bands - 1; b++)
                            pixel[b] = (int) (pixel[b] * alpha + 0.5f);
                        wr.setPixel(x, y, pixel);
                    }
                }
        }
    }

    public static void divideAlpha(WritableRaster wr) {
        if (is_BYTE_COMP_Data(wr.getSampleModel()))
            divide_BYTE_COMP_Data(wr);
        else if (is_INT_PACK_Data(wr.getSampleModel(), true))
            divide_INT_PACK_Data(wr);
        else {
            int x0, x1, y0, y1, a, b;
            float ialpha;
            int bands = wr.getNumBands();
            int[] pixel = null;

            x0 = wr.getMinX();
            x1 = x0 + wr.getWidth();
            y0 = wr.getMinY();
            y1 = y0 + wr.getHeight();
            for (int y = y0; y < y1; y++)
                for (int x = x0; x < x1; x++) {
                    pixel = wr.getPixel(x, y, pixel);
                    a = pixel[bands - 1];
                    if ((a > 0) && (a < 255)) {
                        ialpha = 255 / (float) a;
                        for (b = 0; b < bands - 1; b++)
                            pixel[b] = (int) (pixel[b] * ialpha + 0.5f);
                        wr.setPixel(x, y, pixel);
                    }
                }
        }
    }

    public static boolean is_INT_PACK_Data(SampleModel sm, boolean requireAlpha) {
        // Check ColorModel is of type DirectColorModel
        if (!(sm instanceof SinglePixelPackedSampleModel))
            return false;

        // Check transfer type
        if (sm.getDataType() != DataBuffer.TYPE_INT)
            return false;

        SinglePixelPackedSampleModel sppsm;
        sppsm = (SinglePixelPackedSampleModel) sm;

        int[] masks = sppsm.getBitMasks();
        if (masks.length == 3) {
            if (requireAlpha)
                return false;
        } else if (masks.length != 4)
            return false;

        if (masks[0] != 0x00ff0000)
            return false;
        if (masks[1] != 0x0000ff00)
            return false;
        if (masks[2] != 0x000000ff)
            return false;
        return !((masks.length == 4) && (masks[3] != 0xff000000));

    }

    public static boolean is_BYTE_COMP_Data(SampleModel sm) {
        // Check ColorModel is of type DirectColorModel
        if (!(sm instanceof ComponentSampleModel))
            return false;

        // Check transfer type
        return sm.getDataType() == DataBuffer.TYPE_BYTE;

    }

    protected static void divide_INT_PACK_Data(WritableRaster wr) {
        // System.out.println("Divide Int");

        SinglePixelPackedSampleModel sppsm;
        sppsm = (SinglePixelPackedSampleModel) wr.getSampleModel();

        final int width = wr.getWidth();

        final int scanStride = sppsm.getScanlineStride();
        DataBufferInt db = (DataBufferInt) wr.getDataBuffer();
        final int base = (db.getOffset() + sppsm
                                                .getOffset(
                                                           wr.getMinX()
                                                                   - wr
                                                                       .getSampleModelTranslateX(),
                                                           wr.getMinY()
                                                                   - wr
                                                                       .getSampleModelTranslateY()));
        int pixel, a, aFP;
        // Access the pixel data array
        final int pixels[] = db.getBankData()[0];
        for (int y = 0; y < wr.getHeight(); y++) {
            int sp = base + y * scanStride;
            final int end = sp + width;
            while (sp < end) {
                pixel = pixels[sp];
                a = pixel >>> 24;
                if (a <= 0) {
                    pixels[sp] = 0x00FFFFFF;
                } else if (a < 255) {
                    aFP = (0x00FF0000 / a);
                    pixels[sp] = ((a << 24)
                                  | (((((pixel & 0xFF0000) >> 16) * aFP) & 0xFF0000))
                                  | (((((pixel & 0x00FF00) >> 8) * aFP) & 0xFF0000) >> 8) | (((((pixel & 0x0000FF)) * aFP) & 0xFF0000) >> 16));
                }
                sp++;
            }
        }
    }

    protected static void mult_INT_PACK_Data(WritableRaster wr) {
        // System.out.println("Multiply Int: " + wr);

        SinglePixelPackedSampleModel sppsm;
        sppsm = (SinglePixelPackedSampleModel) wr.getSampleModel();

        final int width = wr.getWidth();

        final int scanStride = sppsm.getScanlineStride();
        DataBufferInt db = (DataBufferInt) wr.getDataBuffer();
        final int base = (db.getOffset() + sppsm
                                                .getOffset(
                                                           wr.getMinX()
                                                                   - wr
                                                                       .getSampleModelTranslateX(),
                                                           wr.getMinY()
                                                                   - wr
                                                                       .getSampleModelTranslateY()));
        // Access the pixel data array
        final int pixels[] = db.getBankData()[0];
        for (int y = 0; y < wr.getHeight(); y++) {
            int sp = base + y * scanStride;
            final int end = sp + width;
            while (sp < end) {
                int pixel = pixels[sp];
                int a = pixel >>> 24;
                if ((a >= 0) && (a < 255)) {
                    pixels[sp] = ((a << 24)
                                  | ((((pixel & 0xFF0000) * a) >> 8) & 0xFF0000)
                                  | ((((pixel & 0x00FF00) * a) >> 8) & 0x00FF00) | ((((pixel & 0x0000FF) * a) >> 8) & 0x0000FF));
                }
                sp++;
            }
        }
    }

    protected static void divide_BYTE_COMP_Data(WritableRaster wr) {
        // System.out.println("Multiply Int: " + wr);

        ComponentSampleModel csm;
        csm = (ComponentSampleModel) wr.getSampleModel();

        final int width = wr.getWidth();

        final int scanStride = csm.getScanlineStride();
        final int pixStride = csm.getPixelStride();
        final int[] bandOff = csm.getBandOffsets();

        DataBufferByte db = (DataBufferByte) wr.getDataBuffer();
        final int base = (db.getOffset() + csm
                                              .getOffset(
                                                         wr.getMinX()
                                                                 - wr
                                                                     .getSampleModelTranslateX(),
                                                         wr.getMinY()
                                                                 - wr
                                                                     .getSampleModelTranslateY()));

        int a = 0;
        int aOff = bandOff[bandOff.length - 1];
        int bands = bandOff.length - 1;
        int b, i;
        // Access the pixel data array
        final byte pixels[] = db.getBankData()[0];
        for (int y = 0; y < wr.getHeight(); y++) {
            int sp = base + y * scanStride;
            final int end = sp + width * pixStride;
            while (sp < end) {
                a = pixels[sp + aOff] & 0xFF;
                if (a == 0) {
                    for (b = 0; b < bands; b++)
                        pixels[sp + bandOff[b]] = (byte) 0xFF;
                } else if (a < 255) {
                    int aFP = (0x00FF0000 / a);
                    for (b = 0; b < bands; b++) {
                        i = sp + bandOff[b];
                        pixels[i] = (byte) (((pixels[i] & 0xFF) * aFP) >>> 16);
                    }
                }
                sp += pixStride;
            }
        }
    }

    protected static void mult_BYTE_COMP_Data(WritableRaster wr) {
        // System.out.println("Multiply Int: " + wr);

        ComponentSampleModel csm;
        csm = (ComponentSampleModel) wr.getSampleModel();

        final int width = wr.getWidth();

        final int scanStride = csm.getScanlineStride();
        final int pixStride = csm.getPixelStride();
        final int[] bandOff = csm.getBandOffsets();

        DataBufferByte db = (DataBufferByte) wr.getDataBuffer();
        final int base = (db.getOffset() + csm
                                              .getOffset(
                                                         wr.getMinX()
                                                                 - wr
                                                                     .getSampleModelTranslateX(),
                                                         wr.getMinY()
                                                                 - wr
                                                                     .getSampleModelTranslateY()));

        int a = 0;
        int aOff = bandOff[bandOff.length - 1];
        int bands = bandOff.length - 1;
        int b, i;

        // Access the pixel data array
        final byte pixels[] = db.getBankData()[0];
        for (int y = 0; y < wr.getHeight(); y++) {
            int sp = base + y * scanStride;
            final int end = sp + width * pixStride;
            while (sp < end) {
                a = pixels[sp + aOff] & 0xFF;
                if (a != 0xFF)
                    for (b = 0; b < bands; b++) {
                        i = sp + bandOff[b];
                        pixels[i] = (byte) (((pixels[i] & 0xFF) * a) >> 8);
                    }
                sp += pixStride;
            }
        }
    }
    
    public static String getColorHexString(Color c) { 
        String colString = Integer.toHexString(c.getRGB() & 0xffffff); 
        return "#000000".substring(0, 7 - colString.length()).concat(colString); 
    }     

    /**
     * Draws an image on top of a component by doing a 3x3 grid stretch of the image
     * using the specified insets.
     */
    public static void tileStretchPaint(Graphics g, 
                JComponent comp,
                BufferedImage img,
                Insets ins) {
        
        int left = ins.left;
        int right = ins.right;
        int top = ins.top;
        int bottom = ins.bottom;
        
        // top
        g.drawImage(img,
                    0,0,left,top,
                    0,0,left,top,
                    null);
        g.drawImage(img,
                    left,                 0, 
                    comp.getWidth() - right, top, 
                    left,                 0, 
                    img.getWidth()  - right, top, 
                    null);
        g.drawImage(img,
                    comp.getWidth() - right, 0, 
                    comp.getWidth(),         top, 
                    img.getWidth()  - right, 0, 
                    img.getWidth(),          top, 
                    null);

        // middle
        g.drawImage(img,
                    0,    top, 
                    left, comp.getHeight()-bottom,
                    0,    top,   
                    left, img.getHeight()-bottom,
                    null);
        
        g.drawImage(img,
                    left,                  top, 
                    comp.getWidth()-right,      comp.getHeight()-bottom,
                    left,                  top,   
                    img.getWidth()-right,  img.getHeight()-bottom,
                    null);
         
        g.drawImage(img,
                    comp.getWidth()-right,     top, 
                    comp.getWidth(),           comp.getHeight()-bottom,
                    img.getWidth()-right, top,   
                    img.getWidth(),       img.getHeight()-bottom,
                    null);
        
        // bottom
        g.drawImage(img,
                    0,comp.getHeight()-bottom, 
                    left, comp.getHeight(),
                    0,img.getHeight()-bottom,   
                    left,img.getHeight(),
                    null);
        g.drawImage(img,
                    left,                    comp.getHeight()-bottom, 
                    comp.getWidth()-right,        comp.getHeight(),
                    left,                    img.getHeight()-bottom,   
                    img.getWidth()-right,    img.getHeight(),
                    null);
        g.drawImage(img,
                    comp.getWidth()-right,     comp.getHeight()-bottom, 
                    comp.getWidth(),           comp.getHeight(),
                    img.getWidth()-right, img.getHeight()-bottom,   
                    img.getWidth(),       img.getHeight(),
                    null);
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);
//        boolean hasAlpha = true;

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.BITMASK;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    // This method returns true if the specified image has transparent pixels
    public static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
         PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }



    public static BufferedImage convert(JComponent comp) {
        comp.setDoubleBuffered(false);
        Dimension size = comp.getPreferredSize();
        comp.setSize(size);

        BufferedImage image = new BufferedImage(size.width, size.height,
                BufferedImage.TYPE_4BYTE_ABGR);
        final Graphics g = image.getGraphics();
        g.setClip(0, 0, size.width, size.height);
        return image;
    }

    public static BufferedImage getZone(int x,int y,int width,int height) throws AWTException{
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(new Rectangle(x,y,width, height));
        return image;
    }

    public static BufferedImage getScreen() throws AWTException{
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(
                new Rectangle(java.awt.Toolkit.getDefaultToolkit().getScreenSize()) );
        return image;
    }

    public static BufferedImage getImageBlur(BufferedImage bufferedImage){
        float[ ] matrice = {
            0.1f, 0.1f, 0.1f,
            0.1f, 0.2f, 0.1f,
            0.1f, 0.1f, 0.1f
        };
        BufferedImageOp op = new ConvolveOp(new Kernel(3,3,matrice));
        BufferedImage nouvelleImage = op.filter(bufferedImage, null);
        return nouvelleImage;
    }

    public static BufferedImage toBufferedImage2(Image image) {
        if( image instanceof BufferedImage ) {
                return( (BufferedImage)image );
        } else {
                image = new ImageIcon(image).getImage();
                BufferedImage bufferedImage = new BufferedImage(
                                                      image.getWidth(null),
                                                      image.getHeight(null),
                                                      BufferedImage.TYPE_INT_RGB );
                Graphics g = bufferedImage.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();
                return( bufferedImage );
        }
    }

    public static BufferedImage getReflection(Icon icon){
         BufferedImage imagen = toBufferedImage(((ImageIcon) icon).getImage());
         BufferedImage mask = Reflection.createGradientMask(imagen.getWidth(),
                                                               imagen.getHeight());
         imagen = Reflection.createReflectedPicture(imagen, mask);
         return imagen;
    }

    public static BufferedImage getReflection(Image image){
         BufferedImage imagen = toBufferedImage(image);
         BufferedImage mask = Reflection.createGradientMask(imagen.getWidth(),
                                                               imagen.getHeight());
         imagen = Reflection.createReflectedPicture(imagen, mask);
         return imagen;
    }

    public static BufferedImage getImage(Component component){
       if(component==null){return null;}
       int width = component.getWidth();
       int height = component.getHeight();
       BufferedImage image = new BufferedImage(width, height,
                                                 BufferedImage.TYPE_INT_ARGB);
       Graphics2D g = image.createGraphics();
       component.paintAll(g);
       g.dispose();
       return toBufferedImage(image);
    }

    public static BufferedImage scale(BufferedImage bi, double scaleValue) {
        AffineTransform tx = new AffineTransform();
        tx.scale(scaleValue, scaleValue);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BICUBIC);
        BufferedImage biNew = new BufferedImage( (int) (bi.getWidth() * scaleValue),
                (int) (bi.getHeight() * scaleValue),
                bi.getType());
        return op.filter(bi, biNew);
    }

    public static BufferedImage rotate(BufferedImage bi, double rotateValue) {
        AffineTransform tx = new AffineTransform();
        tx.rotate(rotateValue);
        AffineTransformOp op = new AffineTransformOp(tx,
                AffineTransformOp.TYPE_BILINEAR);
        BufferedImage biNew = new BufferedImage( (bi.getWidth()),(bi.getHeight()),
                bi.getType());
        return op.filter(bi, biNew);
    }
}
