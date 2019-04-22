import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.BufferedWriter;
import java.io.File;
import java.nio.Buffer;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class ImageTools2 {

    public static int HORIZONTAL_FLIP = 1, VERTICAL_FLIP = 2, DOUBLE_FLIP = 3;

    /**
     * Loads an image.
     *
     * @param fileName The name of a file with an image
     * @return Returns the loaded image. null is returned if the image cannot be loaded.
     */
    public static BufferedImage load(String fileName) {
        try {
            return ImageIO.read(new File(fileName));
        }catch (Exception e){e.printStackTrace();}
        return null;
    }

    /**
     * Copies and returns an image.
     *
     * @param img Receives a buffered image
     * @return Returns a copy of the received image. null is returned if the received image is null.
     */
    public static BufferedImage copy(BufferedImage img) {
        BufferedImage tempImage=new BufferedImage(img.getWidth(),img.getHeight(),TYPE_4BYTE_ABGR);
        Graphics g=tempImage.getGraphics();
        g.drawImage(img,0,0,null);
        return tempImage;
    }

    /**
     * Returns a new image with transparency enabled.
     *
     * @param img Receives a buffered image
     * @return returns a copy of the received image with a color mode that allows transparency.
     * null is returned if the received image is null.
     */
    public static BufferedImage copyWithTransparency(BufferedImage img) {
        if(img==null) {
            return  null;
        }
        BufferedImage transparent=new BufferedImage(img.getWidth(),img.getHeight(),TYPE_INT_ARGB);
        for(int x=0;x<img.getWidth();x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                transparent.setRGB(x,y,img.getRGB(x,y));
            }
        }
        return transparent;
        //TYPE_INT_ARGB – RGB color with transparency
    }

    /**
     * Checks if the provided image has transparency.
     *
     * @param img Receives a buffered image
     * @return returns true if the image has a transparent mode and false otherwise.
     */
    public static boolean hasTransparency(BufferedImage img) {
        if(img.getType()==TYPE_INT_ARGB) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Scales an image.
     *
     * @param img Receives a buffered image and two positive double scale values (percentages)
     * @param horizontalScale Value to scale horizontal by.
     * @param verticalScale Value to scale vertical by.
     * @return creates and returns a scaled copy of the received image,
     * null is returned if the received image is null or if non-positive scales are provided
     */
    public static BufferedImage scale(BufferedImage img, double horizontalScale, double verticalScale) {
        BufferedImage tempImage=new BufferedImage((int)(img.getWidth()*horizontalScale),(int)(img.getHeight()*verticalScale),TYPE_4BYTE_ABGR);
        Graphics g=tempImage.getGraphics();
        g.drawImage(img,0,0,tempImage.getWidth(),tempImage.getHeight(),0,0,img.getWidth(),img.getHeight(),null);
        return tempImage;
    }

    /**
     * Scales an image.
     *
     * @param img Receives a buffered image
     * @param newWidth New width to scale to.
     * @param newHeight New height to scale to.
     * @return creates and returns a scaled copy of the received image,
     * null is returned if the received image is null or if non-positive dimensions are provided
     */
    public static BufferedImage scale(BufferedImage img, int newWidth, int newHeight) {
        BufferedImage tempImage=new BufferedImage(newWidth,newHeight,TYPE_4BYTE_ABGR);
        Graphics g=tempImage.getGraphics();
        g.drawImage(img,0,0,tempImage.getWidth(),tempImage.getHeight(),0,0,img.getWidth(),img.getHeight(),null);
        return tempImage;
    }

    /**
     * Rotates an image.
     *
     * @param img Receives a buffered image
     * @param angle The angle to rotate to.
     * @return The rotated image. null is returned if the received image is null.
     */
    public static BufferedImage rotate(BufferedImage img, double angle) {
        // img will be the image being rotated
// angle will be the angle for the rotation
// corrects any angle bigger than 360
        angle = angle%360;
// Creates the transform for the rotation
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.setToTranslation(0,0);
        affineTransform.rotate(Math.toRadians(angle), img.getWidth()/2, img.getHeight()/2);
// Stores the transparency of the original image
        int transparency = img.getColorModel().getTransparency();
// Creates an image to store the rotated version of the original image
        BufferedImage rotated =
                new BufferedImage( img.getWidth(), img.getHeight(), transparency);
// gets the graphics of the destination image.
        Graphics2D g = (Graphics2D) (rotated.getGraphics());
// draws the original image onto the destination image, with the correct rotation
        g.drawImage(img, affineTransform, null);
        return rotated;
    }

    /**
     * Flips an image.
     *
     * @param img Receives a buffered image
     * @param type Type of flip (int)
     * @return Creates and returns a flipped copy of the received image.
     * null is returned if the received image is null or if an invalid flipping value is provided
     */
    public static BufferedImage flip(BufferedImage img, int type) {
        BufferedImage flipped=copyWithTransparency(img);

        if(type==VERTICAL_FLIP) {
            AffineTransform affineTransform = AffineTransform.getScaleInstance(1, -1);
            affineTransform.translate(0, -img.getHeight(null));
            AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            flipped = op.filter(img, null);

            return flipped;
        }


        else if(type==HORIZONTAL_FLIP) {
            AffineTransform affineTransform = AffineTransform.getScaleInstance(-1, 1);
            affineTransform.translate(-img.getWidth(null),0);
            AffineTransformOp op = new AffineTransformOp(affineTransform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            flipped = op.filter(img, null);

            return flipped;
        }

        else if(type==DOUBLE_FLIP) {
            double angle = 180;
// Creates the transform for the rotation
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToTranslation(0,0);
            affineTransform.rotate(Math.toRadians(angle), img.getWidth()/2, img.getHeight()/2);
// Stores the transparency of the original image
            int transparency = img.getColorModel().getTransparency();
// Creates an image to store the rotated version of the original image
            BufferedImage rotated =
                    new BufferedImage( img.getWidth(), img.getHeight(), transparency);
// gets the graphics of the destination image.
            Graphics2D g = (Graphics2D) (rotated.getGraphics());
// draws the original image onto the destination image, with the correct rotation
            g.drawImage(img, affineTransform, null);
            return rotated;
        }


        return null;

    }

    /**
     * Blurs an image.
     *
     * @param img Receives a buffered image
     * @return creates and returns a blurred copy of the received image,
     * the blurring is created by blending each cell with its 8 neighbors.
     * Null is returned if the received image is null.
     */
    public static BufferedImage blur(BufferedImage img) {
        //SOURCED FROM STACK OVERFLOW
        BufferedImage blurred=copyWithTransparency(img);

        Kernel kernel = new Kernel(3, 3,

        new float[] {1f/9f, 1f/9f, 1f/9f, 1f/9f, 1f/9f, 1f/9f, 1f/9f, 1f/9f, 1f/9f});

        BufferedImageOp tempImage = new ConvolveOp(kernel);
        blurred = tempImage.filter(blurred, null);

        return blurred;
    }

    /**
     * Inverts an image's colors.
     *
     * @param img Receives a buffered image
     * @return Image with inverted colors. null is returned if the received image is null.
     */
    public static BufferedImage invertColor(BufferedImage img) {
        BufferedImage invert=copyWithTransparency(img);
        for(int r=0;r<img.getHeight();r++) {
            for(int c=0;c<img.getWidth();c++) {
                int colorNum=img.getRGB(c,r);

                Color color=new Color(colorNum,true);
                int red=255-color.getRed();
                int blue=255-color.getBlue();
                int green=255-color.getGreen();
                int alpha=color.getAlpha();

                Color newColor=new Color(red,green,blue,alpha);
                invert.setRGB(c,r,newColor.getRGB());
            }
        }
        return invert;
    }

    /**
     * Removes a certain percentage of an image's pixels.
     *
     * @param img Receives a buffered image
     * @param percentToRemove Percent to remove of the image.
     * @return creates and returns a copy of the received image with the given
     * percentage in decimal form of the images remaining non-fully transparent
     * pixels changed to be completely transparent. null is returned if the
     * received image is null or if non-positive percentage is provided.
     */
    public static BufferedImage removePixels(BufferedImage img, double percentToRemove) {
        BufferedImage pixelsRemoved= copyWithTransparency(img);
        /*
        for(int x=0;x<pixelsRemoved.getWidth();x++) {
            for(int y=0;y<pixelsRemoved.getHeight();y++) {
                if()
            }
        }*/
        int visiblePixels=0;
        for(int x=0;x<pixelsRemoved.getWidth();x++) {
            for(int y=0;y<pixelsRemoved.getHeight();y++) {
                if((pixelsRemoved.getRGB(x,y)>>24)!=0x00) {
                    visiblePixels++;
                }
            }
        }

        int f=(int)(percentToRemove*visiblePixels);


        System.out.println(f+" f PIXELS");
        int x=(int)(Math.random()*pixelsRemoved.getWidth()),y=(int)(Math.random()*pixelsRemoved.getHeight());
        do {
            x=(int)(Math.random()*pixelsRemoved.getWidth());
            y=(int)(Math.random()*pixelsRemoved.getHeight());
            if((pixelsRemoved.getRGB(x,y)>>24)!=0x00) {
                Color color=new Color(pixelsRemoved.getRGB(x,y));
                color=new Color(color.getRed(),color.getGreen(),color.getBlue(),0);
                pixelsRemoved.setRGB(x,y,color.getRGB());
                f--;
            }
        }while (f>0);
        return pixelsRemoved;


    }

    /**
     * Removes a certain amount of pixels from an image.
     *
     * @param img Receives a buffered image
     * @param numToRemove number of pixels to remove
     * @return creates and returns a copy of the received image with the given
     * number of the images remaining pixels removed.
     * Non-fully transparent pixels are changed to be completely transparent.
     * null is returned if the received image is null or if non-positive number
     * is provided. Note: is there are not enough pixels in the image it will
     * remove as many as it can.
     */
    public static BufferedImage removePixels(BufferedImage img, int numToRemove) {
        BufferedImage pixelsRemoved= copy(img);
        /*
        for(int x=0;x<pixelsRemoved.getWidth();x++) {
            for(int y=0;y<pixelsRemoved.getHeight();y++) {
                if()
            }
        }*/
        int f=numToRemove;
        int x=(int)(Math.random()*pixelsRemoved.getWidth()),y=(int)(Math.random()*pixelsRemoved.getHeight());
        do {
            if(new Color(pixelsRemoved.getRGB(x,y)).getAlpha()!=0) {
                Color color=new Color(pixelsRemoved.getRGB(x,y));
                color=new Color(color.getRed(),color.getGreen(),color.getBlue(),0);

                pixelsRemoved.setRGB(x,y,color.getRGB());
                f--;
            }

            x=(int)(Math.random()*pixelsRemoved.getWidth());
            y=(int)(Math.random()*pixelsRemoved.getHeight());
        }while (f>0);
        return pixelsRemoved;
    }

    /**
     * Fades an image.
     *
     * @param img Receives a buffered image
     * @param fade Double percentage to fade
     * @return Creates and returns a copy of the received image that has been
     * faded the given percentage. Fading is done by multiply the alpha value by (1-fade)
     * Null is returned if the received image is null or if non-positive fade value is provided
     * Note: any fade greater than 1 will be reduced to 1
     */
    public static BufferedImage fade(BufferedImage img, double fade) {
        if(img==null||fade<=0) {
            return null;
        }
        BufferedImage faded=copyWithTransparency(img);
        Color color;
        for(int x=0;x<img.getWidth();x++) {
            for(int y=0;y<img.getHeight();y++) {
                color=new Color(faded.getRGB(x,y),true);
                color=new Color(color.getRed(),color.getGreen(),color.getBlue(),(int)(color.getAlpha()*(1-fade)));

                faded.setRGB(x,y,color.getRGB());
            }
        }

        return faded;
    }

    /**
     * Lightens an image.
     *
     * @param img Receives a buffered image
     * @param lightenFactor double percentage to lighten
     * @return creates and returns a copy of the received image that has been
     * lightened by the given percentage + 1.
     * Null is returned if the received image is null or if non-positive lighten.
     * Factor value is provided.
     * Note: any colors that end up being larger than 255 will be changed to 255.
     */
    
    public static BufferedImage lighten(BufferedImage img, double lightenFactor) {
        BufferedImage lightened=new BufferedImage(img.getWidth(),img.getHeight(),TYPE_INT_ARGB);
        for(int x=0;x<img.getWidth();x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                lightened.setRGB(x,y,img.getRGB(x,y));
            }
        }
        for(int x=0;x<img.getWidth();x++) {
            //TODO: 4/4/19 HEY GUYS DONT FORGET TO DO THIS!!!
            for(int y=0;y<img.getHeight();y++) {
                Color color=new Color(lightened.getRGB(x,y),true);
                if((int)(color.getRed()+lightenFactor*color.getRed())>255||(int)(color.getBlue()+lightenFactor*color.getBlue())>255||(int)(color.getGreen()+lightenFactor*color.getGreen())>255) {
                    if((int)(color.getRed()+lightenFactor*color.getRed())>255) {
                        color=new Color(255,color.getGreen(),color.getBlue(),color.getAlpha());
                    }
                    if((int)(color.getBlue()+lightenFactor*color.getBlue())>255) {
                        color=new Color(color.getRed(),color.getGreen(),255,color.getAlpha());
                    }
                    if((int)(color.getGreen()+lightenFactor*color.getGreen())>255) {
                        color=new Color(color.getRed(),255,color.getBlue(),color.getAlpha());
                    }
                    lightened.setRGB(x,y,color.getRGB());
                }
                else {
                    color=new Color((int)(color.getRed()+(color.getRed()*lightenFactor)),(int)(color.getGreen()+(lightenFactor*color.getGreen())),(int)(color.getBlue()+(color.getBlue()*lightenFactor)),color.getAlpha());
                    lightened.setRGB(x,y,color.getRGB());
                }
            }
        }
        return lightened;
    }

    /**
     * Darkens an image.
     *
     * @param img Receives a buffered image
     * @param darkenFactor double percentage to darken
     * @return creates and returns a copy of the received image that has been
     * darkened by 1 minus the given percentage.
     * null is returned if the received image is null or if non-positive.
     * Note: any colors that end up being larger than 255 will be
     * changed to 255.
     */
    public static BufferedImage darken(BufferedImage img, double darkenFactor) {
        BufferedImage lightened=new BufferedImage(img.getWidth(),img.getHeight(),TYPE_INT_ARGB);
        for(int x=0;x<img.getWidth();x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                lightened.setRGB(x,y,img.getRGB(x,y));
            }
        }
        for(int x=0;x<img.getWidth();x++) {
            //TODO: 4/4/19 HEY GUYS DONT FORGET TO DO THIS!!!
            for(int y=0;y<img.getHeight();y++) {
                Color color=new Color(lightened.getRGB(x,y),true);
                if((int)(color.getRed()-darkenFactor*color.getRed())<0||(int)(color.getBlue()+darkenFactor*color.getBlue())<0||(int)(color.getGreen()+darkenFactor*color.getGreen())<0) {
                    if((int)(color.getRed()-darkenFactor*color.getRed())<0) {
                        color=new Color(0,color.getGreen(),color.getBlue(),color.getAlpha());
                    }
                    if((int)(color.getBlue()-darkenFactor*color.getBlue())<0) {
                        color=new Color(color.getRed(),color.getGreen(),0,color.getAlpha());
                    }
                    if((int)(color.getGreen()-darkenFactor*color.getGreen())<0) {
                        color=new Color(color.getRed(),0,color.getBlue(),color.getAlpha());
                    }
                    lightened.setRGB(x,y,color.getRGB());
                }
                else {
                    color=new Color((int)(color.getRed()-(color.getRed()*darkenFactor)),(int)(color.getGreen()-(darkenFactor*color.getGreen())),(int)(color.getBlue()-(color.getBlue()*darkenFactor)),color.getAlpha());
                    lightened.setRGB(x,y,color.getRGB());
                }
            }
        }
        return lightened;
    }
}