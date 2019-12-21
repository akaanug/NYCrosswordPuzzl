/*
 * The MIT License
 *
 * Copyright 2019 kaan.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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

    /**
     * Assigns cells into the GUI form.
     * @param cells
     * @return an ArrayList of CellGUI objects assigned with corresponding cells
     */
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
