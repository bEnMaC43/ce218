package game1;

import java.util.Random;

public class MoveNShoot implements BasicController {
    Action action = new Action();

    static final int freq = 1;//lower the more frequent
    @Override
    public Action action() {
        Random random = new Random();
        if (System.currentTimeMillis() % 16==0)
            if (random.nextInt(freq) == 0) {
                if (random.nextBoolean()) {
                    action.shoot = true;
                    action.thrust = 0;
                    action.turn=0;
                }
                else if (random.nextBoolean()){
                    action.shoot = false;
                    if (action.turn == 0)
                        action.turn=1;
                    else action.turn*=-1;
                }
                else if (random.nextBoolean()) {
                    action.thrust = 1;
                    action.shoot = false;
                    action.turn=0;
                }
            }
            else{
                action.thrust = 0;
                action.shoot=false;
                action.turn=0;
            }

        return action;
    } }