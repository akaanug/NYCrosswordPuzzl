package com.mycompany.nycrosswordpuzzle;

import java.io.BufferedReader;
import java.io.FileReader;
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
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return clues;
    }

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
        } catch ( Exception e ) {
            e.printStackTrace();

        }
        return boxes;

    }

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
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return clueLabels;
    }

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
                    System.out.println( 0 );
                } else {
                    clueLabels.add( (Integer.parseInt( clueNums )) );
                    pw.println( (Integer.parseInt( clueNums )) );
//                    System.out.println( (Integer.parseInt( clueNums )) );
                }

            }
            pw.close();
        } catch ( Exception e ) {
            e.printStackTrace();
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

        } catch ( Exception e ) {
            e.printStackTrace();
        }

        return cellAmount;
    }

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
                System.out.println( allAnswers );
                pw.println( newelm.get( i ).text() );
            }
            pw.close();
        } catch ( Exception e ) {
            e.printStackTrace();
        }

        for ( char c : allAnswers.toCharArray() ) {
            System.out.println( c );
            answers.add( c + "" );
        }

        s.closeCon();

        return answers;
    }

    public static ArrayList tempAnsw() {
        ArrayList<String> ans = new ArrayList();
        BufferedReader reader;
        try {
            reader = new BufferedReader( new FileReader(
                    "/Users/kaan/NetBeansProjects/answ/answers6102019.txt" ) );
            String line = reader.readLine();
            while ( line != null ) {
                System.out.println( line );
                ans.add( line );
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        
        return ans;
    }

    public static void main( String[] args ) {
        getClues();
        getBoxes();
        getClueLabels();
        getCellAmount();
        getClueLabelsOnCells();

    }
}
