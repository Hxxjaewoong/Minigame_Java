package gui;

import java.awt.Dimension;
import java.awt.Rectangle;

public interface GamePanel {
    Rectangle gameArea = new Rectangle(0, 0, 460, 520);
    Dimension gameInfoArea = new Dimension(460, 50);
    Dimension gamePlayArea = new Dimension(460, 460);
}
