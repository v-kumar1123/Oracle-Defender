import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rotater extends Collidable {
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int x=0;
    int shootDecide=0;
    int y=0;
    int speed=0;

    AffineTransform trans = new AffineTransform();
    boolean directionRight=false;
    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
        super(convertToImage, imageWidth, imageHeight);
        image=super.getElementImage();
    }*/
    public Rotater(int x, int y, File file, boolean directionRight, int speed) {
        super(x,y,file);
        this.x=x;
        this.speed=speed;
        this.directionRight=directionRight;
        this.y=y;
        image=super.tools.scale(convertFileToImage(),20.0,5);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        super.setImage(image);
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update() {
        if(shootDecide%30==0) {
            int angle=2;
            BufferedImage img=image;
            angle = angle%360;
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.setToTranslation(0,0);
            affineTransform.rotate(Math.toRadians(angle), 0, 0);
            BufferedImage rotated =
                    new BufferedImage( img.getWidth(), img.getHeight(), img.getTransparency());
            Graphics2D g = (Graphics2D) (rotated.getGraphics());
            g.drawImage(img, affineTransform, null);
            image=rotated;
            imageHeight = image.getHeight();
            imageWidth = image.getWidth();
            super.setImage(image);
            image = super.getElementImage();
            System.out.println("I HAVE ROTATED");
        }
        shootDecide++;
    }

    public int getShootDecide() {
        return shootDecide;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public BufferedImage getImage() {
        return image;
    }
}
