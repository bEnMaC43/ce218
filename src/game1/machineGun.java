package game1;
import utilities.Vector2D;
import java.awt.*;

public class machineGun extends interactable {

    machineGun(Vector2D position, Vector2D velocity){
        super(position,velocity);
        COLOR = Color.red;
    }
}
