package org.academiadecodigo.bootcamp.platformbro.objects;

public interface Droppable {
    void drop();
    void reset();
    boolean isVisible();
    int getLeft();
    int getRight();
    int getTop();
    int getBottom();
}
