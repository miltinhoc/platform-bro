package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Shape;

public class CollisionDetector {
    private Rectangle[] rectangles;

    public CollisionDetector(Rectangle[] rectangles) {
        this.rectangles = rectangles;
    }
    
    public boolean willCollideSideways(Shape shape, int dX) {
        for (Rectangle platform : rectangles) {
            int left = shape.getX() + dX;
            int right = shape.getX() + shape.getWidth() + dX;
            int top = shape.getY();
            int bottom = shape.getY() + shape.getHeight();

            if (right > platform.getX()
                    && left < platform.getX() + platform.getWidth()
                    && bottom > platform.getY()
                    && top < platform.getY() + platform.getHeight()
            ) {
                return true;
            }
        }

        return false;
    }

    public boolean willCollideOnTop(Shape shape) {
        for (Rectangle platform : rectangles) {
            int left = shape.getX();
            int right = shape.getX() + shape.getWidth();
            int top = shape.getY() - 1;
            int bottom = shape.getY() + shape.getHeight() - 1;

            if (right > platform.getX()
                    && left < platform.getX() + platform.getWidth()
                    && bottom > platform.getY()
                    && top < platform.getY() + platform.getHeight()
            ) {
                return true;
            }
        }

        return false;
    }

    public boolean willCollideOnBottom(Shape shape) {
        for (Rectangle platform : rectangles) {
            int left = shape.getX();
            int right = shape.getX() + shape.getWidth();
            int top = shape.getY() + 1;
            int bottom = shape.getY() + shape.getHeight() + 1;

            if (right > platform.getX()
                    && left < platform.getX() + platform.getWidth()
                    && bottom > platform.getY()
                    && top < platform.getY() + platform.getHeight()
            ) {
                return true;
            }
        }

        return false;
    }
}
