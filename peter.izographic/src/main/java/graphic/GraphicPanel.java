package graphic;

import map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Piotr Kulma on 21.06.14.
 */
class GraphicPanel extends JPanel{
    private final int elementWidth = 12;
    private final int elementHeight = 12;

    private final double viewAngle = 46;
    private final double angleStep = 1;
    private final int mapSize = 44;

    private double angle;

    private Map map;

    public GraphicPanel(Map map) {
        this.map = map;
        this.angle = 0;
    }

    public void updateMap(Map map) {
        this.map = map;
        this.repaint();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = this.getWidth();
        int height = this.getHeight();
        double rectangleWidth = width / (viewAngle / angleStep);

        BufferedImage gameImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        BufferedImage minimapImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = (Graphics2D) g;
        Graphics2D gameGraphics = gameImage.createGraphics();
        Graphics2D miniMapGraphics = minimapImage.createGraphics();

        drawMinimap(miniMapGraphics);

        int counter = 0;

        for(double i=0; i<=viewAngle; i+=angleStep) {
            Point playerPosition = map.findPlayerGraphicCoordinates();
            double length = drawRay(playerPosition, miniMapGraphics, minimapImage, angle + (25 - i));
            max(length);
            gameGraphics.setColor(new Color(0, 0, 255-(int)((length * 255) / 600)));
            gameGraphics.fillRect(
                    (int) rectangleWidth * counter++,
                    (int) (length * 750) / 900,
                    (int) rectangleWidth,
                    (height - 2 * (int) (length * 750) / 900));
        }


        g2d.drawImage(gameImage, 0, 0, width, height, this);
    }

    double mlen = 0;

    public void max(double max) {
        if(max > mlen) {
            System.out.println(max);
            mlen = max;
        }
    }

    private void drawMinimap(Graphics2D graphics) {
        for(int i=0; i<map.getMap().length; i++) {
            for (int j = 0; j < map.getMap()[0].length; j++) {
                if (map.getMap()[i][j] == Map.MAP_ELEMENT_WALL) {
                    graphics.setColor(Color.DARK_GRAY);
                    graphics.drawRect(elementWidth * (j + 1), elementHeight * (i + 1), elementWidth, elementHeight);
                    graphics.fillRect(elementWidth * (j + 1), elementHeight * (i + 1), elementWidth, elementHeight);
                } else if (map.getMap()[i][j] == Map.MAP_ELEMENT_PLAYER) {
                    graphics.setColor(Color.blue);
                    graphics.drawRect(elementWidth * (j + 1), elementHeight * (i + 1), elementWidth, elementHeight);
                }
            }
        }
    }

    private double drawRay(Point playerPosition, Graphics2D graphics, BufferedImage image, double angle) {
        for(double len=0; len<=1000; len+=1) {
            int x1 = (elementWidth * (playerPosition.x + 1)) + (elementWidth / 2);
            int y1 = (elementHeight * (playerPosition.y + 1)) + (elementHeight / 2);
            int x2 = x1 + (int) (Math.cos(degToRad(-angle)) * len);
            int y2 = y1 + (int) (Math.sin(degToRad(angle)) * len);

            int rgb[];
            try {
                rgb = getRGBMatrix(image.getRGB(x2, y2));
            }catch(ArrayIndexOutOfBoundsException e) {
                rgb = new int[]{64, 64, 64};
            }
            //graphics.setColor(Color.blue);
            //graphics.drawLine(x1, y1, x2, y2);

            if(wall(rgb)) {
                return len;
            }
        }

        return 0;
    }

    private boolean wall(int[] rgb) {
        return (rgb[0] == 64 && rgb[1] == 64 && rgb[2] == 64);
    }

    private int[] getRGBMatrix(int argb) {
        return new int[]{
                (argb >> 16) & 0xff,
                (argb >> 8) & 0xff,
                (argb) & 0xff
        };
    }

    private double degToRad(double deg) {
        return deg * (Math.PI / 180.0d);
    }
}
