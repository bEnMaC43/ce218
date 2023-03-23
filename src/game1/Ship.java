package game1;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;

public abstract class Ship extends GameObject {
    public Color COLOR;
    public Bullet bullet =null;
    public Vector2D direction;
    public BasicController ctrl;
    private long timeLastShot;
    public long cooldownTime = 500;


    Ship(Vector2D position, Vector2D velocity,BasicController ctrl) {
        super(position, velocity);
        this.ctrl = ctrl;
        direction = new Vector2D(0,-1);
    }
    @Override
    public void update(){
        super.update();
    }
    public void mkBullet() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - timeLastShot < cooldownTime) {
            // bullet still on cooldown
            return;
        }
        bullet = new Bullet(new Vector2D(position), new Vector2D(direction).mult(30));
        SoundManager.fire();
        timeLastShot = currentTime;


    }
    @Override
    public void hit(){
        super.hit();
        SoundManager.play(SoundManager.bangMedium);
    }




}
