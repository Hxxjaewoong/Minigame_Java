package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

public interface GamePanel {
    Rectangle gameArea = new Rectangle(0, 0, 460, 520);
    Dimension gameInfoArea = new Dimension(460, 50);
    Dimension gamePlayArea = new Dimension(460, 460);

    Font infoFont = new Font("Sanserif", Font.BOLD, 15);
    Font game1ButtonFont = new Font("Sanserif", Font.BOLD, 10);
    Font game2ButtonFont = new Font("Sanserif", Font.BOLD, 30);
}
