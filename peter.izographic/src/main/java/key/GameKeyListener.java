package key;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Piotr Kulma on 21.06.14.
 */
public class GameKeyListener implements KeyListener {
    public boolean lock;
    public KeyIndicator indicator;
    public Map<Character, Character> keyFilter;

    public GameKeyListener(KeyIndicator indicator) {
        lock = false;
        keyFilter = new HashMap<Character, Character>();
        this.indicator = indicator;
    }

    public boolean isInKeyFilter(char c) {
        return true;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == 37 || e.getKeyCode() == 39) {
            indicator.changeMap(e.getKeyCode());
            lock = false;
        } else {
            if (!lock && isInKeyFilter(e.getKeyChar())) {
                indicator.changeMap(e.getKeyCode());
            }
            lock = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        lock = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
