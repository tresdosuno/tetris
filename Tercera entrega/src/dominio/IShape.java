package dominio;

import dominio.Block;

public class IShape extends Block {

    public IShape() {
        super(new int[][] {{1,1,1,1}});
    }

    @Override
    public void rotate() {
        super.rotate();
        if (getWidth() == 1) {
            setX(getX() + 1);
            setY(getY() - 1);
        } else {
            setX(getX() - 1);
            setY(getY() + 1);
        }
    }
}
