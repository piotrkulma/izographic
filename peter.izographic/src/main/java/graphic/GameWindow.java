package graphic;

import key.GameKeyListener;
import key.KeyIndicator;
import map.Map;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Piotr Kulma on 21.06.14.
 */
public class GameWindow extends JFrame implements KeyIndicator {
    public static final int KEY_UP              = 87;
    public static final int KEY_DOWN            = 83;
    public static final int KEY_LEFT            = 65;
    public static final int KEY_RIGHT           = 68;

    public static final int KEY_ARROW_LEFT      = 37;
    public static final int KEY_ARROW_RIGHT     = 39;

    private GameKeyListener gameKeyListener;
    private GraphicPanel graphicPanel;
    private Map map;

    public GameWindow(Map map) {
        super("graphic.GameWindow");

        this.map = map;

        setResizable(false);
        setSize(new Dimension(800, 600));

        initComponents();
        add(graphicPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        this.addKeyListener(gameKeyListener);
    }

    @Override
    public void changeMap(int c) {
        if(c == KEY_UP) {
            map.movePlayer(Map.DIRECTION_FORWARD, graphicPanel.getAngle());
        }else if(c == KEY_DOWN) {
            map.movePlayer(Map.DIRECTION_BACK, graphicPanel.getAngle());
        }else if(c == KEY_LEFT) {
            map.movePlayer(Map.DIRECTION_LEFT, graphicPanel.getAngle());
        }else if(c == KEY_RIGHT) {
            map.movePlayer(Map.DIRECTION_RIGHT, graphicPanel.getAngle());
        }else if(c == KEY_ARROW_LEFT) {
             if(graphicPanel.getAngle() > 360) {
                 graphicPanel.setAngle(0);
             }
            graphicPanel.setAngle(graphicPanel.getAngle() + 4.0d);
        }else if(c == KEY_ARROW_RIGHT) {
            if(graphicPanel.getAngle() < 0) {
                graphicPanel.setAngle(360);
            }
            graphicPanel.setAngle(graphicPanel.getAngle() - 4.0d);
        }

        graphicPanel.updateMap(map);
    }

    public void initComponents() {
        graphicPanel = new GraphicPanel(map);
        gameKeyListener = new GameKeyListener(this);
    }
}
