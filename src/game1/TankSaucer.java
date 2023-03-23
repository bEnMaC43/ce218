package game1;

import utilities.Vector2D;

import java.awt.*;

public class TankSaucer extends Saucer{
    int lives;
    long timeSinceLastHit;

    TankSaucer(Vector2D position, Vector2D velocity, BasicController ctrl) {
        super(position, velocity, ctrl);
        COLOR = Color.blue;
        beltColor = Color.yellow;
        MAG_ACC = 50;
        lives = 3;
    }
    @Override
    public void hit(){
        if (lives <0)
            super.hit();
        else {
            lives--;
            collisionsOff=true;
            timeSinceLastHit = System.currentTimeMillis();
        }
    }
    @Override
    public void update(){
        super.update();
        if (timeSinceLastHit+250 < System.currentTimeMillis())
            collisionsOff=false;
    }
}
