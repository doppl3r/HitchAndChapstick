import mapping.TileBuffer;
import textures.SpriteSheet;

import java.awt.*;

public class World {
    private int mainX;
    private int mainY;
    private int blockSize;
    private Player player;
    private MapFileReader mapFile;
    private SpriteSheet terrain;
    public TileBuffer map;

    public World(){
        blockSize = 16;
        player = new Player();
        terrain = new SpriteSheet(Window.tt.terrain,8,8,0.0);
        mapFile = new MapFileReader();
        map = new TileBuffer(blockSize); //16 pixel block size
        map.setMapList(mapFile.convertToTileMap("raw/map1.txt"));
        player.setSpawn((map.getMap().getCols()*blockSize)/2,
            (map.getMap().getRows()*blockSize)-(blockSize*2));
    }
    public void draw(Graphics2D g){
        map.draw(g, Window.getPanelWidth(), Window.getPanelHeight(), terrain);
        player.draw(g);
    }
    public void update(double mod){
        player.update(mod);
        map.update(-player.getX()+(Window.getPanelWidth()/2),
            -player.getY()+(Window.getPanelHeight()/2), blockSize, 0, mod);

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
    public void keySpacePressed(){
        player.spaceBar(true);
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
    public void keySpaceReleased(){
        //player.spaceBar(false);
    }
    //key released
    public void releaseR(){ player.pressR(); }
    public int getBlockSize(){ return blockSize; }
}
