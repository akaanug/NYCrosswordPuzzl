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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
     * @return clue numbers of all cells.
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

    /**
     * @return an ArrayList of block status of the cells.
     */
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

    /**
     * Get across clues from Parser
     *
     * @return
     */
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

    /**
     * Get down clues from Parser
     *
     * @return
     */
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
     * Searches wordNet.
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

        System.out.println( "*WN Seaching Wordnet for " + wordToSearch );

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
            return null;
        }

        IWordID wordID = idxWord.getWordIDs().get( 0 );
        IWord word = dict.getWord( wordID );
        System.out.println( "*WN Id = " + wordID );
        System.out.println( "*WN Lemma = " + word.getLemma() );
        System.out.println( "*WN Gloss = " + word.getSynset().getGloss() );

        return word.getSynset().getGloss();
    }

    public final String searchUrbanDictionary( String wordToSearch ) throws IOException {

        System.out.println( "*UD Seaching Urban Dictionary for " + wordToSearch );

        try {
            Document doc = Jsoup.connect( "https://www.urbandictionary.com/define.php?term=" + wordToSearch ).get();
            Elements newelm = doc.getElementsByClass( "meaning" );
            Iterator<Element> iter = newelm.iterator();

            if ( doc != null || newelm != null ) {
                System.out.println( "*UD: " + newelm.first().text() );
                int index = newelm.first().text().lastIndexOf( ". " );
                int firstpt = newelm.first().text().indexOf( ". " );

                if ( !(newelm.first().text().substring( 0, index ).contains( wordToSearch ))
                        && newelm.first().text().substring( 0, firstpt ).length() > wordToSearch.length() ) {
                    System.out.println( "*UD taking " + (newelm.first().text().substring( 0, index )) );
                    return (newelm.first().text().substring( 0, index ));
                } else if ( !(newelm.first().text().substring( 0, index ).contains( wordToSearch )) ) {
                    System.out.println( "*UD taking " + newelm.first().text().substring( index + 2, newelm.first().text().length() ) );
                    return newelm.first().text().substring( index + 2, newelm.first().text().length() );
                }

                if ( newelm.first().text().contains( wordToSearch ) ) {
                    if ( iter.hasNext() ) {
                        System.out.println( "*UD taking " + newelm.first().text().substring( index, newelm.first().text().length() ) );
                        return newelm.first().text().substring( index, newelm.first().text().length() );
                    }
                }
                return (newelm.first().text().substring( 0, index ));

//                return newelm.first().text();
            } else {
                System.out.println( "*UD: Word" + wordToSearch + " not found on WordNet." );
                return null;
            }
        } catch ( Exception e ) {
            System.out.println( "*UD Word not found on Urban Dictionary" );
            return null;
        }

    }

    /**
     * Search the dictionary database and pick the shortest clue. If no result
     * found in dictionary, call searchWordNet. If searchWordNet also returns
     * null, search urban dictionary.
     *
     * @param answersToSearch
     * @return
     * @throws SQLException
     */
    public final ArrayList<String> searchDictionary( ArrayList<String> answersToSearch ) throws SQLException {
        ArrayList<String> dictAnswers = new ArrayList();
        for ( int i = 0; i < answersToSearch.size(); i++ ) {

            System.out.println( "*D Searching Dictionary For: "
                    + answersToSearch.get( i ) );
            String currentWord = answersToSearch.get( i );

            //to search the results and see if there is more than one result
            String searchQuery = "SELECT * FROM `entries` WHERE `word` LIKE '"
                    + currentWord + "' ORDER BY `word` DESC";
            ResultSet searchSet = st.executeQuery( searchQuery );
            int numOfResults = 0;

            int minClueLength;
            int currClueLength;
            String shortestClue = "";

            boolean wrongClue = false;
            String rc = null;

            if ( searchSet.next() ) { //if searchSet is not empty
                currClueLength = searchSet.getString( "definition" ).length();
                shortestClue = searchSet.getString( "definition" );
                minClueLength = currClueLength;
                System.out.println( "*D: Found in dictionary: "
                        + searchSet.getString( "definition" )
                        + "-LENGTH: " + currClueLength + "-" );
                numOfResults++;

                while ( searchSet.next() ) {
                    currClueLength = searchSet.getString( "definition" ).length();
                    if ( currClueLength < minClueLength ) {
                        minClueLength = currClueLength;
                        shortestClue = searchSet.getString( "definition" );
                    }

                    System.out.println( "*D: Found in dictionary: "
                            + searchSet.getString( "definition" ) + "-LENGTH: "
                            + currClueLength + "-" );
                    numOfResults++;
                }

                System.out.println( "*D: Shortest Clue found is: "
                        + shortestClue + "-LENGTH: " + minClueLength + "-" );

                if ( shortestClue != null && (shortestClue.contains( "Alt. of" )
                        || shortestClue.contains( ("of " + currentWord).substring( 0, currentWord.length() - 1 ) )) ) {
                    System.out.println( "*D Incorrect Clue" );
                    wrongClue = true;
                    try {
                        shortestClue = replaceWordInsideClue( searchWordNet( currentWord ), currentWord );
                        if ( shortestClue == null ) {
                            shortestClue = searchUrbanDictionary( currentWord );
                            int l = shortestClue.lastIndexOf( ". " );
//                            shortestClue = shortestClue.substring( l, shortestClue.length());
                        }
                    } catch ( IOException IOException ) {
                        System.out.println( IOException );
                    }
                }
            }

            //set the cursor to beginning
            searchSet.first();

            //if no result found in dictionary, search wordNet
            if ( numOfResults == 0 ) {
                System.out.println( "*D Could not found " + currentWord
                        + " in dictionary." );
                try {
                    String clueFromWordNet;
                    clueFromWordNet = searchWordNet( currentWord );
                    if ( clueFromWordNet != null ) {
                        dictAnswers.add( replaceWordInsideClue( clueFromWordNet, currentWord ) );
                    } else {
                        String clueFromUrban;
                        clueFromUrban = searchUrbanDictionary( currentWord );
                        if ( clueFromUrban != null ) {
                            dictAnswers.add( replaceWordInsideClue( clueFromUrban, currentWord ) );
                        } else {
                            dictAnswers.add( "NULL" );
                        }
                    }
                } catch ( IOException ex ) {
                    Logger.getLogger( Constants.class.getName() ).log( Level.SEVERE, null, ex );
                }
            } else {
                dictAnswers.add( replaceWordInsideClue( shortestClue, currentWord ) );
            }
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

    /**
     * Label the across clues into the HashMap with corresponding numbers.
     *
     * @return
     * @throws SQLException
     */
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

    /**
     * Label the down clues into the HashMap with corresponding numbers.
     *
     * @return
     * @throws SQLException
     */
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
