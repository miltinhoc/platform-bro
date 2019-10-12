package org.academiadecodigo.bootcamp.platformbro.objects;

import org.academiadecodigo.bootcamp.platformbro.Configuration;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Shape;

import static org.academiadecodigo.bootcamp.platformbro.Configuration.*;

public class DropItem implements Droppable {

    private Rectangle rectangle;

    public DropItem(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    @Override
    public void drop() {
        rectangle.translate(0, 1);
    }

    @Override
    public void reset() {
        rectangle.translate(0, 0 - (GAME_HEIGHT + PADDING) - rectangle.getHeight() - 1);
    }

    @Override
    public boolean isVisible() {
        return getBottom() > 0 || getTop() > PADDING + GAME_HEIGHT;
    }

    @Override
    public int getLeft() {
        return rectangle.getX();
    }

    @Override
    public int getRight() {
        return rectangle.getX() + rectangle.getWidth();
    }

    @Override
    public int getTop() {
        return rectangle.getY();
    }

    @Override
    public int getBottom() {
        return rectangle.getY() + rectangle.getHeight();
    }
}
