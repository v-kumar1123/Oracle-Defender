import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Collidable {
    private Rectangle rect=null;

    ImageTools2 tools=new ImageTools2();
    int width=0;
    int height=0;
    BufferedImage image=null;

    File file=null;

    int x=0;
    int y=0;
    public Collidable(Rectangle rect) {
        this.rect = rect;
    }


    public Collidable(int x, int y, int width, int height) {
        this.rect = new Rectangle(x,y,width,height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Collidable(int x, int y, File file) {
        this.file=file;
        image=convertFileToImage();
        this.x=x;
        this.y=y;
        this.rect = new Rectangle(x,y,image.getWidth(),image.getHeight());
        width=(int)rect.getWidth();
        height=(int)rect.getHeight();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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

    public void setX(int x) {
        this.x = x;
    }

    public BufferedImage getElementImage() {
        return image;
    }
    public void setY(int y) {
        this.y = y;
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

    public void setNewY(int y){}

    public void setNewX(int x){}

    public void setUp(boolean up){}
    public void setDown(boolean up){}
    public void setRight(boolean up){}
    public void setLeft(boolean up){}

    public String toString() {
        return rect.getX()+", "+rect.getY()+", "+rect.getWidth()+", "+rect.getHeight();
    }

}
