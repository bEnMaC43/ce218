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
    public static final int XP[] = {-6, 0, 6, 0}, YP[] = {8, 4, 8, -8};
    public  static final int XPTHRUST[] = {-5, 0, 5, 0}, YPTHRUST[] = {7, 3, 7, -7};


    public playerShip(BasicController ctrl) {
        super(new Vector2D(FRAME_WIDTH / 2, FRAME_HEIGHT / 2),new Vector2D(),ctrl);
        playerFriendly = true;
        COLOR = Color.CYAN;
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



}
