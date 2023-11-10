package dominio;

import presentacion.*;
import presentacion.TetrisGUI;

import javax.swing.*;

public class Tetris {
    private static TetrisGUI gui;
    private static Startup startup;
    private static LeaderboardForm leaderboard;

    public static void start() {
        gui = new TetrisGUI();
        gui.setVisible(true);
        gui.startGame();
    }

    public static void gameOver(int score) {
        leaderboard = new LeaderboardForm();
        String name = JOptionPane.showInputDialog("Game over\nPor favor ingrese su nombre: ");
        gui.setVisible(false);
        leaderboard.addPlayer(name, score);
    }

    public static void showLeaderboard() {
        leaderboard = new LeaderboardForm();
        leaderboard.setVisible(true);
    }

    public static void showStartup() {
        startup = new Startup();
        startup.setVisible(true);
    }

}
