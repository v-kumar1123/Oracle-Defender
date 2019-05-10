import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Exploder extends Collidable {
    BufferedImage[][] array=new BufferedImage[4][4];
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    int x=0;
    int row=0;
    int col=0;
    int shootDecide=0;
    int y=0;
    int speed=0;
    boolean directionRight=false;
    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
        super(convertToImage, imageWidth, imageHeight);
        image=super.getElementImage();
    }*/
    public Exploder(int x, int y, File file, boolean directionRight, int speed) {
        super(x,y,file);
        this.x=x;
        this.speed=speed;
        this.directionRight=directionRight;
        this.y=y;

        image=convertFileToImage();
        initArray();
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
        setRect(new Rectangle(x,y,imageWidth,imageHeight));
        super.setImage(image);
    }


    public void update() {
        if(shootDecide%20==0) {
            if (col >= array[0].length) {
                col = 0;
            }
            if (row >= array[0].length) {
                row = 0;
                col++;
            }
            image = array[row][col];

            row++;
            col++;
            super.setImage(image);
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

    public void initArray() {
        int divideIntoY=4;
        int divideIntoX=4;
        int subHeight=image.getHeight()/divideIntoY;
        int subWidth=image.getWidth()/divideIntoX;
        int tx=0;

        int y=0;
        ArrayList<BufferedImage> imgs2=new ArrayList<BufferedImage>();
        BufferedImage[][] array=new BufferedImage[divideIntoY][divideIntoX];
        //Splits via height
        for(int r=0;r<divideIntoY;r++) {
            tx=0;
            for (int c = 0; c < divideIntoX; c++) {
                array[r][c]=(image.getSubimage(tx, y, subWidth, subHeight));
                //Goes Right
                tx+=subWidth;
            }
            //Goes Down
            y+=subHeight;
        }
    }


}
