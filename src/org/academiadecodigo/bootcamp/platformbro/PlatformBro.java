package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.bootcamp.platformbro.objects.DropItemFactory;
import org.academiadecodigo.bootcamp.platformbro.objects.Droppable;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import static org.academiadecodigo.bootcamp.platformbro.Configuration.*;

public class PlatformBro {

    private Player player;
    private Rectangle floor;
    private Rectangle[] platforms;
    private Droppable[] droppables;
    private int droppablesStep;

    public PlatformBro(Player player) {
        this.player = player;
    }

    public void init() {
        new Rectangle(PADDING, PADDING, GAME_WIDTH, GAME_HEIGHT).draw();
        floor = new Rectangle(PADDING, GROUND_Y, GAME_WIDTH, GAME_HEIGHT - GROUND_Y + PADDING);
        floor.fill();

        platforms = initPlatforms();
        droppables = DropItemFactory.getDropItems(20);
        droppables[(int)(Math.random() * 20)].drop();

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
            moveDroppables();
        }
    }

    private void moveDroppables() {
        if (droppablesStep % 2 != 0) {
            droppablesStep++;
            return;
        }

        for (Droppable droppable : droppables) {
            if (droppable.getBottom() <= 0) {
                continue;
            }

            if (droppable.getTop() > PADDING + GAME_HEIGHT) {
                droppable.reset();
                continue;
            }

            droppable.drop();
        }

        if (droppablesStep == 750) {
            launchDroppable();
            droppablesStep = 0;
        }

        droppablesStep++;
    }

    private void launchDroppable() {
        int rndIndex = (int) (Math.random() * 20);

        if (droppables[rndIndex].isVisible()) {
            launchDroppable();
            return;
        }

        droppables[rndIndex].drop();
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
