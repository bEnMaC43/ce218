package game1;
import utilities.Vector2D;
import java.awt.*;

public class MachineGun extends Interactable {

    MachineGun(Vector2D position, Vector2D velocity){
        super(position,velocity);
        COLOR = Color.red;
    }
}
