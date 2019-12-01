/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nycrosswordpuzzle;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.morph.WordnetStemmer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kaan
 */
public class Constants {

    private final int cellAmount;
    private final ArrayList<Boolean> isBlock;
    private final ArrayList<String> answers;
    private final ArrayList<String> answersAsWords;
    private final ArrayList<String> downAnswersAsWordList;
    private final HashMap<Integer, String> downAnswersAsWords;
    private final HashMap<Integer, String> acrossClues;
    private final HashMap<Integer, String> generatedAcrossClues;
    private final HashMap<Integer, String> generatedDownClues;
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

        this.downAnswersAsWords = downAnswersAsWords();
        this.downAnswersAsWordList = downAnswersAsWordList();

        con = DriverManager.getConnection( DB_URL, DB_USER, DB_PASSWD );
        st = con.createStatement();
        this.generatedAcrossClues = generateNewAcrossClues();
        this.generatedDownClues = generateNewDownClues();

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

    public final HashMap<Integer, String> getGeneratedDown() {
        return generatedDownClues;
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

    /**
     * Get across answers as words to search it
     *
     * @return
     */
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

    /**
     * Write down answers into the HashMap with the according labels
     *
     * @return
     */
    public final HashMap<Integer, String> downAnswersAsWords() {
        int count = 0;
        int label = 0;

        ArrayList<String> answersWithSpace = new ArrayList();
        for ( int i = 0; i < cellAmount; i++ ) {
            if ( isBlock.get( i ) ) {
                answersWithSpace.add( "" );
            } else {
                answersWithSpace.add( answers.get( count++ ) );
            }
        }

        HashMap<Integer, String> wordAnsw = new HashMap();
        String word = "";
        int length = (int) Math.sqrt( cellAmount );

        for ( int column = 0; column < length; column++ ) {
            for ( int i = 0; i < cellAmount; i += length ) {
                if ( !isBlock.get( column + i ) ) {
                    if ( label == 0 ) {
                        if ( clueLabelsOnCells.get( column + i ) != 0 ) {
                            label = clueLabelsOnCells.get( column + i );
                        }
                    }
                    word += answersWithSpace.get( column + i );
                }

                if ( i >= length * (length - 1) ) {
                    wordAnsw.put( label, word );
                    word = "";
                    label = 0;
                }
            }
        }

//        wordAnsw.entrySet().forEach( entry -> {
//            System.out.println( entry.getKey() + " " + entry.getValue() );
//        } );
        return wordAnsw;
    }

    /**
     * Get down answers as an ArrayList in order to use it to search db
     *
     * @return
     */
    public final ArrayList<String> downAnswersAsWordList() {
        ArrayList<String> list = new ArrayList<>( downAnswersAsWords.values() );
        return list;
    }

    /**
     * Replace the words same as the answer inside generated clues with "____"
     *
     * @param clue
     * @param answer
     * @return
     */
    public String replaceWordInsideClue( String clue, String answer ) {
        String modifiedClue;
        if ( (clue != null) && (clue.toLowerCase().contains( answer.toLowerCase() )) ) {
            clue = clue.replace( answer.toLowerCase(), "_______" );
            modifiedClue = clue;
            return modifiedClue;
        } else {
            return clue;
        }
    }

    /**
     * Searches wordNet
     *
     * @param wordToSearch
     * @return
     * @throws IOException
     */
    public final String searchWordNet( String wordToSearch ) throws IOException {
        URL url = null;
        try {
            url = new URL( "file", null, "/Users/kaan/Downloads/WordNet-3.0/dict" );
        } catch ( MalformedURLException e ) {
            System.out.println( e );
        }
        if ( url == null ) {
            return null;
        }

        // construct the dictionary object and open it
        IDictionary dict = new Dictionary( url );
        dict.open();

        System.out.println( "Seaching Wordnet for " + wordToSearch );

        // look up first sense of the word
        String stem;
        List stems = new ArrayList();

        WordnetStemmer stemmer = new WordnetStemmer( dict );
        stems = stemmer.findStems( wordToSearch, POS.NOUN );

        if ( stems.size() > 0 ) {
            stem = (String) (stems.get( stems.size() - 1 ));
            wordToSearch = stem;
        }

        IIndexWord idxWord = dict.getIndexWord( wordToSearch, POS.NOUN );
        if ( idxWord == null ) {
            System.out.println( "*WN: Word " + wordToSearch + " not found on WordNet." );
            return "NULL";
        }

        IWordID wordID = idxWord.getWordIDs().get( 0 );
        IWord word = dict.getWord( wordID );
        System.out.println( "Id = " + wordID );
        System.out.println( "Lemma = " + word.getLemma() );
        System.out.println( "Gloss = " + word.getSynset().getGloss() );

        return word.getSynset().getGloss();
    }

    /**
     * Search the dictionary database Pick second meaning if there is more than
     * one. Else pick the first one.(subject to change) If no result found in
     * dictionary, call searchWordNet.
     *
     * @param answersToSearch
     * @return
     * @throws SQLException
     */
    public final ArrayList<String> searchDictionary( ArrayList<String> answersToSearch ) throws SQLException {
        ArrayList<String> dictAnswers = new ArrayList();
        for ( int i = 0; i < answersToSearch.size(); i++ ) {

            System.out.println( "Searching Dictionary For: "
                    + answersToSearch.get( i ) );
            String currentWord = answersToSearch.get( i );

            //to search the results and see if there is more than one result
            String searchQuery = "SELECT * FROM `entries` WHERE `word` LIKE '"
                    + currentWord + "' ORDER BY `word` DESC";
            ResultSet searchSet = st.executeQuery( searchQuery );
            int numOfResults = 0;

            while ( searchSet.next() ) {
                System.out.println( "*D: Found in dictionary: " + searchSet.getString( "definition" ) );
                numOfResults++;
            }

            //set the cursor to beginning
            searchSet.first();
            
            //if no result found in dictionary, search wordNet
            if ( numOfResults == 0 ) {
                System.out.println( "Could not found " + currentWord
                        + " in dictionary." );
                try {
                    String clueFromWordNet;
                    clueFromWordNet = searchWordNet( currentWord );
                    if ( clueFromWordNet != null ) {
                        dictAnswers.add( replaceWordInsideClue( clueFromWordNet, currentWord ) );
                    } else {
                        dictAnswers.add( "NULL" );
                    }
                } catch ( IOException ex ) {
                    Logger.getLogger( Constants.class.getName() ).log( Level.SEVERE, null, ex );
                }
            } else if ( numOfResults == 1 ) {
                dictAnswers.add( replaceWordInsideClue( searchSet.getString( "definition" ), currentWord ) );
            } else {
                searchSet.next();
                dictAnswers.add( replaceWordInsideClue( searchSet.getString( "definition" ), currentWord ) );
            }

//            int limit = 2;
//            if ( numOfResults == 1 ) {
//                limit = 1;
//            }
//            String query = "SELECT * FROM `entries` WHERE `word` LIKE '"
//                    + currentWord + "' ORDER BY `word` DESC LIMIT " + limit;
//            ResultSet rs = st.executeQuery( query );
//
//            if ( !rs.isBeforeFirst() ) {
//                
//            }
//
//            while ( rs.next() ) {
//                System.out.println( "*D: Found in dictionary: " + rs.getString( "definition" ) );
//            }
//
//            if ( numOfResults != 0 ) {
//                String generatedClue = replaceWordInsideClue( rs.getString( "definition" ), currentWord );
//                dictAnswers.add( generatedClue );
//            }
//
//            
        }
        return dictAnswers;
    }

    /**
     * Get key of the corresponding value
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param value
     * @return
     */
    public static <K, V> K getKey( Map<K, V> map, V value ) {
        return map.entrySet()
                .stream()
                .filter( entry -> value.equals( entry.getValue() ) )
                .map( Map.Entry::getKey )
                .findFirst().get();
    }

    public final HashMap<Integer, String> generateNewAcrossClues() throws SQLException {

        HashMap<Integer, String> newAcrossClues = new HashMap();
        ArrayList<String> dictAcrossAnswers = searchDictionary( answersAsWords );

        for ( int i = 0; i < answersAsWords.size(); i++ ) {
            String currentClue = dictAcrossAnswers.get( i );
            int clueLabel = (int) Parser.getClueLabels().get( i );
            newAcrossClues.put( clueLabel, currentClue );
        }

        return newAcrossClues;
    }

    public final HashMap<Integer, String> generateNewDownClues() throws SQLException {

        HashMap<Integer, String> newDownClues = new HashMap();
        ArrayList<String> dictDownAnswers = searchDictionary( downAnswersAsWordList );

        for ( int i = 0; i < downAnswersAsWordList.size(); i++ ) {
            String currentWord = downAnswersAsWordList.get( i );
            String currentClue = dictDownAnswers.get( i );
            int clueLabel;
            clueLabel = getKey( downAnswersAsWords, currentWord );
            newDownClues.put( clueLabel, currentClue );
        }

        return newDownClues;
    }
}
