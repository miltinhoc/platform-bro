package org.academiadecodigo.bootcamp.platformbro;

public class PlatformBro {
    public static void main(String[] args) {
        Game game = new Game(new Player());
        game.init();
        game.start();
    }
}
