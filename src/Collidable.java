import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Collidable {
    private Rectangle rect=null;

    ImageTools2 tools=new ImageTools2();

    BufferedImage image=null;

    File file=null;


    public Collidable(Rectangle rect) {
        this.rect = rect;
    }


    public Collidable(int x, int y, int width, int height) {
        this.rect = new Rectangle(x,y,width,height);
    }



    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Collidable(int x, int y, File file) {
        this.file=file;
        image=convertFileToImage();

        this.rect = new Rectangle(x,y,image.getWidth(),image.getHeight());

    }

    public void setFile(File file) {
        this.file = file;
        image=convertFileToImage();
    }

    public BufferedImage convertFileToImage() {
        BufferedImage b=null;
        try {
            b= ImageIO.read(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    public BufferedImage getElementImage() {
        return image;
    }


    public Rectangle getRect() {
        return rect;
    }

    public int getImageWidth() {
        return image.getWidth();
    }
    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public boolean collidesWith(Collidable c){
        return rect.intersects(c.getRect());
    }

    public void update(){
        return;
    }

    public int getDirection(){return -1;}



    public void setUp(boolean up){}
    public void setDown(boolean up){}
    public void setRight(boolean up){}
    public void setLeft(boolean up){}

    public String toString() {
        return rect.getX()+", "+rect.getY()+", "+rect.getWidth()+", "+rect.getHeight();
    }

}
