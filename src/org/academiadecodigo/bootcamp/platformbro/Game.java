package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Game {

    private Player player;
    private Rectangle floor;
    private Rectangle[] platforms;

    public Game(Player player) {
        this.player = player;
    }

    public void init() {
        new Rectangle(10,10,1280,720).draw();
        floor = new Rectangle(10,610,1280,120);
        floor.fill();

        player.init();

        platforms = initPlatforms();
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
}
