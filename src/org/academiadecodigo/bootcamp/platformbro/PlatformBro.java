package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import static org.academiadecodigo.bootcamp.platformbro.Configuration.*;

public class PlatformBro {

    private Player player;
    private Rectangle floor;
    private Rectangle[] platforms;

    public PlatformBro(Player player) {
        this.player = player;
    }

    public void init() {
        new Rectangle(PADDING,PADDING,GAME_WIDTH,GAME_HEIGHT).draw();
        floor = new Rectangle(PADDING,GROUND_Y, GAME_WIDTH,GAME_HEIGHT - GROUND_Y + PADDING);
        floor.fill();

        platforms = initPlatforms();

        player.setCollisionDetector(new CollisionDetector(platforms));
        player.init();

    }

    public void start() {
        while (true) {
            player.move();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Rectangle[] initPlatforms() {
        Rectangle[] platforms = new Rectangle[2];

        platforms[0] = new Rectangle(500, 500, 250, 50);
        platforms[0].setColor(Color.BLUE);
        platforms[0].fill();

        platforms[1] = new Rectangle(900, 400, 250, 50);
        platforms[1].setColor(Color.BLUE);
        platforms[1].fill();

        return platforms;
    }

    public static void main(String[] args) {
        PlatformBro platformBro = new PlatformBro(new Player());
        platformBro.init();
        platformBro.start();
    }
}
