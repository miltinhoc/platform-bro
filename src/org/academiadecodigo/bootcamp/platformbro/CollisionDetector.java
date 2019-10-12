package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Shape;

public class CollisionDetector {
    private Rectangle[] rectangles;

    public CollisionDetector(Rectangle[] rectangles) {
        this.rectangles = rectangles;
    }

    public boolean willCollide(Shape shape, int dX, int dY) {
        for (Rectangle platform : rectangles) {
            int left = shape.getX() + dX;
            int right = shape.getX() + shape.getWidth() + dX;
            int top = shape.getY() + dY;
            int bottom = shape.getY() + shape.getHeight() + dY;

            if (right > platform.getX()
                    && left < platform.getX() + platform.getWidth()
                    && bottom > platform.getY()
                    && top < platform.getY() + platform.getHeight()) {
                return true;
            }
        }

        return false;
    }
}
