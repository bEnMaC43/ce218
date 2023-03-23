package game1;

import utilities.Vector2D;

import java.awt.*;

public class Shield extends Interactable {

    Shield(Vector2D position, Vector2D velocity) {
        super(position, velocity);
        COLOR = Color.green;
    }
}
