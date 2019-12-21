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


import GUI.consoleGUI;
import com.mycompany.nycrosswordpuzzle.SetupPuzzle;
import java.sql.SQLException;

/**
 *
 * @author kaan
 */
public class PuzzleMain {

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main( String[] args ) throws SQLException {
        // TODO code application logic here
        consoleGUI g = new consoleGUI();
        g.setLocation( 2, 1000 );
        g.setVisible( true );
        System.out.println( "( *D for dictionary outputs, *WN for wordnet, "
                + " *UD for Urban dictionary )" );
        SetupPuzzle set = new SetupPuzzle();
        System.out.println( "( *D for dictionary outputs, *WN for wordnet, "
                + " *UD for Urban dictionary" );

    }

}
