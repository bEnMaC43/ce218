package game1;

import utilities.Vector2D;

import java.awt.*;

public class Shield extends GameObject{

    Shield(Vector2D position, Vector2D velocity) {
        super(position, velocity);
        playerFriendly = false;
    }
    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.green);
        g.fillOval((int) position.x - radius/2, (int) position.y - radius/2,  radius,  radius);

    }
}
