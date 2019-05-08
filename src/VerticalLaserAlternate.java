import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class VerticalLaserAlternate extends Collidable {
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int x=0;
    int shootDecide=0;
    int y=0;
    int speed=0;
    boolean directionUp=false;
    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
        super(convertToImage, imageWidth, imageHeight);
        image=super.getElementImage();
    }*/
    public VerticalLaserAlternate(int x, int y, File file, boolean directionUp, int speed) {
        super(x,y,file);
        this.x=x;
        this.speed=speed;
        this.directionUp=directionUp;
        this.y=y;
        image=super.tools.scale(convertFileToImage(),10.0,10.0);
        image=super.tools.rotate(image,90);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        setRect(new Rectangle(x,y,imageWidth,imageHeight));
        super.setImage(image);
    }

    public void setDirectionUp(boolean directionUp) {
        this.directionUp = directionUp;
    }

    public boolean isDirectionUp() {
        return directionUp;
    }

    public void update() {
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
