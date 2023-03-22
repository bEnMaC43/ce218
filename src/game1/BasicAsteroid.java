package game1;

import utilities.ImageManager;
import utilities.SoundManager;
import utilities.Vector2D;

import static game1.Constants.DT;
import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class BasicAsteroid extends GameObject {

    public static final double MAX_SPEED = 100;
    public ArrayList<BasicAsteroid> childAsteroids = new ArrayList<>();
    private long startTime;
    Image im;


    public BasicAsteroid(double x, double y, Vector2D velocity) {
        super(new Vector2D(x,y),velocity);
        this.startTime = System.currentTimeMillis();
        try {
            im = ImageManager.loadImage("asteroid1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        im =  im.getScaledInstance(radius*3,radius*3,Image.SCALE_SMOOTH);

    }
    public BasicAsteroid(double x, double y, Vector2D velocity,int radius){
        super(new Vector2D(x,y),velocity);
        this.startTime = System.currentTimeMillis();
        try {
            im = ImageManager.loadImage("asteroid1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.radius = radius;
        im =  im.getScaledInstance(this.radius*3,this.radius*3,Image.SCALE_SMOOTH);

    }

    public static BasicAsteroid makeRandomAsteroid()  {
        //Sets the velocity and coordinates for a new random asteroid
        double x = (Math.random() * FRAME_HEIGHT);
        double y = (Math.random() * FRAME_WIDTH);
        double v = (Math.random() * MAX_SPEED);
        //randomly determines direction of asteroid
        if (Math.random() > 0.5)
            v *=-1;
            return new BasicAsteroid(x, y, new Vector2D(v, v));
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(im, (int) position.x-radius, (int) position.y-radius,null);
//        g.setColor(Color.red);
//        g.fillOval((int) position.x - radius, (int) position.y - radius, 2 * radius, 2 * radius);
    }
    @Override
    public void hit(){
        super.hit();
        radius/=2;
        if (radius > 4){
            BasicAsteroid a1 = new BasicAsteroid(position.x,position.y,new Vector2D((Math.random() * MAX_SPEED),(Math.random() * MAX_SPEED)),radius);
            BasicAsteroid a2 = new BasicAsteroid(position.x,position.y,new Vector2D((Math.random() * MAX_SPEED),(Math.random() * MAX_SPEED)),radius);
            childAsteroids.add(a1);
            childAsteroids.add(a2);
        }
        SoundManager.play(SoundManager.bangSmall);
    }
    @Override
    public void update(){
        super.update();
        if ((System.currentTimeMillis() - this.startTime)<2000)
            collisionsOff=true;
        else collisionsOff = false;
   }

}
