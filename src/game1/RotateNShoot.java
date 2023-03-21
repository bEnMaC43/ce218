package game1;

import java.util.Random;

public class RotateNShoot implements BasicController {
    Action action = new Action();

    static final int freq = 1;//lower the more frequent
    @Override
    public Action action() {
        Random random = new Random();
        if (random.nextInt(freq) == 0) {
            if (random.nextBoolean()) {
                action.shoot = true;
                action.thrust = 0;
            } else if (random.nextBoolean()) {
                action.thrust = 1;
                action.shoot = false;
            }
        }
        else{
            action.thrust = 0;
            action.shoot=false;
        }

        return action;
    } }