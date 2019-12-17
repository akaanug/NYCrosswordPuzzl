
import GUI.consoleGUI;
import com.mycompany.nycrosswordpuzzle.SetupPuzzle;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
