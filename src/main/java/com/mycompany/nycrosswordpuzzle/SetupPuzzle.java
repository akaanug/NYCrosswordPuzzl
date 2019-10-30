/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nycrosswordpuzzle;

import GUI.MainBoard;
import GUI.CellGUI;
import GUI.GameFrame;
import java.util.ArrayList;

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

    public SetupPuzzle() {
        c = new Constants();
        cellAmount = c.getCellAmount();
        cells = createAllCells();
        cellGUIs = assignCells( cells );

        b = new MainBoard( getWidth( cellAmount ), cellGUIs );

        GameFrame f = new GameFrame(b);
        f.setVisible(true);
    }

    private ArrayList createAllCells() {
        ArrayList<Cell> allCells;
        ArrayList<Boolean> isBlock = c.getIsBlock();
        allCells = new ArrayList();
        for ( int i = 0; i < cellAmount; i++ ) {
            String correctWord = "";
            String currentWord = "";
            int cellNo = i;
            boolean isTheBlock = (isBlock).get( i );
            int downClue = 0;
            int acrossClue = 0;
            allCells.add( new Cell( correctWord, currentWord, cellNo, isTheBlock, downClue,
                    acrossClue ) );
        }
        return allCells;
    }

    public final ArrayList<CellGUI> assignCells( ArrayList<Cell> cells ) {

        ArrayList<CellGUI> allGUICells = new ArrayList();
        cells.forEach( ( oneCell ) -> {
            allGUICells.add( new CellGUI( oneCell ) );
            System.out.println( oneCell );
        } );

        return allGUICells;

    }

    public final int getWidth( int cellAmount ) {
        return (int) (Math.sqrt( cellAmount ));
    }

}
