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
        image=super.tools.scale(convertFileToImage(),10.0,10.0);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        setRect(new Rectangle(x,y,imageWidth,imageHeight));
        super.setImage(image);
    }


    public void update() {
        if(shootDecide%120==0) {

            AffineTransform oldtrans = new AffineTransform();
            System.out.println("I AM ROTATING PLEASE LOVE ME");
            trans.setToIdentity();
            /*trans.rotate(Math.toRadians(1), getRect().getWidth()/2, getRect().getHeight()/2);
            trans.translate(getRect().getX()-(getRect().getWidth()/2), getRect().getY()-(getRect().getHeight()/2));*/
            trans.rotate(1);
            //setTransform(trans);
            //g.drawImage(this.getImage(), (int)_x, (int)_y, (int)width, (int)height, null);
            setImage(image);
            //g.setTransform(oldtrans);
            //super.setImage(super.tools.rotate(image, 1));


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
