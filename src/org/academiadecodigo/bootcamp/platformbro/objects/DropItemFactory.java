package org.academiadecodigo.bootcamp.platformbro.objects;

import org.academiadecodigo.bootcamp.platformbro.Configuration;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;

import static org.academiadecodigo.bootcamp.platformbro.Configuration.GAME_WIDTH;
import static org.academiadecodigo.bootcamp.platformbro.Configuration.PADDING;

public abstract class DropItemFactory {

    public static final int ITEM_SIZE = 50;

    public static DropItem[] getDropItems(int quantity) {
        DropItem[] items = new DropItem[quantity];
        final int spaceBetween = ((PADDING + GAME_WIDTH) - (quantity * ITEM_SIZE)) / (quantity + 1);
        int nextX = spaceBetween;

        for (int i = 0; i < quantity; i++) {
            Rectangle rectangle = new Rectangle(PADDING + nextX, 0 - ITEM_SIZE, ITEM_SIZE, ITEM_SIZE);
            rectangle.draw();
            items[i] = new DropItem(rectangle);
            nextX += ITEM_SIZE + spaceBetween;
        }

        return items;
    }
}
