package game1;

import utilities.Vector2D;

import java.awt.*;

import static game1.Constants.*;

public class Bullet extends GameObject{

    public Color COLOR;
    Bullet(Vector2D position, Vector2D velocity)
    {
        super(position,velocity);
        radius = 5;
        if (overlap(BasicGame.ship)) {
            playerFriendly = true;
            COLOR = Color.blue;
        }
        else {
            playerFriendly = false;
            COLOR = Color.white;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(COLOR);
        g.fillOval((int)position.x,(int)position.y,radius,radius);

    }
    @Override
    public void update(){
        position.addScaled(velocity, DT);
        if (position.x > FRAME_WIDTH || position.x < 0)
            hit();
        if (position.y > FRAME_HEIGHT || position.y < 0)
            hit();

    }
}
