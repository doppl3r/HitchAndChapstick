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
    private double xSpeed;
    private double ySpeed;

    private int score;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean jumping;

    public Player(){
        sprite = new SpriteSheet(Window.tt.toon,4,4,0.2);
        //sprite.resize(64,64);
        sprite.center();
        sprite.animate(0); //start looking down

        //physics
        aL = new SprintAccelerator(2.0, 0.5, 1.5); //maxX,speed,acceleration
        aR = new SprintAccelerator(2.0, 0.5, 1.5);
        jump = new JumpAccelerator(20.0, 92.0, 0.50); //length,height,speed

        x = Window.getPanelWidth()/2;
        y = Window.getPanelHeight()/2;
        xSpeed = ySpeed = 1.0; //0.5 works
    }
    public void draw(Graphics2D g){
        sprite.draw(g);
    }
    public void update(double mod){
        //animations
        /*if (up) sprite.animate(0,8,mod);
        else if (right) sprite.animate(8,16,mod);
        else if (down) sprite.animate(16,24,mod);
        else if (left) sprite.animate(24,32,mod);*/
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
        //check right collision
        for (int i=0; i < Math.abs(aR.getSprintAmount())/blockSize; i+=blockSize){
            if (Window.panel.game.world.map.getMap().getTile((int)((y+i)/blockSize),
                (int)((x+i)/blockSize)).isPassable() == false){
                System.out.println("hey");
            }

        }
    }
    public void moveUp(boolean up){ this.up=up; testRespawn(); }
    public void moveRight(boolean right){ this.right=right; }
    public void moveDown(boolean down){ this.down=down; }
    public void moveLeft(boolean left){ this.left=left; }
    public void spaceBar(boolean jumping){ this.jumping=jumping; }
    public double getX(){ return x; }
    public double getY(){ return y; }
    public void testRespawn(){
        jump.reset();
        jumping=false;
        x=Window.getPanelWidth()/2;
        y=Window.getPanelHeight()/2;
    }
}
