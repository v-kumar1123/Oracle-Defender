import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class OracleDefenderGame {
    BufferedImage wall=null;
    BufferedImage tile=null;
    private ArrayList<GameElement> LevelOne;
    public OracleDefenderGame() {
        LevelOne=new ArrayList<GameElement>();
        levelOneLoader();
    }

    public void levelOneLoader() {
        Scanner k=new Scanner("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\World1Level1.txt");

        int y=0;
        while(k.hasNextLine()) {
            String line=k.nextLine();

            try {
                for (int c = 0; c < line.length(); c++) {
                    if (line.charAt(c) == 'W') {
                        LevelOne.add(new Wall(25 * c, 25 * y, new File("Wall.png")));
                        System.out.println("WALL ADDED"+line.length());
                    } if (line.charAt(c) == 'T') {
                        LevelOne.add(new Wall(25 * c, 25 * y, new File("WalkableTile.png")));
                        System.out.println("TILE ADDED");
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            y++;
        }
    }

    public ArrayList<GameElement> getLevelOne() {
        System.out.println("size: "+LevelOne.size());
        return LevelOne;
    }
}
