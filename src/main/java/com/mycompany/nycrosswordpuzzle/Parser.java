package com.mycompany.nycrosswordpuzzle;

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

    public static void main( String[] args ) {
        getClues();
        getBoxes();
        getClueLabels();
        getCellAmount();

    }
}
