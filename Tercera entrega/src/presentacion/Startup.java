package presentacion;

import dominio.Tetris;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Startup extends JFrame{
    private JPanel mainPanel;
    private JButton salirButton;
    private JButton puntuacionesButton;
    private JButton jugarButton;
    private JLabel photoLabel;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public Startup() {
        setTitle("Tetris");
        preparAcciones();
        java.net.URL imgUrl = getClass().getResource("/Miscel/logo2-resize.png");
        photoLabel.setIcon(new ImageIcon(imgUrl));
        setSize(screenSize.width/4, screenSize.height/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void preparAcciones() {

        Startup frame = this;
        //Botón iniciar
        jugarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] options = {"Singleplayer", "Multiplayer", "Ninguno"};
                int n = JOptionPane.showOptionDialog(frame, "¿Qué modo quiere jugar?",
                        "Modo de juego", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[2]);
                if (n == 0) {
                    setVisible(false);
                    Tetris.start();
                } else if (n == 1) {
                    return;
                } else if (n == 2) {
                    System.exit(0);
                }
            }
        });

        //Botón puntuaciones
        puntuacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                Tetris.showLeaderboard();
            }
        });

        //Botón salir
        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new Startup();
        frame.setVisible(true);
    }
}
