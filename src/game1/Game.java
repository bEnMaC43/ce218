package game1;
import utilities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static game1.Constants.*;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> gameObjects;
    public static PlayerShip ship;
    private int score;
    private static int lives;
    private int level;
    private boolean shipShieldOn;
    private int planets;

    public static boolean gameOver;



    public Game(PlayerShip ship)  {
        Random random = new Random();
        score = 0;
        lives = 4;
        level=1;
        shipShieldOn=false;
        gameObjects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            gameObjects.add(Asteroid.makeRandomAsteroid());
        }
        gameObjects.add(ship);
        gameObjects.add(new Saucer(new Vector2D(random.nextInt(FRAME_WIDTH),random.nextInt(FRAME_HEIGHT)),new Vector2D(),new MoveNShoot()));
        gameObjects.add(new Moon(new Vector2D(random.nextInt(FRAME_WIDTH),random.nextInt(FRAME_HEIGHT))));
        planets++;



    }

    public static void main(String[] args) throws IOException, InterruptedException {
        KeyController keyController = new KeyController();
        ship = new PlayerShip(keyController);
        Game game = new Game(ship);
        GameView view = new GameView(game);
        new JEasyFrame(view, "Basic Game",keyController);
        while (true) {
            game.update();
            view.repaint();
            if (ship.alive!=true)
                break;
            Thread.sleep(DELAY);
        }
        gameOver=true;
        leaderboardManager.addToFile(Integer.toString(game.getScore()));
    }

    public void update() {
        List<GameObject> dead = new ArrayList<>();
        List<Asteroid> newAsteroids = new ArrayList<>();
        List<Saucer> saucers = new ArrayList<>();
        List<GameObject> powerUps = new ArrayList<>();
        ArrayList<GameObject> nukedItems = new ArrayList<>();
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

            if (object instanceof PlayerShip)
                ship = (PlayerShip) object;

            if (object instanceof Saucer) {
                saucers.add((Saucer) object);
                if (((Saucer) object).powerUp!=null)
                    powerUps.add(((Saucer) object).powerUp);

            }



            if (object instanceof Asteroid){
                enemies++;
                for(Asteroid childAsteroid : ((Asteroid) object).childAsteroids){
                    newAsteroids.add(childAsteroid);
                }

            }
            if (object instanceof Saucer)
                enemies++;
        }
        if (enemies <=planets){
            incLevel();
            gameObjects = new ArrayList<>();
            for (int i = 0; i < N_INITIAL_ASTEROIDS*getLevel(); i++) {
                gameObjects.add(Asteroid.makeRandomAsteroid());
            }
            for (int i = 0; i < getLevel(); i++) {
                gameObjects.add(new Saucer(new Vector2D(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT)), new Vector2D(), new MoveNShoot()));
                if (i % 2 ==0 )
                    gameObjects.add(new TankSaucer(new Vector2D(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT)), new Vector2D(), new MoveNShoot()));

                planets = 0;
                if ((i) % 2 == 0){
                    planets++;
                    gameObjects.add(new Moon(new Vector2D(random.nextInt(FRAME_WIDTH), random.nextInt(FRAME_HEIGHT))));
                }
            }

            gameObjects.add(ship);

        }

        for (GameObject object : dead) {
            if (object instanceof Asteroid) {
                incScore();
                if (((Asteroid) object).droppable!=null)
                    gameObjects.add(((Asteroid) object).droppable);
            }

            else if (object instanceof PlayerShip && lives > 0){
                loseLife();
                object.alive=true;
                continue;
            }
            else if (object instanceof  Nuke){
                for (GameObject o : gameObjects)
                {
                    SoundManager.play(SoundManager.nuke);
                    if (!(o instanceof PlayerShip) )
                        nukedItems.add(o);
                }
            }
            for (GameObject item : nukedItems) {
                gameObjects.remove(item);
                incScore();
            }

            gameObjects.remove(object);


        }
        for (Asteroid a : newAsteroids){
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
        if (random.nextInt(0,1000)==1)
            gameObjects.add(new MachineGun(new Vector2D(random.nextInt(0,FRAME_WIDTH),random.nextInt(0,FRAME_HEIGHT)),new Vector2D()));
    }

    public void incScore(){score++;}
    public int getScore(){return score;}
    public int getLives(){return lives;}
    public void incLife(){lives++;}
    public void loseLife(){lives--;}
    public int getLevel(){return level;}
    public void incLevel(){level++;}
}
