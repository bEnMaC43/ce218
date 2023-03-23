package game1;

import utilities.Vector2D;

import java.awt.*;
import java.util.Random;

import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

public class Interactable extends  GameObject{
    public Color COLOR;
    private long startTime;
    Interactable(Vector2D position, Vector2D velocity) {
        super(position, velocity);
        playerFriendly = false;
        isInteractable = true;
        this.startTime = System.currentTimeMillis();
        collisionsOff=true;
    }
    @Override
    public void draw(Graphics2D g){
        g.setColor(COLOR);
        g.fillOval((int) position.x - radius/2, (int) position.y - radius/2,  radius,  radius);

    }
    @Override
    public void collisionHandling(GameObject other){
        Random random = new Random();
        if (this.getClass() != other.getClass() &&  this.overlap(other) && !collisionsOff && !other.collisionsOff) {
            if (other instanceof PlayerShip)
                this.hit();

            else if (this.playerFriendly != other.playerFriendly && !(other instanceof Bullet) ) {
                this.hit();
                other.hit();
            }
            else if (other instanceof Moon)
                position=new Vector2D(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT));
        }
    }
    @Override
    public void update(){
        super.update();
        if (!((System.currentTimeMillis() - this.startTime)<1000))
            collisionsOff=false;
    }


}
