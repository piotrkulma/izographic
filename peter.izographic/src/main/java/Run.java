import graphic.GameWindow;
import map.Map;

import java.awt.*;

/**
 * Created by Piotr Kulma on 21.06.14.
 */
public class Run {
    public static void main(String... args) {
        final Map map = new Map();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow(map);
            }
        });
    }
}
