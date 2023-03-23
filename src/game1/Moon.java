package game1;

import utilities.ImageManager;
import utilities.Vector2D;

import java.awt.*;
import java.io.IOException;

public class Moon extends GameObject{

    Moon(Vector2D position) {
        super(position, new Vector2D(0,0));
        radius = 15;
        try {
            im = ImageManager.loadImage("asteroid1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        im =  im.getScaledInstance(radius*4,radius*4,Image.SCALE_SMOOTH);
    }

    Image im;
    @Override
    public void hit(){

    }
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(im, (int) position.x-radius, (int) position.y-radius,null);
    }



}
