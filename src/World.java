import mapping.TileBuffer;
import textures.SpriteSheet;

import java.awt.*;

public class World {
    private int mainX;
    private int mainY;
    private Player player;
    private TileBuffer map;
    private MapFileReader mapFile;
    private SpriteSheet terrain;

    public World(){
        player = new Player();
        terrain = new SpriteSheet(Window.tt.terrain,8,8,0.0);
        mapFile = new MapFileReader();
        map = new TileBuffer(16); //16 pixel block size
        map.setMapList(mapFile.convertToArray("raw/map1.txt"));
    }
    public void draw(Graphics2D g){
        map.draw(g, Window.getPanelWidth(), Window.getPanelHeight(), terrain);
        player.draw(g);
    }
    public void update(double mod){
        map.update(mainX, mainY, 16, 0, mod);
        player.update(mod);
    }
    //key pressed
    public void keyUpPressed(){
        player.moveUp(true);
    }
    public void keyDownPressed(){
        player.moveDown(true);
    }
    public void keyLeftPressed(){
        player.moveLeft(true);
    }
    public void keyRightPressed(){
        player.moveRight(true);
    }
    //key released
    public void keyUpReleased(){
        player.moveUp(false);
    }
    public void keyDownReleased(){
        player.moveDown(false);
    }
    public void keyLeftReleased(){
        player.moveLeft(false);
    }
    public void keyRightReleased(){
        player.moveRight(false);
    }
}
