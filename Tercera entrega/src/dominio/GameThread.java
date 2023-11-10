package dominio;

import presentacion.Board;
import presentacion.TetrisGUI;

import javax.swing.*;

public class GameThread extends Thread {

    private Board board;
    private int score;
    private int scorePerLevel = 100;
    private int level = 1;
    private TetrisGUI gui;
    private int pause = 500;
    private int speedUpPerLevel = 50;

    public GameThread(Board ga, TetrisGUI gui) {
        board = ga;
        this.gui = gui;

        gui.updateScore(score);
        gui.updateLevel(level);
    }

    @Override
    public void run() {
        while (true){
            board.createShape();

            while (board.moveBlockDown()) {
                try {
                    Thread.sleep(pause);
                } catch (InterruptedException e) {
                    return;
                }
            }
            if(board.isBlockOutOfBounds()) {
                Tetris.gameOver(score);
                break;
            }
            board.moveBlockToBackground();
            score += board.clearLines();
            gui.updateScore(score);

            int lvl = score / scorePerLevel + 1;
            if (lvl > level) {
                level = lvl;
                gui.updateLevel(level);
                pause -= speedUpPerLevel;
            }
        }
    }

    public void setPause(int newPause) {
        pause = newPause;
    }

    public int getPause() {
        return pause;
    }
}
