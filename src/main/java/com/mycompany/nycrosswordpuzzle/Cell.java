/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nycrosswordpuzzle;

/**
 *
 * @author kaan
 */
public class Cell {

    private final String correctWord;
    private String currentWord;
    private final int cellNo;
    private final boolean isBlock;
    private final int downClue;
    private final int acrossClue;

    public Cell( String correctWord, String currentWord, int cellNo, boolean isBlock, int downClue, int acrossClue ) {

        this.cellNo = cellNo;
        this.correctWord = correctWord;
        this.currentWord = currentWord;
        this.isBlock = isBlock;
        this.downClue = downClue;
        this.acrossClue = acrossClue;

    }

    public void setCurrentWord( String currentWord ) {
        this.currentWord = currentWord;
    }

    public String getCorrectWord() {
        return correctWord;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public int getCellNo() {
        return cellNo;
    }

    public boolean isIsBlock() {
        return isBlock;
    }

    public int getDownClue() {
        return downClue;
    }

    public int getAcrossClue() {
        return acrossClue;
    }

}
