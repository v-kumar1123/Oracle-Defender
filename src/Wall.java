import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Wall extends GameElement{
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int x=0;
    int y=0;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
            super(convertToImage, imageWidth, imageHeight);
            image=super.getElementImage();
        }*/
    public Wall(int x, int y,File file) {
        super(x,y,file);
        this.x=x;
        this.y=y;
        image=super.tools.scale(convertFileToImage(),5.0,5.0);
        super.setElementImage(image);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();

        setRect(new Rectangle(x,y,imageWidth,imageHeight));
    }

    @Override
    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public int getImageHeight() {
        return imageHeight;
    }

    public BufferedImage getImage() {
        return image;
    }
}
