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
    private int clueLabel;

    public Cell( String correctWord, String currentWord, int cellNo, boolean isBlock, int downClue, int acrossClue, int clueLabel ) {

        this.cellNo = cellNo;
        this.correctWord = correctWord;
        this.currentWord = currentWord;
        this.isBlock = isBlock;
        this.downClue = downClue;
        this.acrossClue = acrossClue;
        this.clueLabel = clueLabel;

    }

    public String getClueLabelAsString() {
        if( isBlock ) {
            return null;
        }
        
        if ( clueLabel == 0 ) {
            return "";
        } else {
            return clueLabel + "";
        }
    }

    public void setClueLabel( int clueLabel ) {
        this.clueLabel = clueLabel;
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
