/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.nycrosswordpuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author kaan
 */
public class Constants {

    private final int cellAmount;
    private final ArrayList<Boolean> isBlock;
    private final HashMap<Integer, String> acrossClues;
    private final HashMap<Integer, String> downClues;

    public Constants() {
        this.cellAmount = getCellAmount();
        this.acrossClues = getAcrossClues();
        this.downClues = getDownClues();
        this.isBlock = isTheBlock();
    }

    public final int getCellAmount() {
        return Parser.getCellAmount();
    }
    
    public final ArrayList<Boolean> getIsBlock() {
        return isBlock;
    }

    public final ArrayList<Boolean> isTheBlock() {
        ArrayList isBox;
        isBox = new ArrayList();
        for ( int i = 0; i < cellAmount; i++ ) {
            if ( Parser.getBoxes().contains( i ) ) {
                System.out.println( "true" );
                isBox.add( true );
            } else {
                System.out.println( "false" );
                isBox.add( false );
            }
        }
        return isBox;
    }
    
    public final HashMap getAcrossClues() {
        HashMap<Integer, String> clues = new HashMap<>();
        int clueSize = Parser.getClues().size() / 2;
        for ( int i = 0; i < clueSize; i++ ) {
            int clueLabel = (int) Parser.getClueLabels().get( i );
            String clue = (String) Parser.getClues().get( i );
            clues.put( clueLabel, clue );
        }
        System.out.println( "Across Clues:\n" + Arrays.asList( clues ) );
        return clues;
    }

    public final HashMap getDownClues() {
        HashMap<Integer, String> clues = new HashMap<>();
        int clueSize = Parser.getClues().size() / 2;
        for ( int i = clueSize; i < clueSize * 2; i++ ) {
            int clueLabel = (int) Parser.getClueLabels().get( i );
            String clue = (String) Parser.getClues().get( i );
            clues.put( clueLabel, clue );
        }
        System.out.println( "Down Clues:\n" + Arrays.asList( clues ) );
        return clues;
    }

    public int getCellCount() {
        System.out.println( cellAmount );
        return cellAmount;
    }

}
