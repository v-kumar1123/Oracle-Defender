import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Scanner;

public class OracleDefenderGame {
    BufferedImage wall=null;
    BufferedImage tile=null;
    int levelWidth=0;
    private ArrayList<GameElement> LevelOne;
    public OracleDefenderGame() {
        LevelOne=new ArrayList<GameElement>();
        levelOneLoader();
    }

    public void levelOneLoader() {
        Scanner k=null;
        try {
            k = new Scanner(new File("C:\\Users\\varun\\Desktop\\Oracle-Defender\\World1Level1"));
        }catch (FileNotFoundException e ) {
            e.printStackTrace();
        }
        int y=0;
        while(k.hasNextLine()) {
            String line=k.nextLine();
            levelWidth=line.length()*25;
            try {
                System.out.println("Length" + line.length());
                for (int c = 0; c < line.length(); c++) {
                    if (line.charAt(c) == 'W') {
                        LevelOne.add(new Wall(25 * c, 25 * y, new File("C:\\Users\\varun\\Desktop\\Oracle-Defender\\Wall.png")));
                        System.out.println("WALL ADDED"+line.length());
                    }
                    if (line.charAt(c) == 'T') {
                        LevelOne.add(new Wall(25 * c, 25 * y, new File("C:\\Users\\varun\\Desktop\\Oracle-Defender\\WalkableTile.png")));
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
        //System.out.println("size: "+LevelOne.size());
        return LevelOne;
    }
}
