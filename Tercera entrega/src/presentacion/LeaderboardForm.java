package presentacion;

import dominio.Tetris;

import javax.naming.ldap.SortKey;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class LeaderboardForm extends JFrame{
    private JScrollPane jScrollPane1;
    private JLabel titleLabel;
    private JButton backHomeButton;
    private JPanel mainPanel;
    private JTable leaderboard;
    private DefaultTableModel tm;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private String leaderboardFileName = "leaderboard";

    public LeaderboardForm() {
        setTitle("Puntuaciones");
        titleLabel.setText("Puntuaciones");
        setSize(screenSize.width/4, screenSize.height/2);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
        initTableData();
        initTableSorter();
    }

    private void initTableSorter() {
        leaderboard.setAutoCreateRowSorter(true);
    }

    private void initTableData() {
        Vector ci = new Vector();
        ci.add("Nombre");
        ci.add("Puntuación");
        tm = (DefaultTableModel) leaderboard.getModel();
        try {
            FileInputStream fs = new FileInputStream(leaderboardFileName);
            ObjectInputStream os = new ObjectInputStream(fs);
            tm.setDataVector((Vector<Vector>) os.readObject(), ci);
            fs.close();
            os.close();
        } catch(Exception e) {}
    }

    private void saveLeaderboard() {
        try {
            FileOutputStream fs = new FileOutputStream(leaderboardFileName);
            ObjectOutputStream os = new ObjectOutputStream(fs);

            os.writeObject(tm.getDataVector());
            os.close();
            fs.close();
        } catch (Exception e) {}
    }


    public void addPlayer(String name, int score) {
        tm.addRow(new Object[] {name, score});
        saveLeaderboard();
        setVisible(true);
    }

    public void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        leaderboard = new javax.swing.JTable();
        backHomeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        leaderboard.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Nombre", "Puntuacion"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(leaderboard);

        backHomeButton.setText("Volver a menú");
        backHomeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setVisible(false);
                Tetris.showStartup();
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(backHomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(backHomeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
    }


    public static void main(String[] args) {
        JFrame frame = new LeaderboardForm();
        frame.setVisible(true);
    }
}
