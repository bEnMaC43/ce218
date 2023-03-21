package game1;

import utilities.Vector2D;

import java.awt.*;
import java.util.Random;

import static game1.Constants.*;


public class Saucer extends Ship{

    public static final double STEER_RATE = 2 * Math.PI;
    // acceleration when thrust is applied
    public static final double MAG_ACC = 200;
    // constant speed loss factor
    public static final double DRAG = 0.01;


    Saucer(Vector2D position, Vector2D velocity, BasicController ctrl) {
        super(position, velocity, ctrl);
        playerFriendly=false;
        COLOR = Color.gray;
//        Random random = new Random();
//        direction = new Vector2D(random.nextInt(FRAME_WIDTH)+1,random.nextInt(FRAME_HEIGHT)+1);
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
    public void draw(Graphics2D g){
        g.setColor(COLOR);
        g.fillOval((int) position.x - radius, (int) position.y - radius, 2 * radius, 2 * radius);

    }
    @Override
    public void mkBullet(){
        super.mkBullet();
        Random random = new Random();
        bullet.playerFriendly=false;
        bullet.COLOR = Color.red;
//        bullet.velocity = new Vector2D(random.nextInt(FRAME_WIDTH)+1,random.nextInt(FRAME_HEIGHT)+1).mult(30);


    }



}
