package presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import dominio.*;

public class TetrisGUI extends JFrame {

    private Board board;
    private GameThread thread;

    //Componentes Menú in- game
    private JPanel placeholder;
    private JMenuBar mb;
    private JMenu menu;
    private JMenuItem opcionNuevo, opcionReiniciar, opcionSalir;

    //Various
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //In-game options
    private JLabel scoreDisplay, levelDisplay;
    private JButton backHomeButton, pauseButton, reanudeButton;

    public TetrisGUI() {
        prepareElementos();
        prepareAcciones();
    }

    public void prepareElementos(){
        placeholder = new JPanel();
        placeholder.setBounds(0,0,300, 485);
        board = new Board(placeholder, 20);
        add(board);
        setTitle("Tetris");
        scoreDisplay = new JLabel("Puntuación: 0");
        levelDisplay = new JLabel("Nivel: 1");
        Dimension size = scoreDisplay.getPreferredSize();
        scoreDisplay.setBounds(320,20,size.width + 50,size.height);
        levelDisplay.setBounds(320,40,size.width + 50,size.height);
        add(scoreDisplay);
        add(levelDisplay);
        backHomeButton = new JButton();
        backHomeButton.setText("Volver a menú");
        backHomeButton.setBounds(320, 350, backHomeButton.getPreferredSize().width, backHomeButton.getPreferredSize().height);
        add(backHomeButton);
        pauseButton = new JButton();
        pauseButton.setText("Pausa");
        pauseButton.setBounds(320,400,pauseButton.getPreferredSize().width, pauseButton.getPreferredSize().height);
        add(pauseButton);
        reanudeButton = new JButton();
        reanudeButton.setText("Reanudar");
        reanudeButton.setBounds(320, 450, reanudeButton.getPreferredSize().width, reanudeButton.getPreferredSize().height);
        //add(reanudeButton);
        setSize(screenSize.width/4, screenSize.height/2);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        prepareElementosMenu();
    }

    public void startGame() {
        board.initBackgroundArray();
        thread = new GameThread(board, this);
        thread.start();
    }

    public void updateScore(int score) {
        scoreDisplay.setText("Puntuación: " + score);
    }

    public void updateLevel(int level) {
        levelDisplay.setText("Nivel: " + level);
    }

    private void initControls() {
        InputMap im = this.getRootPane().getInputMap();
        ActionMap am = this.getRootPane().getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
        im.put(KeyStroke.getKeyStroke("DOWN"), "down");
        im.put(KeyStroke.getKeyStroke("UP"), "up");
        im.put(KeyStroke.getKeyStroke("LEFT"), "left");


        am.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveBlockRight();
            }
        });

        am.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.dropBlock();
            }
        });

        am.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.rotateBlock();
            }
        });

        am.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.moveBlockLeft();
            }
        });
    }

    public void prepareElementosMenu() {
        mb = new JMenuBar();
        menu = new JMenu("Menu");
        opcionNuevo = new JMenuItem("Nuevo");
        opcionReiniciar = new JMenuItem("Reiniciar juego");
        opcionSalir = new JMenuItem("Salir");



        menu.add(opcionNuevo);
        menu.add(opcionReiniciar);
        menu.add(opcionSalir);

        mb.add(menu);

        setJMenuBar(mb);
    }

    public void prepareAcciones() {
        initControls();
        TetrisGUI frame = this;
        Container contentpane = getContentPane();

        //In-game reanudar botón
        reanudeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.setPause(500);
            }
        });

        //In-game pausa botón
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.setPause(Integer.MAX_VALUE);
            }
        });

        //In-game volver a menú botón
        backHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                thread.interrupt();
                setVisible(false);
                Tetris.showStartup();
            }
        });


        //Salir (ventana)
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                int resultado = JOptionPane.showConfirmDialog(frame,
                        "¿Está seguro que quiere salir?", "¿Salir?", JOptionPane.YES_NO_OPTION);
                if (resultado == JOptionPane.YES_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                } else if (resultado == JOptionPane.NO_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        //Salir (botón menú)
        opcionSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int resultado = JOptionPane.showConfirmDialog(frame,
                        "¿Está seguro que quiere salir?", "¿Salir?", JOptionPane.YES_NO_OPTION);
                if (resultado == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else if (resultado == JOptionPane.NO_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        //Reiniciar
        opcionReiniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                int resultado = JOptionPane.showConfirmDialog(frame,
                        "¿Está seguro que quiere reiniciar el juego?", "¿Reiniciar?", JOptionPane.YES_NO_OPTION);
                if (resultado == JOptionPane.YES_OPTION) {
                    contentpane.removeAll();
                    prepareElementos();
                    prepareAcciones();
                    setVisible(true);
                } else if (resultado == JOptionPane.NO_OPTION) {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

}
