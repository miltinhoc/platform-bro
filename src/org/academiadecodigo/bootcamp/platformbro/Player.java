package org.academiadecodigo.bootcamp.platformbro;


import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Player implements KeyboardHandler {

    private static final int MAX_JUMP = 200;

    private Rectangle rectangle;
    private Direction direction;
    private boolean moving;
    private boolean jumping;
    private int currentJump;

    public void init() {
        rectangle = new Rectangle(110, 510, 40, 100);
        rectangle.setColor(Color.RED);
        rectangle.fill();

        Keyboard keyboard = new Keyboard(this);

        for (Direction direction : Direction.values()) {
            KeyboardEvent pressed = new KeyboardEvent();
            pressed.setKey(direction.getKey());
            pressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

            KeyboardEvent released = new KeyboardEvent();
            released.setKey(direction.getKey());
            released.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);

            keyboard.addEventListener(released);
            keyboard.addEventListener(pressed);
        }
    }

    public void move() {
        if (!moving && !jumping && currentJump == 0) {
            return;
        }

        if (currentJump == MAX_JUMP) {
            jumping = false;
        }

        int dX = moving && !movingOut(direction.getdX()) ? direction.getdX() : 0;
        int dY = (jumping & currentJump < MAX_JUMP ? -1 : (!jumping && currentJump == 0 ? 0 : 1));

        currentJump -= dY;

        rectangle.translate(dX, dY);

    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                direction = Direction.LEFT;
                moving = true;
                break;
            case KeyboardEvent.KEY_RIGHT:
                direction = Direction.RIGHT;
                moving = true;
                break;
            case KeyboardEvent.KEY_UP:
                jumping = currentJump == 0 || jumping;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_UP) {
            return;
        }
        direction = null;
        moving = false;
    }

    private boolean movingOut(int dX) {
        return rectangle.getX() + dX < 10 || rectangle.getX() + rectangle.getWidth() + dX > 1290;
    }

    private enum Direction {
        LEFT(-1, KeyboardEvent.KEY_LEFT),
        RIGHT(1, KeyboardEvent.KEY_RIGHT),
        UP(1, KeyboardEvent.KEY_UP);

        private int dX;
        private int key;

        Direction(int dX, int key) {
            this.dX = dX;
            this.key = key;
        }

        public int getdX() {
            return dX;
        }

        public int getKey() {
            return key;
        }
    }
}
