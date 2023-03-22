package game1;

import utilities.Vector2D;
import java.awt.*;
import static game1.Constants.*;

public abstract class GameObject {

    public int radius;
    public Vector2D position;
    public Vector2D velocity;
    public Boolean alive;
    public Boolean playerFriendly;
    public Boolean collisionsOff;
    GameObject(Vector2D position, Vector2D velocity){
        this.position = position;
        this.velocity = velocity;
        alive = true;
        radius = 10;
        playerFriendly = false;
        collisionsOff=false;
    }

    public void update() {
            position.addScaled(velocity, DT);
            position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public void draw(Graphics2D g) {

    }
    public void hit(){
        alive = false;
    }

    public boolean overlap(GameObject other){
        // Calculate the distance between the two object centers
        double distance = this.position.dist(other.position);

        // Calculate the sum of the two radii
        double radiiSum = this.radius + other.radius;

//        if (other instanceof  Bullet)
//            System.out.println(radiiSum + " <-- raqdiisum  distance -->  " + distance);
        // Check if the objects are colliding
        if (distance < radiiSum)
            return true;

        else return false;


    }
    public void collisionHandling(GameObject other) {
        if (this.getClass() != other.getClass() &&  this.overlap(other) && !collisionsOff && !other.collisionsOff) {
            if (this.playerFriendly != other.playerFriendly  ) {
                this.hit();
                other.hit();
            }
        }
    }

}
