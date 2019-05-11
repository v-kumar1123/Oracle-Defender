import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Mine extends Collidable {
    int imageWidth=0;
    int imageHeight=0;
    int shouldI=0;
    boolean canExplode=false;
    BufferedImage image=null;
    int x=0;
    int y=0;
    int speed=100;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
            super(convertToImage, imageWidth, imageHeight);
            image=super.getElementImage();
        }*/
    public Mine(int x, int y, File file) {
        super(x,y,file);
        this.x=x;
        this.y=y;
        image=super.tools.scale(convertFileToImage(),5.0,5.0);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        setRect(new Rectangle(x,y,imageWidth,imageHeight));
        super.setImage(image);
    }

    public void setCanExplode(boolean canExplode) {
        this.canExplode = canExplode;
    }

    public boolean isCanExplode() {
        return canExplode;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
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

    public void update() {

        if(shouldI%speed==0) {
            if (isCanExplode()) {
                setCanExplode(false);
                try {
                    image = ImageIO.read(new File("WalkableTile.png"));
                    super.setImage(tools.scale(image,5.0,5.0));
                    image=super.getElementImage();
                }catch (IOException r) {
                    r.printStackTrace();
                }
            }
            else if(!isCanExplode()) {
                setCanExplode(true);
                try {
                    image = ImageIO.read(new File("Mine.png"));
                    super.setImage(tools.scale(image,5.0,5.0));
                    image=super.getElementImage();
                }catch (IOException r) {
                    r.printStackTrace();
                }
            }
        }
        shouldI++;
    }
}
