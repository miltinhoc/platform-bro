package org.academiadecodigo.bootcamp.platformbro;


import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

public class Player implements KeyboardHandler {

    private Rectangle rectangle;
    private Direction direction;
    private boolean jumping;

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
        if (direction == null || movingOut(direction.getdX())) {
            return;
        }

        rectangle.translate(direction.getdX(), 0);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        switch (keyboardEvent.getKey()) {
            case KeyboardEvent.KEY_LEFT:
                direction = Direction.LEFT;
                break;
            case KeyboardEvent.KEY_RIGHT:
                direction = Direction.RIGHT;
                break;
            case KeyboardEvent.KEY_UP:
                jumping = true;
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
        if (keyboardEvent.getKey() == KeyboardEvent.KEY_UP) {
            return;
        }
        direction = null;
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
