package org.academiadecodigo.bootcamp.platformbro;


import org.academiadecodigo.bootcamp.platformbro.objects.Droppable;
import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;

import static org.academiadecodigo.bootcamp.platformbro.Configuration.*;
import static org.academiadecodigo.bootcamp.platformbro.Configuration.Player.*;

public class Player implements KeyboardHandler {

    private CollisionDetector collisionDetector;
    private Rectangle rectangle;
    private Direction direction;
    private boolean moving;
    private boolean jumping;
    private boolean falling;
    private int currentJump;

    public void init() {
        rectangle = new Rectangle(X, Y, WIDTH, HEIGHT);
        rectangle.setColor(Color.RED);
        rectangle.fill();
    }

    public void move() {
        falling = !willCollideVertically(1) && isAboveGround();

        if (!falling && !moving && !jumping) {
            return;
        }

        int dX = moving && !movingOut(direction.getdX()) && !willCollideSideways() ? direction.getdX() : 0;
        int dY = 0;

        if (jumping) {
            if (currentJump < MAX_JUMP && !willCollideVertically(-1)) {
                dY = -1;
                currentJump++;
            } else {
                currentJump = 0;
                jumping = false;
            }
        } else if (falling) {
            dY = 1;
        }

        rectangle.translate(dX, dY);
    }

    public boolean hasCollided(Droppable[] droppables) {
        return collisionDetector.hasCollide(droppables, rectangle);
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
                if (!falling) {
                    jumping = true;
                }
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
        return rectangle.getX() + dX < PADDING || rectangle.getX() + rectangle.getWidth() + dX > GAME_WIDTH + PADDING;
    }

    private boolean willCollideSideways() {
        if (collisionDetector == null || direction == null) {
            return false;
        }

        return collisionDetector.willCollide(rectangle, direction.getdX(), 0);
    }

    private boolean willCollideVertically(int dY) {
        if (collisionDetector == null) {
            return false;
        }

        return collisionDetector.willCollide(rectangle, 0, dY);
    }

    private boolean isAboveGround() {
        return rectangle.getY() + rectangle.getHeight() < GROUND_Y;
    }

//    private void setupKeyboard() {
//        Keyboard keyboard = new Keyboard(this);
//
//        for (Direction direction : Direction.values()) {
//            KeyboardEvent pressed = new KeyboardEvent();
//            pressed.setKey(direction.getKey());
//            pressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);
//
//            KeyboardEvent released = new KeyboardEvent();
//            released.setKey(direction.getKey());
//            released.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);
//
//            keyboard.addEventListener(released);
//            keyboard.addEventListener(pressed);
//        }
//    }

    public boolean isFalling() {
        return falling;
    }

    public void setCollisionDetector(CollisionDetector collisionDetector) {
        this.collisionDetector = collisionDetector;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public enum Direction {
        LEFT(-1, KeyboardEvent.KEY_LEFT),
        RIGHT(1, KeyboardEvent.KEY_RIGHT),
        UP(-1, KeyboardEvent.KEY_UP);

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
