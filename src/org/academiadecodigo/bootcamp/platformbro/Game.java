package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;

public class Game {

    private Player player;
    private Rectangle floor;

    public Game(Player player) {
        this.player = player;
    }

    public void init() {
        new Rectangle(10,10,1280,720).draw();
        floor = new Rectangle(10,610,1280,120);
        floor.fill();

        player.init();
    }

    public void start() {
        while (true) {
            player.move();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
