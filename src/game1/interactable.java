package game1;

import utilities.Vector2D;

import java.awt.*;

public class interactable extends  GameObject{
    public Color COLOR;
    private long startTime;
    interactable(Vector2D position, Vector2D velocity) {
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
        if (this.getClass() != other.getClass() &&  this.overlap(other) && !collisionsOff && !other.collisionsOff) {
            if (other instanceof playerShip)
                this.hit();

            else if (this.playerFriendly != other.playerFriendly && !(other instanceof Bullet) ) {
                this.hit();
                other.hit();
            }
        }
    }
    @Override
    public void update(){
        super.update();
        if (!((System.currentTimeMillis() - this.startTime)<1000))
            collisionsOff=false;
    }


}
