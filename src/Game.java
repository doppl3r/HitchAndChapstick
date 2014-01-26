import java.awt.*;

public class Game {
    private boolean gameOver;
    public World world;

    public Game(){
        world = new World();
    }
    public void draw(Graphics2D g){
        world.draw(g);
    }
    public void update(double mod){
        world.update(mod);
    }

    //key pressed
    public void keyUpPressed(){ world.keyUpPressed(); }
    public void keyDownPressed(){ world.keyDownPressed(); }
    public void keyLeftPressed(){ world.keyLeftPressed(); }
    public void keyRightPressed(){ world.keyRightPressed(); }
    public void keySpacePressed(){ world.keySpacePressed(); }

    //key released
    public void keyUpReleased(){ world.keyUpReleased(); }
    public void keyDownReleased(){ world.keyDownReleased(); }
    public void keyLeftReleased(){ world.keyLeftReleased(); }
    public void keyRightReleased(){ world.keyRightReleased(); }
    public void keySpaceReleased(){ world.keySpaceReleased(); }
    public void releaseR(){ world.releaseR(); }

    //setGameOver
    public void setGameOver(boolean gameOver){ this.gameOver=gameOver; }
    public boolean isGameOver(){ return gameOver; }
}
