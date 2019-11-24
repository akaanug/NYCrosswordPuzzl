/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nycrosswordpuzzle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kaan
 */
public class Constants {

    private final int cellAmount;
    private final ArrayList<Boolean> isBlock;
    private final ArrayList<String> answers;
    private final ArrayList<String> answersAsWords;
    private final HashMap<Integer, String> acrossClues;
    private final HashMap<Integer, String> generatedAcrossClues;
    private final HashMap<Integer, String> downClues;
    private final HashMap<Integer, Integer> clueLabelsOnCells;

    static final String DB_URL
            = "jdbc:mysql://localhost:3306/entries";
    static final String DB_USER = "kaan";
    static final String DB_PASSWD = "0000";

    Connection con = null;
    Statement st = null;

    public Constants() throws SQLException {

        this.cellAmount = getCellAmount();
        this.acrossClues = acrossClues();
        this.downClues = downClues();
        this.isBlock = isTheBlock();
        this.clueLabelsOnCells = clueLabelsOnCells();
        this.answers = retAnswers();
        this.answersAsWords = wordAnswers();

        con = DriverManager.getConnection( DB_URL, DB_USER, DB_PASSWD );
        st = con.createStatement();
        this.generatedAcrossClues = generateNewAcrossClues();

    }

    public final ArrayList<String> getAnswers() {
        return answers;
    }

    public final HashMap<Integer, Integer> getClueLabelsOnCells() {
        return clueLabelsOnCells;
    }

    public final HashMap<Integer, String> getAcross() {
        return acrossClues;
    }

    public final HashMap<Integer, String> getGeneratedAcross() {
        return generatedAcrossClues;
    }

    public final HashMap<Integer, String> getDown() {
        return downClues;
    }

    public final int getCellAmount() {
        return Parser.getCellAmount();
    }

    public final ArrayList<Boolean> getIsBlock() {
        return isBlock;
    }

    /**
     * (cell number, clue Number) adds 0 as clue number if the is none
     *
     * @return
     */
    public final HashMap clueLabelsOnCells() {
        HashMap<Integer, Integer> clueLabels = new HashMap<>();
        ArrayList<Integer> clueNums = Parser.getClueLabelsOnCells();
        for ( int i = 0; i < cellAmount; i++ ) {
//            System.out.println( "Cell Num:" + i + " CLUE: " + clueNums.get( i ) );
            clueLabels.put( i, clueNums.get( i ) );
        }

        return clueLabels;
    }

    public final ArrayList<Boolean> isTheBlock() {
        ArrayList isBox;
        isBox = new ArrayList();
        for ( int i = 0; i < cellAmount; i++ ) {
            if ( Parser.getBoxes().contains( i ) ) {
//                System.out.println( "true" );
                isBox.add( true );
            } else {
//                System.out.println( "false" );
                isBox.add( false );
            }
        }
        return isBox;
    }

    public final HashMap acrossClues() {
        HashMap<Integer, String> clues = new HashMap<>();
        int clueSize = Parser.getClues().size() / 2;
        for ( int i = 0; i < clueSize; i++ ) {
            int clueLabel = (int) Parser.getClueLabels().get( i );
            String clue = (String) Parser.getClues().get( i );
            clues.put( clueLabel, clue );
        }
        return clues;
    }

    public final HashMap downClues() {
        HashMap<Integer, String> clues = new HashMap<>();
        int clueSize = Parser.getClues().size() / 2;
        for ( int i = clueSize; i < clueSize * 2; i++ ) {
            int clueLabel = (int) Parser.getClueLabels().get( i );
            String clue = (String) Parser.getClues().get( i );
            clues.put( clueLabel, clue );
        }
        return clues;
    }

    public final ArrayList<String> retAnswers() {
        return Parser.getAnswers();
    }

    public int getCellCount() {
//        System.out.println( cellAmount );
        return cellAmount;
    }

    public final ArrayList<String> wordAnswers() {
        ArrayList<String> wordAnsw = new ArrayList();
        String word = "";
        int length = (int) Math.sqrt( cellAmount );
        int count = 0;

        Boolean b;

        for ( int i = 0; i < length; i++ ) {
            b = isBlock.get( i );
            if ( !b ) {
                word += answers.get( count++ );
            }

            if ( i == cellAmount - 1 ) {
                wordAnsw.add( word );
            }

            if ( i == length - 1 && i != cellAmount - 1 ) {
                length += (int) Math.sqrt( cellAmount );
                wordAnsw.add( word );
                word = "";
            }
        }

        return wordAnsw;
    }

    public final HashMap<Integer, String> generateNewAcrossClues() throws SQLException {

        HashMap<Integer, String> newAcrossClues = new HashMap();

        for ( int i = 0; i < answersAsWords.size(); i++ ) {

            System.out.println( "Searching Dictionary For: "
                    + answersAsWords.get( i ) );
            String currentWord = answersAsWords.get( i );
            String query = "SELECT * FROM `entries` WHERE `word` LIKE '"
                    + currentWord + "' ORDER BY `word` DESC LIMIT 2";
            ResultSet rs = st.executeQuery( query );
            rs.absolute( 1 );
            if ( !rs.isBeforeFirst() ) {
                System.out.println( "Could not found " + currentWord
                        + " in dictionary." );
            }

            while ( rs.next() ) {
                int clueLabel = (int) Parser.getClueLabels().get( i );
                System.out.println( "Found in dictionary: " + rs.getString( "definition" ) );
                newAcrossClues.put( clueLabel, rs.getString( "definition" ) );
            }

        }

        return newAcrossClues;

    }

}
