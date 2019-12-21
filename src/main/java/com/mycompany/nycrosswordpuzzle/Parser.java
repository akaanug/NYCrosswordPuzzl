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

import java.io.FileNotFoundException;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

public class Parser {

    static Calendar cal = Calendar.getInstance();
    static int day = cal.get( Calendar.DAY_OF_MONTH );
    static int month = cal.get( Calendar.MONTH );
    static int year = cal.get( Calendar.YEAR );

    public static ArrayList getClues() {
        String url = "https://www.nytimes.com/crosswords/game/mini";
        ArrayList<String> clues = new ArrayList();

        try {
            Document doc = Jsoup.parse( new URL( url ), 10000 );
            Elements newelm = doc.getElementsByClass( "Clue-text--3lZl7" );
            PrintWriter pw = new PrintWriter( "clues" + day + month + year + ".txt" );
            for ( int i = 0; i < newelm.size(); i++ ) {
                clues.add( (String) (newelm.get( i ).text()) );
                pw.println( newelm.get( i ).text() );
            }
            pw.close();
        } catch ( IOException e ) {
            System.out.println( e );
        }
        return clues;
    }

    /**
     *
     * @return Retrieves all of the the black box indexes
     */
    public static ArrayList<Integer> getBoxes() {
        String url = "https://www.nytimes.com/crosswords/game/mini";
        ArrayList<Integer> boxes = new ArrayList();

        try {
            Document doc = Jsoup.parse( new URL( url ), 10000 );
            Elements newelm = doc.getElementsByClass( "Cell-block--1oNaD" );
            String blackCellID;

            PrintWriter pw = new PrintWriter( "boxes" + day + month + year + " .txt" );
            for ( int i = 0; i < newelm.size(); i++ ) {
                blackCellID = newelm.get( i ).attr( "id" );
                blackCellID = blackCellID.replaceAll( "\\D+", "" ); //get only integers
                boxes.add( Integer.parseInt( blackCellID ) );
                pw.println( blackCellID );
            }
            pw.close();
        } catch ( IOException | NumberFormatException e ) {
            System.out.println( e );
        }
        return boxes;

    }

    /**
     * Retrieve clue labels if there is one.
     *
     * @return
     */
    public static ArrayList getClueLabels() {
        String url = "https://www.nytimes.com/crosswords/game/mini";
        ArrayList<Integer> clueLabels = new ArrayList();
        int clueAmount = 0;
        try {
            Document doc = Jsoup.parse( new URL( url ), 10000 );
            Elements newelm = doc.getElementsByClass( "Clue-label--2IdMY" );

            PrintWriter pw = new PrintWriter( "clueLabels" + day + month + year + " .txt" );
            for ( int i = 0; i < newelm.size(); i++ ) {
                clueLabels.add( Integer.parseInt( newelm.get( i ).text() ) );
                pw.println( newelm.get( i ).text() );
            }
            pw.close();
        } catch ( IOException | NumberFormatException e ) {
            System.out.println( e );
        }
        return clueLabels;
    }

    /**
     * Retrieves all clue labels on cells. Inputs 0 if there isn't any clue
     * label
     *
     * @return
     */
    public static ArrayList<Integer> getClueLabelsOnCells() {
        String url = "https://www.nytimes.com/crosswords/game/mini";
        ArrayList<Integer> clueLabels = new ArrayList();
        int clueAmount = 0;
        String clueNums;
        try {
            Document doc = Jsoup.parse( new URL( url ), 10000 );
            Elements newelm = doc.select( "g" );

            PrintWriter pw = new PrintWriter( "getClueLabelsOnCells" + day + month + year + " .txt" );
            for ( int i = 4; i < newelm.size() - 1; i++ ) {
                clueNums = (newelm.get( i ).text());

                if ( clueNums.equals( "" ) ) {
                    clueLabels.add( 0 );
                    pw.println( 0 );
//                    System.out.println( 0 );
                } else {
                    clueLabels.add( (Integer.parseInt( clueNums )) );
                    pw.println( (Integer.parseInt( clueNums )) );
                }

            }
            pw.close();
        } catch ( IOException | NumberFormatException e ) {
            System.out.println( e );
        }
        return clueLabels;
    }

    public static int getCellAmount() {
        String url = "https://www.nytimes.com/crosswords/game/mini";
        int cellAmount = 0;

        try {
            Document doc = Jsoup.parse( new URL( url ), 10000 );
            Elements newelm = doc.select( "rect" );
            cellAmount = newelm.size() - 1;

        } catch ( IOException e ) {
            System.out.println( e );
        }

        return cellAmount;
    }

    /**
     * Retrieves answers using Selenium
     * @return
     */
    public static ArrayList getAnswers() {
        String url = "https://www.nytimes.com/crosswords/game/mini";
        ArrayList<String> answers = new ArrayList();
        String allAnswers = "";
        SeleniumConnection s = new SeleniumConnection();
        try {
            Document doc = Jsoup.parse( s.pageSource() );
            Elements newelm = doc.select( "#xwd-board > g:nth-child(5)" );
            PrintWriter pw = new PrintWriter( "answers" + day + month + year + ".txt" );
            for ( int i = 0; i < newelm.size(); i++ ) {
                allAnswers = (newelm.get( i ).text()).replaceAll( "[^A-Za-z]+", "" );
//                System.out.println( allAnswers );
                pw.println( allAnswers );
            }
            pw.close();

            int length = allAnswers.length();
            char[] anw = allAnswers.toCharArray();

            //change due to saturday's puzzle
            for ( int i = 0; i < length; i += 2 ) {
                char c = anw[i];
                answers.add( c + "" );
            }

//            for ( char c : allAnswers.toCharArray() ) {
////                System.out.println( c );
//                answers.add( c + "" );
//            }
        } catch ( FileNotFoundException e ) {
            System.out.println( e );
        }

        s.closeCon();

        return answers;
    }

    public static void main( String[] args ) {

        getAnswers();
        getClues();
        getBoxes();
        getClueLabels();
        getCellAmount();
        getClueLabelsOnCells();

    }
}
