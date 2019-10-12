package org.academiadecodigo.bootcamp.platformbro;

import org.academiadecodigo.bootcamp.platformbro.objects.Droppable;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Shape;

public class CollisionDetector {
    private Rectangle[] rectangles;

    public CollisionDetector(Rectangle[] rectangles) {
        this.rectangles = rectangles;
    }

    public boolean willCollide(Shape shape, int dX, int dY) {
        for (Rectangle platform : rectangles) {
            Limit shapeLimit = Limit.getFromShape(shape);
            shapeLimit.left += dX;
            shapeLimit.right += dX;
            shapeLimit.top += dY;
            shapeLimit.bottom += dY;

            if (checkCollision(shapeLimit, Limit.getFromShape(platform))) {
                return true;
            }
        }

        return false;
    }

    public boolean hasCollide(Droppable[] droppables, Shape shape) {
        for (Droppable droppable : droppables) {
           if (checkCollision(Limit.getFromShape(shape), Limit.getFromDroppable(droppable))) {
               return true;
           }
        }

        return false;
    }

//    private boolean checkCollision(Shape shape1, Shape shape2, int d1X, int d1Y) {
//        int left = shape1.getX() + d1X;
//        int right = shape1.getX() + shape1.getWidth() + d1X;
//        int top = shape1.getY() + d1Y;
//        int bottom = shape1.getY() + shape1.getHeight() + d1Y;
//
//        return right > shape2.getX()
//                && left < shape2.getX() + shape2.getWidth()
//                && bottom > shape2.getY()
//                && top < shape2.getY() + shape2.getHeight();
//    }

    private boolean checkCollision(Limit limit1, Limit limit2) {
        return limit1.right > limit2.left &&
                limit1.left < limit2.right &&
                limit1.bottom > limit2.top &&
                limit1.top < limit2.bottom;
    }

    private static class Limit {
        private int top;
        private int right;
        private int bottom;
        private int left;

        private Limit(int top, int right, int bottom, int left) {
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.left = left;
        }

        public static Limit getFromShape(Shape shape) {
            return new Limit(shape.getY(),
                    shape.getX() + shape.getWidth(),
                    shape.getY() + shape.getHeight(),
                    shape.getX());
        }

        public static Limit getFromDroppable(Droppable droppable) {
            return new Limit(droppable.getTop(), droppable.getRight(), droppable.getBottom(), droppable.getLeft());
        }
    }
}
