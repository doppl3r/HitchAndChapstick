import physics.JumpAccelerator;
import physics.SprintAccelerator;
import textures.SpriteSheet;

import java.awt.*;

public class Player {
    private SpriteSheet sprite;
    private SprintAccelerator aL, aR;
    private JumpAccelerator jump;

    private double x;
    private double y;
    private double spawnX;
    private double spawnY;
    private double xSpeed;
    private double ySpeed;

    private int score;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean jumping;
    private boolean fallJump;

    public Player(){
        sprite = new SpriteSheet(Window.tt.toon,4,4,0.2);
        //sprite.resize(64,64);
        sprite.center();
        sprite.animate(0); //start looking down

        //physics
        aL = new SprintAccelerator(2.0, 0.5, 1.5); //maxX,speed,acceleration
        aR = new SprintAccelerator(2.0, 0.5, 1.5);
        jump = new JumpAccelerator(20.0, 92.0, 0.50); //length,height,speed
        jump.land();

        x = Window.getPanelWidth()/2;
        y = Window.getPanelHeight()/2;
        xSpeed = ySpeed = 1.0; //0.5 works
    }
    public void draw(Graphics2D g){
        sprite.draw(g);
    }
    public void update(double mod){
        //animations
        if (up || jumping) sprite.animate(false,12,16,mod);
        else if (right) sprite.animate(true,4,8,mod);
        else if (down) sprite.animate(true,0,0,mod);
        else if (left) sprite.animate(true,8,12,mod);
        else sprite.animate(0,4);
        checkCollision(); //for jumps
        sprite.update(Window.getPanelWidth()/2,Window.getPanelHeight()/2);
    }
    public void checkCollision(){
        int blockSize=Window.panel.game.world.getBlockSize();
        //update the x value to the sprite
        if (left){ aL.accelerate(); x -= (aL.getY()); }
        if (right){ aR.accelerate(); x += (aR.getY()); }
        //decelerate the player
        if (!left){ aL.decelerate(); x -= (aL.getY()); }
        if (!right){ aR.decelerate(); x += (aR.getY()); }
        if (jumping){
            jump.accelerate();
            y += (-jump.getJumpAmount());
        }
        //check jump collision
        for (int i=0; i < Math.abs(jump.getJumpAmount()); i+=blockSize){ //scan every section
            if (jump.isAssending()){ //if jumping up
                if (!Window.panel.game.world.map.getMap().getTile((int)((y+i-7)/blockSize),(int)((x-3)/blockSize)).isPassable() ||
                    !Window.panel.game.world.map.getMap().getTile((int)((y+i-7)/blockSize),(int)((x+3)/blockSize)).isPassable()){
                    y = (((int)(y+i+jump.getJumpAmount()/2)/blockSize)*blockSize)+blockSize-8;
                    jumping = false;
                    jump.land();
                    break;
                }
                else jumping = true;
            }
            else {
                if (!Window.panel.game.world.map.getMap().getTile((int)((y+i+8)/blockSize),(int)((x-3)/blockSize)).isPassable() ||
                    !Window.panel.game.world.map.getMap().getTile((int)((y+i+8)/blockSize),(int)((x+3)/blockSize)).isPassable()){
                    y = (((int)(y+jump.getJumpAmount()/2)/blockSize)*blockSize)+8;
                    jumping = false;
                    fallJump = true;
                    jump.land();
                    break;
                }
                else jumping = true;
            }
        }
        //check right
        for (int i=0; i < Math.abs(aR.getSprintAmount()); i+=blockSize){ //scan every section
            if (!Window.panel.game.world.map.getMap().getTile((int)((y+7)/blockSize),(int)((x+i+5)/blockSize)).isPassable() ||
                !Window.panel.game.world.map.getMap().getTile((int)((y-6)/blockSize),(int)((x+i+5)/blockSize)).isPassable()){
                x = (((int)(x+aR.getSprintAmount()/2)/blockSize)*blockSize)+blockSize-5;
                aR.stop();
                break;
            }
        }
        //check left
        for (int i=0; i < Math.abs(aL.getSprintAmount()); i+=blockSize){ //scan every section
            if (!Window.panel.game.world.map.getMap().getTile((int)((y+7)/blockSize),(int)((x-i-5)/blockSize)).isPassable() ||
                !Window.panel.game.world.map.getMap().getTile((int)((y-6)/blockSize),(int)((x-i-5)/blockSize)).isPassable()){
                x = (((int)(x-aL.getSprintAmount()/2)/blockSize)*blockSize)+5;
                aL.stop();
                break;
            }
        }
        //check special blocks
        checkSpecialBlocks(blockSize);
    }
    public void moveUp(boolean up){ this.up=up; spaceBar(up); }
    public void moveRight(boolean right){ this.right=right; if (!right) sprite.animate(false,0,0,0); }
    public void moveDown(boolean down){ this.down=down; }
    public void moveLeft(boolean left){ this.left=left; if (!left) sprite.animate(false,0,0,0); }
    public void spaceBar(boolean jumping){
        if (jump.isGrounded() && !this.jumping){ jump.reset(); fallJump=false; }
        else if (fallJump){ jump.reset(); fallJump=false; }
        this.jumping=jumping;
    }
    public void pressR(){ respawn(); }
    public double getX(){ return x; }
    public double getY(){ return y; }

    public void setXY(double x, double y){ this.x=x; this.y=y; }
    public void setSpawn(double x, double y, int blockSize){
        this.x=spawnX=x;
        this.y=spawnY=y;
    }
    public void fixSpawn(int blockSize){
        while (!Window.panel.game.world.map.getMap().getTile((int)((y)/blockSize),
                (int)((x)/blockSize)).isPassable()){
            y-=blockSize;
        }
    }
    public void respawn(){
        x=spawnX;
        y=spawnY;
        jump.land();
        jumping=false;
    }
    public void checkSpecialBlocks(int blockSize){
        int blockID = Window.panel.game.world.map.getMap().getTile((int)((y)/blockSize),(int)((x)/blockSize)).getID();
        if (blockID > 4 && blockID <= 8) { //checkpoint marker
            Window.panel.game.world.map.getMap().setTileID((int)((y)/blockSize),(int)((x)/blockSize),8);
            spawnX = x;
            spawnY = y;
        }
        else if (blockID > 8 && blockID <= 12){
            respawn();
        }
    }
}
