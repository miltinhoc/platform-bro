package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.bootcamp.platformbro.objects.DropItemFactory;
import org.academiadecodigo.bootcamp.platformbro.objects.Droppable;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import static org.academiadecodigo.bootcamp.platformbro.Configuration.*;

public class PlatformBro implements KeyboardHandler {

    private Player player;
    private Rectangle floor;
    private Rectangle[] platforms;
    private Droppable[] droppables;
    private int droppablesStep;
    private boolean gameOver;

    public PlatformBro(Player player) {
        this.player = player;
    }

    public void init() {
        new Rectangle(PADDING, PADDING, GAME_WIDTH, GAME_HEIGHT).draw();
        floor = new Rectangle(PADDING, GROUND_Y, GAME_WIDTH, GAME_HEIGHT - GROUND_Y + PADDING);
        floor.fill();

        platforms = initPlatforms();
        droppables = DropItemFactory.getDropItems(20);
        droppables[(int) (Math.random() * 20)].drop();

        player.setCollisionDetector(new CollisionDetector(platforms));
        player.init();
    }

    public void start() {
        while (!gameOver) {
            player.move();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            moveDroppables();
            if (player.hasCollided(droppables)) {
                System.out.println("GAME OVER");
                gameOver = true;
            }
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

            if (droppable.getTop() > GROUND_Y) {
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

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                player.setDirection(Player.Direction.LEFT);
                player.setMoving(true);
                break;
            case KeyboardEvent.KEY_RIGHT:
                player.setDirection(Player.Direction.RIGHT);
                player.setMoving(true);
                break;
            case KeyboardEvent.KEY_UP:
                if (!player.isFalling()) {
                    player.setJumping(true);
                }
                break;
            case KeyboardEvent.KEY_R:
                if (gameOver) {
                    gameOver = false;
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_UP) {
            return;
        }
        player.setDirection(null);
        player.setMoving(false);
    }

    public static void main(String[] args) {
        PlatformBro platformBro = new PlatformBro(new Player());
        platformBro.init();

        initKeyboard(platformBro);

        while (true) {
            platformBro.start();

            while (platformBro.gameOver) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            platformBro.reset();
        }
    }

    private void reset() {
        for (Droppable droppable : droppables) {
            if (droppable.getBottom() > 0) {
                droppable.reset();
            }
        }
    }

    private static void initKeyboard(KeyboardHandler handler) {
        Keyboard keyboard = new Keyboard(handler);

        for (Player.Direction direction : Player.Direction.values()) {
            KeyboardEvent pressed = new KeyboardEvent();
            pressed.setKey(direction.getKey());
            pressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

            KeyboardEvent released = new KeyboardEvent();
            released.setKey(direction.getKey());
            released.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);

            keyboard.addEventListener(released);
            keyboard.addEventListener(pressed);
        }

        KeyboardEvent reset = new KeyboardEvent();
        reset.setKey(KeyboardEvent.KEY_R);
        reset.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        keyboard.addEventListener(reset);

    }
}
