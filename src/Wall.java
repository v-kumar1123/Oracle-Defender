import java.awt.image.BufferedImage;
import java.io.File;

public class Wall extends GameElement{
    int imageWidth=0;
    int imageHeight=0;
    BufferedImage image=null;
    /*public WalkableTile(File convertToImage, int imageWidth, int imageHeight) {
        super(convertToImage, imageWidth, imageHeight);
        image=super.getElementImage();
    }*/
    public Wall(File file) {
        super(file);
        image=super.tools.scale(convertFileToImage(),5.0,5.0);
        this.imageHeight=image.getHeight();
        this.imageWidth=image.getWidth();
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
