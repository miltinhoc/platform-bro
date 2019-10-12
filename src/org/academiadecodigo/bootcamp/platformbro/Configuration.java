package org.academiadecodigo.bootcamp.platformbro;

public final class Configuration {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    public static final int PADDING = 10;
    public static final int GROUND_Y = 600 + PADDING;


    private Configuration() {
    }

    public static final class Player {

        public static final int HEIGHT = 100;
        public static final int WIDTH = 40;
        public static final int X = PADDING + 800;
        public static final int Y = PADDING + 500;
        public static final int MAX_JUMP = 200;

        private Player() {
        }
    }

}
