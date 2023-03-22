package game1;
import utilities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game1.Constants.*;

public class BasicGame {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> gameObjects;
    public static playerShip ship;
    private int score;
    private static int lives;
    private int level;
    private boolean shipShieldOn;

    public static boolean gameOver;



    public BasicGame(playerShip ship)  {
        Random random = new Random();
        score = 0;
        lives = 4;
        level=1;
        shipShieldOn=false;
        gameObjects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            gameObjects.add(BasicAsteroid.makeRandomAsteroid());
        }
        gameObjects.add(ship);
        gameObjects.add(new Saucer(new Vector2D(random.nextInt(FRAME_WIDTH),random.nextInt(FRAME_HEIGHT)),new Vector2D(),new RotateNShoot()));



    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BasicKeys keyController = new BasicKeys();
        ship = new playerShip(keyController);
        BasicGame game = new BasicGame(ship);
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic Game",keyController);
        while (true) {
            game.update();
            view.repaint();
            if (ship.alive!=true)
                break;
            Thread.sleep(DELAY);
        }
        gameOver=true;
    }

    public void update() {
        List<GameObject> dead = new ArrayList<>();
        List<BasicAsteroid> newAsteroids = new ArrayList<>();
        List<Saucer> saucers = new ArrayList<>();
        List<GameObject> powerUps = new ArrayList<>();
        Random random = new Random();
        for (GameObject a : gameObjects){
            for(GameObject b : gameObjects)
                a.collisionHandling(b);
        }
        int enemies =0;
        for (GameObject object : gameObjects) {
            if (object.alive)
                object.update();
            else {
                dead.add(object);

            }

            if (object instanceof playerShip)
                ship = (playerShip) object;

            if (object instanceof Saucer) {
                saucers.add((Saucer) object);
                if (((Saucer) object).powerUp!=null)
                    powerUps.add(((Saucer) object).powerUp);

            }



            if (object instanceof  BasicAsteroid){
                enemies++;
                for(BasicAsteroid childAsteroid : ((BasicAsteroid) object).childAsteroids){
                    newAsteroids.add(childAsteroid);
                }

            }
            if (object instanceof Saucer)
                enemies++;
        }
        if (enemies == 0){
            incLevel();
            gameObjects = new ArrayList<>();
            for (int i = 0; i < N_INITIAL_ASTEROIDS*getLevel(); i++) {
                gameObjects.add(BasicAsteroid.makeRandomAsteroid());
            }
            for (int i = 0; i < getLevel(); i++)
                gameObjects.add(new Saucer(new Vector2D(random.nextInt(FRAME_WIDTH),random.nextInt(FRAME_HEIGHT)),new Vector2D(),new RotateNShoot()));
            gameObjects.add(ship);

        }

        for (GameObject object : dead) {
            if (object instanceof BasicAsteroid) {
                incScore();
            }

            else if (object instanceof playerShip && lives > 0){
                loseLife();
                object.alive=true;
                continue;
            }
            gameObjects.remove(object);


        }
        for (BasicAsteroid a : newAsteroids){
            gameObjects.add(a);
        }
        for (GameObject powerUp : powerUps)
            gameObjects.add(powerUp);

        if (ship!=null && ship.bullet != null)
            gameObjects.add(ship.bullet);

        else if (ship!=null)
            gameObjects.remove(ship.bullet);
        for (Saucer s : saucers) {
            if (s!=null && s.bullet != null)
                gameObjects.add(s.bullet);

            else if (s!=null)
                gameObjects.remove(s.bullet);

        }
        if (ship.shieleded && !shipShieldOn){
            incLife();
            shipShieldOn=true;
            SoundManager.play(SoundManager.shieldPickup);

        }
        if (!ship.shieleded){
            shipShieldOn=false;
        }
    }

    public void incScore(){score++;}
    public int getScore(){return score;}
    public int getLives(){return lives;}
    public void incLife(){lives++;}
    public void loseLife(){lives--;}
    public int getLevel(){return level;}
    public void incLevel(){level++;}
}
