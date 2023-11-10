package dominio;

import java.awt.*;
import java.util.Random;


public class Block {

    private int[][] currentShape;
    private Color color;
    private int x,y;
    private int[][][] shapes;
    private int currentRotation;
    private Color[] colors = {Color.BLUE, Color.RED, Color.GREEN, Color.PINK, Color.YELLOW, Color.CYAN};

    public Block(int[][] shape) {
        currentShape = shape;
        initShapes();
    }

    private void initShapes() {
        shapes = new int[4][][];

        for (int i=0; i < 4; i++) {
            int r = currentShape[0].length;
            int c = currentShape.length;
            shapes[i] = new int[r][c];
            for (int y=0; y < r; y++) {
                for (int x=0; x < c; x++) {
                    shapes[i][y][x] = currentShape[c-x-1][y];
                }
            }
            currentShape = shapes[i];
        }
    }

    public void create(int gridWidth) {
        Random r = new Random();
        currentRotation = r.nextInt(4);
        currentShape = shapes[currentRotation];
        y = -getHeight();
        x = (gridWidth - getWidth()) / 2;
        color = colors[r.nextInt(colors.length)];
    }

    public void moveDown() {y++;}
    public void moveRight() {x++;}
    public void moveLeft() {x--;}

    public void rotate() {
        currentRotation++;
        if (currentRotation > 3) currentRotation = 0;
        currentShape = shapes[currentRotation];
    }

    public Color getColor() {return color;}
    public int[][] getCurrentShape() {return currentShape;}
    public int getHeight() {return currentShape.length;}
    public int getWidth() {return currentShape[0].length;}
    public int getX() {return x;}
    public int getY() {return y;}
    public int getLeftEdge() {
        return x;
    }
    public int getRightEdge() {
        return x + getWidth();
    }
    public int getBottomEdge() {return y + getHeight();}

    public void setX(int newX) {x=newX;}
    public void setY(int newY) {y=newY;}
}