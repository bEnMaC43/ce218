package game1;
import javax.swing.*;
import java.awt.*;

import static game1.Constants.FRAME_HEIGHT;
import static game1.Constants.FRAME_WIDTH;

public class BasicView extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;

    private BasicGame game;
    private Graphics2D graphics;

    public BasicView(BasicGame game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g0) {
        if (!game.gameObjects.isEmpty()) {
            graphics = (Graphics2D) g0;

            // paint the background
            graphics.setColor(BG_COLOR);
            graphics.fillRect(0, 0, getWidth(), getHeight());
            for (GameObject object : game.gameObjects) {
                if (object.alive)
                    object.draw(graphics);
            }
            graphics.setColor(Color.YELLOW);
            graphics.setFont(new Font("dialog", Font.BOLD, 20));
            graphics.drawString("Level: " + game.getLevel(), 20, FRAME_HEIGHT - 20);
            graphics.drawString("Score: " + game.getScore(), FRAME_WIDTH / 3 + 20, FRAME_HEIGHT - 20);
            graphics.drawString("Lives: " + game.getLives(), FRAME_WIDTH / 3 + FRAME_WIDTH / 3, FRAME_HEIGHT - 20);
        }
    }

    @Override

    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }

    public void closeFrame() {
        // Get the top-level container of this JComponent
        Container frame = getTopLevelAncestor();

        // Check if the container is a JFrame
        if (frame instanceof JFrame) {
            // Cast the container to a JFrame and dispose it
            JFrame jFrame = (JFrame) frame;
            jFrame.dispose();
        }
    }

}
