import audio.AudioHandler;
import textures.SpriteSheet;

import java.awt.*;

public class Enemy {
    private SpriteSheet sprite;
    private double x;
    private double y;
    private double xDirection;
    private double yDirection;
    private boolean dead;

    public Enemy(){

    }
    public void draw(Graphics2D g){
        sprite.draw(g);
    }
    public void update(double mod){
        //animations
        if (yDirection < 0) sprite.animate(12,16);
        else if (yDirection >= 0) sprite.animate(8,12);
        else if (xDirection <  0) sprite.animate(4,8);
        else if (xDirection >= 0) sprite.animate(0,4);
        //direction
        x += xDirection*mod;
        y += yDirection*mod;

        sprite.update(x,y);
    }
    public boolean isDead(){ return dead; }
    public void setX(double x){ this.x=x; }
    public void setY(double y){ this.y=y; }
    public void setXY(double x, double y){ this.x=x; this.y=y; }
    public void setXDirection(double xDirection){ this.xDirection=xDirection; }
    public void setYDirection(double yDirection){ this.yDirection=yDirection; }
    public void setSprite(SpriteSheet sprite){ this.sprite=sprite; }
    public void resizeSprite(int x, int y){ sprite.resize(x,y); }
    public void centerSprite(){ sprite.center(); }
}
