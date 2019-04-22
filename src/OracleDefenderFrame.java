import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class OracleDefenderFrame extends JFrame {
    ImageTools2 tools=new ImageTools2();
    public OracleDefenderFrame() {
        super();
        setSize(600,600);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);
    }
    public void paint(Graphics g) {
        BufferedImage tile=null;
        try {
            tile=ImageIO.read(new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\src\\WalkableTile.png"));
        }catch(IOException e) {
            e.printStackTrace();
        }
        tile=tools.scale(tile,100,100);

    }
}
