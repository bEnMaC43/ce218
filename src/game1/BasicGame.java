package game1;
import utilities.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static game1.Constants.DELAY;

public class BasicGame {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public List<GameObject> gameObjects;
    private int score;
    private int lives;
    private int level;


    public BasicGame(BasicShip ship) {
        score = 0;
        lives = 4;
        level=1;
        gameObjects = new ArrayList<GameObject>();
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            gameObjects.add(BasicAsteroid.makeRandomAsteroid());
        }
        gameObjects.add(ship);
    }

    public static void main(String[] args) throws Exception {
        BasicKeys keyController = new BasicKeys();
        BasicShip ship = new BasicShip(keyController);
        BasicGame game = new BasicGame(ship);
        BasicView view = new BasicView(game);
        new JEasyFrame(view, "Basic Game",keyController);
        while (true) {
            game.update();
            view.repaint();
            if (ship.alive!=true)
                System.exit(0);
            Thread.sleep(DELAY);

        }


    }

    public void update() {
        BasicShip ship = null;
        List<GameObject> dead = new ArrayList<>();
        List<BasicAsteroid> newAsteroids = new ArrayList<>();
        for (GameObject a : gameObjects){
            for(GameObject b : gameObjects)
                a.collisionHandling(b);
        }
        int asteroids=0;
        for (GameObject object : gameObjects) {
            if (object.alive)
                object.update();
            else {
                dead.add(object);

            }

            if (object instanceof BasicShip){
                ship = (BasicShip) object;

            }
            if (object instanceof  BasicAsteroid){
                asteroids++;
                for(BasicAsteroid childAsteroid : ((BasicAsteroid) object).childAsteroids){
                    newAsteroids.add(childAsteroid);
                }
            }
        }
        if (asteroids == 0){
            incLevel();
            gameObjects = new ArrayList<>();
            for (int i = 0; i < N_INITIAL_ASTEROIDS*getLevel(); i++) {
                gameObjects.add(BasicAsteroid.makeRandomAsteroid());
            }
            gameObjects.add(ship);

        }

        for (GameObject object : dead) {
            if (object instanceof BasicAsteroid) {
                incScore();
                gameObjects.remove(object);
            }
            else if (object instanceof BasicShip && lives > 0){
                loseLife();
                object.alive=true;
            }


        }
        for (BasicAsteroid a : newAsteroids){
            gameObjects.add(a);
        }

        if (ship!=null && ship.bullet != null)
            gameObjects.add(ship.bullet);

        else if (ship!=null)
            gameObjects.remove(ship.bullet);
    }

    public void incScore(){score++;}
    public int getScore(){return score;}
    public int getLives(){return lives;}
    public void loseLife(){lives--;}
    public int getLevel(){return level;}
    public void incLevel(){level++;}
}
