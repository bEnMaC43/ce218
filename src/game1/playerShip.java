package game1;

import utilities.SoundManager;
import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Constants.*;

public class playerShip extends Ship {
    //constants
    // rotation velocity in radians per second
    public static final double STEER_RATE = 2 * Math.PI;
    // acceleration when thrust is applied
    public static final double MAG_ACC = 200;
    // constant speed loss factor
    public static final double DRAG = 0.01;
    public boolean shieleded;
    public static final int XP[] = {-6, 0, 6, 0}, YP[] = {8, 4, 8, -8};
    public  static final int XPTHRUST[] = {-5, 0, 5, 0}, YPTHRUST[] = {7, 3, 7, -7};
    private long machineGunExpTime;

    public playerShip(BasicController ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2),new Vector2D(),ctrl);
        playerFriendly = true;
        COLOR = Color.CYAN;
        shieleded=false;
    }

    @Override
    public void update() {
        direction.rotate(ctrl.action().turn * STEER_RATE*DT);
        velocity = new Vector2D(direction).mult(velocity.mag());
        velocity.addScaled(direction, MAG_ACC*DT*ctrl.action().thrust);
        velocity.addScaled(velocity, -DRAG);
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);

        if (ctrl.action().shoot) {
            mkBullet();
            ctrl.action().shoot = false;
        }
        if (bullet !=  null && !bullet.alive)
            bullet = null;
        if (ctrl.action().thrust == 1 && !SoundManager.thrusting )
            SoundManager.startThrust();
        else if (ctrl.action().thrust==0)
            SoundManager.stopThrust();
        if (machineGunExpTime < System.currentTimeMillis()) {
            machineGunExpTime = System.currentTimeMillis();
            cooldownTime=500;
        }
    }
    @Override
    public void draw(Graphics2D g) {
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(1,1);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (ctrl.action().thrust == 1) {
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);    }
    @Override
    public void collisionHandling(GameObject other){
        if (this.getClass() != other.getClass() &&  this.overlap(other) && !collisionsOff && !other.collisionsOff) {
            if (this.playerFriendly != other.playerFriendly && !(other.isInteractable)) {
                this.hit();
                other.hit();
            } else if (other instanceof Shield) {
                other.hit();
                shieleded = true;
                COLOR = Color.green;
            }
            else if (other instanceof machineGun) {
                cooldownTime/=2;
                machineGunExpTime+= 10000;
            }
        }
    }
    int count=0;
    @Override
    public void hit(){
        super.hit();
        if (shieleded && count == 1) {
            SoundManager.play(SoundManager.shieldDrop);
            shieleded = false;
            COLOR=Color.cyan;
            count = 0;
        }
        else if (shieleded && count < 1)
            count++;
    }

}
