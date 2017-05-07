/**
 * Initializes the entire game*
 *
 * @See GameLogic
 */

public class TheGame {

    public static void main (String [] args)
    {
        System.out.println("Welcome to 2048!");

        GameLogic theGame = new GameLogic();

        HumanCommands Commands = new HumanCommands(theGame);
    }
}
