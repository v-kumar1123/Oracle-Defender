import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

public class GameElement {
    private int x=0;
    private int y=0;
    private BufferedImage elementImage=null;
    private int width=0;
    private int height=0;
    private File convertToImage=null;
    private int imageWidth=0;
    private boolean isImage=false;
    private int imageHeight=0;
    ImageTools2 tools=new ImageTools2();

    public ImageTools2 getTools() {
        return tools;
    }

    public GameElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public GameElement(int x, int y, File convertToImage) {
        this.x = x;
        this.y = y;
        this.convertToImage=convertToImage;
        elementImage=convertFileToImage();
    }

    public GameElement(File convertToImage, int imageWidth, int imageHeight) {
        isImage = true;
        this.convertToImage = convertToImage;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;

        elementImage = convertFileToImage();
    }

    public GameElement(File convertToImage) {
        this.convertToImage=convertToImage;
        elementImage=convertFileToImage();
    }
    public GameElement(BufferedImage image) {
        elementImage=image;
        this.imageWidth = elementImage.getWidth();
        this.imageHeight = elementImage.getHeight();
    }

    public BufferedImage convertFileToImage() {
        try {
            return ImageIO.read(convertToImage);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public BufferedImage getElementImage() {
        return elementImage;
    }

    public boolean isImage() {
        return isImage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
}
