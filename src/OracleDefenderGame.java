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
    private Ampelius player = null;
    public OracleDefenderGame() {
        LevelOne=new ArrayList<Collidable>();
        generic=new ArrayList<Collidable>();
        levelOneLoader();
    }

    public ArrayList<Collidable> getGeneric() {
        return generic;
    }

    public void setLevelOne() {
        for(int x=0;x<getLevelOne().size();x++)
            generic.add(getLevelOne().get(x));


    }

    public void levelOneLoader() {
        LevelOne.clear();
        Scanner k=null;
        try {
            k = new Scanner(new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\World1Level1"));
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
                        LevelOne.add(new Wall(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\Wall.png")));
                    }
                    if (line.charAt(c) == 'T') {
                        LevelOne.add(new WalkableTile(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\WalkableTile.png")));;
                    }
                    if (line.charAt(c) == 'P') {
                        player =new Ampelius(25*c, 25*y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\Ampelius.png"));
                        //System.out.println("I gave ampelius  "+LevelOne.get(LevelOne.size()-1));
                        //LevelOne.add(new WalkableTile(25*c, 25*y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\WalkableTile.png")));
                    }
                    if(line.charAt(c)== 'I') {
                        LevelOne.add(new LaserRect(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\LaserRect.png"),false,3));
                    }
                    if(line.charAt(c)== 'O') {
                        LevelOne.add(new LaserRect(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\LaserRect.png"),true,3));
                    }
                    if(line.charAt(c)=='F') {
                        LevelOne.add(new VerticalLaserRect(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\LaserRect.png"),true,3));
                    }

                    if(line.charAt(c)=='V') {
                        LevelOne.add(new VerticalLaserRect(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\LaserRect.png"),false,3));
                    }

                    if(line.charAt(c)=='A') {
                        LevelOne.add(new AlternatingLaser(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\LaserRect.png"),false,3));
                    }


                    if(line.charAt(c)=='H') {
                        LevelOne.add(new VerticalLaserAlternate(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\LaserRect.png"),false,3));
                    }

                    if(line.charAt(c)=='R') {
                        LevelOne.add(new Rotater(25 * c, 25 * y, new File("C:\\Users\\othscs097\\Desktop\\Oracle-Defender\\Laser.png"),false,3));
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

    public void reset() {
        generic.clear();

        levelOneLoader();
        setLevelOne();
    }

    public Ampelius getPlayer() {
        return player;
    }

    public void setPlayer(Ampelius player) {
        this.player = player;
    }
}
