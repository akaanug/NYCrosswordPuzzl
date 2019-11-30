/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nycrosswordpuzzle;

import GUI.MainBoard;
import GUI.CellGUI;
import GUI.GameFrame;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Create cells and assign properties
 *
 * @author kaan
 */
public class SetupPuzzle {

    Constants c;
    int cellAmount;
    private final MainBoard b;
    private final ArrayList<Cell> cells;
    private final ArrayList<CellGUI> cellGUIs;
    HashMap<Integer, Integer> clueLabelsOnCells;
    private final ArrayList answers;

    public SetupPuzzle() throws SQLException {
        c = new Constants();
        cellAmount = c.getCellAmount();
        clueLabelsOnCells = c.getClueLabelsOnCells();
        answers = c.getAnswers();
        cells = createAllCells();

        cellGUIs = assignCells( cells );

        b = new MainBoard( getWidth( cellAmount ), cellGUIs );

        GameFrame f = new GameFrame( b, c );
        f.setVisible( true );
    }

    private ArrayList createAllCells() {
        ArrayList allCells;
        ArrayList<Boolean> isBlock = c.getIsBlock();
        allCells = new ArrayList();
        int count = 0;
        for ( int i = 0; i < cellAmount; i++ ) {
            String correctWord = "";
            String currentWord = "";
            int cellNo = i;
            boolean isTheBlock = (isBlock).get( i );
            int downClue = 0;
            int acrossClue = 0;
            int clueLabelOnCell = getClueLabelOnCell( i );

            if ( !isTheBlock ) {
                correctWord = (String) answers.get( count );
//                System.out.println( "Current Word: " + correctWord );
                count++;
            }

            allCells.add( new Cell( correctWord, currentWord, cellNo, isTheBlock, downClue,
                    acrossClue, clueLabelOnCell ) );
        }
        return allCells;
    }

    public final ArrayList<CellGUI> assignCells( ArrayList<Cell> cells ) {

        ArrayList<CellGUI> allGUICells = new ArrayList();

        cells.forEach( ( oneCell ) -> {
            allGUICells.add( new CellGUI( oneCell ) );
        } );

        return allGUICells;

    }

    public final int getWidth( int cellAmount ) {
        return (int) (Math.sqrt( cellAmount ));
    }

    public final int getClueLabelOnCell( int cellNum ) {
        return clueLabelsOnCells.get( cellNum );
    }

}
