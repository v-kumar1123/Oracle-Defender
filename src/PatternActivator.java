import java.awt.*;
import java.io.File;

public class PatternActivator extends Collidable{
    public PatternActivator(int x, int y, File file) {
        super(x, y, file);
        image=tools.scale(image,5,5.0);
        image=tools.invertColor(image);
        setImage(image);
        setRect(new Rectangle((int)getRect().getX(),(int)getRect().getY(),image.getWidth(),image.getHeight()));

    }
}
