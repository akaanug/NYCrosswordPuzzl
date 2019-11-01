/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.mycompany.nycrosswordpuzzle.Constants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventListener;
import java.util.Map;
import javax.swing.Timer;

/**
 *
 * @author kaan
 */
public class GameFrame extends javax.swing.JFrame {

    Constants c;

    /**
     * Creates new form GameFrame
     *
     * @param b
     * @param c
     */
    public GameFrame( MainBoard b, Constants c ) {
        this.c = c;
        initComponents();
        board.setLayout( new java.awt.GridLayout() );
        board.add( b );

        int interval = 1000; // 1000 ms

        new Timer( interval, ( ActionEvent e ) -> {
            dateLabel.setText( getCurrentDate() + "" );
        } ).start();
    }

    public Date getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        Date date = new Date();
        return date;
    }

    public String getAcrossClues() {
        Map<Integer, String> across = c.getAcross();
        String clues = "";

        for ( int key : across.keySet() ) {
            clues = clues + key + ": " + across.get( key ) + "\n";
        }

        return clues;
    }

    public String getDownClues() {
        Map<Integer, String> down = c.getDown();
        String clues = "";

        for ( int key : down.keySet() ) {
            clues = clues + key + ": " + down.get( key ) + "\n";
        }

        return clues;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        board = new javax.swing.JPanel();
        acrossCluesPanel = new javax.swing.JPanel();
        acrossTitle = new javax.swing.JLabel();
        acrossClues = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        groupNick = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();
        downCluesPanel = new javax.swing.JPanel();
        downTitle = new javax.swing.JLabel();
        downClues = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout boardLayout = new javax.swing.GroupLayout(board);
        board.setLayout(boardLayout);
        boardLayout.setHorizontalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 612, Short.MAX_VALUE)
        );
        boardLayout.setVerticalGroup(
            boardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        acrossTitle.setText("Across");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText(getAcrossClues());
        acrossClues.setViewportView(jTextArea1);

        javax.swing.GroupLayout acrossCluesPanelLayout = new javax.swing.GroupLayout(acrossCluesPanel);
        acrossCluesPanel.setLayout(acrossCluesPanelLayout);
        acrossCluesPanelLayout.setHorizontalGroup(
            acrossCluesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acrossCluesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(acrossCluesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(acrossClues, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(acrossTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        acrossCluesPanelLayout.setVerticalGroup(
            acrossCluesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(acrossCluesPanelLayout.createSequentialGroup()
                .addComponent(acrossTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(acrossClues, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE))
        );

        groupNick.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        groupNick.setText("SOLVER");

        dateLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateLabel.setText( getCurrentDate() + "" );

        downTitle.setText("Down");

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jTextArea2.setText(getDownClues());
        downClues.setViewportView(jTextArea2);

        javax.swing.GroupLayout downCluesPanelLayout = new javax.swing.GroupLayout(downCluesPanel);
        downCluesPanel.setLayout(downCluesPanelLayout);
        downCluesPanelLayout.setHorizontalGroup(
            downCluesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downCluesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(downCluesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(downClues, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(downTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        downCluesPanelLayout.setVerticalGroup(
            downCluesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(downCluesPanelLayout.createSequentialGroup()
                .addComponent(downTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(downClues, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(board, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(acrossCluesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(groupNick)
                        .addGap(18, 18, 18)
                        .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(downCluesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(board, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(acrossCluesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(downCluesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupNick, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane acrossClues;
    private javax.swing.JPanel acrossCluesPanel;
    private javax.swing.JLabel acrossTitle;
    private javax.swing.JPanel board;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JScrollPane downClues;
    private javax.swing.JPanel downCluesPanel;
    private javax.swing.JLabel downTitle;
    private javax.swing.JLabel groupNick;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
