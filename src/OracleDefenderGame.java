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
    int levelHeight=0;
    private ArrayList<Collidable> LevelOne;
    private ArrayList<Collidable> generic;
    public OracleDefenderGame() {
        LevelOne=new ArrayList<Collidable>();
        generic=new ArrayList<Collidable>();
        levelOneLoader();
    }

    public ArrayList<Collidable> getGeneric() {
        return generic;
    }

    public void setLevelOne() {
        generic=getLevelOne();
    }

    public void levelOneLoader() {
        Scanner k=null;
        try {
            k = new Scanner(new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\CollisionTest"));
        }catch (FileNotFoundException e ) {
            e.printStackTrace();
        }
        int tempX=0;
        int y=0;
        while(k.hasNextLine()) {
            String line=k.nextLine();
            tempX++;
            levelWidth=line.length()*25;
            try {
                for (int c = 0; c < line.length(); c++) {
                    if (line.charAt(c) == 'W') {
                        LevelOne.add(new Wall(25 * c, 25 * y, new File("C:\\Users\\OTHSCS097\\Desktop\\Oracle-Defender\\Wall.png")));
                    }
                    if (line.charAt(c) == 'T') {
                        LevelOne.add(new WalkableTile(25 * c, 25 * y, new File("C:\\Users\\OTHSCS097\\Desktop\\Oracle-Defender\\WalkableTile.png")));;
                    }
                    if (line.charAt(c) == 'P') {
                        LevelOne.add(new Ampelius(25*c, 25*y, new File("C:\\Users\\OTHSCS097\\Desktop\\Oracle-Defender\\Ampelius.png")));
                        LevelOne.add(new WalkableTile(25*c, 25*y, new File("C:\\Users\\OTHSCS097\\Desktop\\Oracle-Defender\\WalkableTile.png")));
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            y++;

            levelHeight=25*tempX;
        }
    }


    public ArrayList<Collidable> getLevelOne() {
        //System.out.println("size: "+LevelOne.size());
        return LevelOne;
    }
}
