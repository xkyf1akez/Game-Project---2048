import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class HumanCommands {

    private boolean gameOver = false;

    public int roundCount = 0;

    public HumanCommands(GameLogic theGameLogic)
    {
       System.out.println("Commands are: 'up', 'down', left', and 'right'.");
        System.out.println();

        while(!gameOver) {
           doNextCommand(theGameLogic);

           testLose(theGameLogic);
           theGameLogic.placeNewNumber();
           theGameLogic.printMap();
           roundCount++;
       }

       //LOSING STUFF HERE
        System.out.println("No more possible moves. GAME OVER");
    }



    //Losing condition ==> when if you do all moves and it is not possible to move. then game Over
    private void testLose(GameLogic theGameLogic)
    {
        //TEST 1 - If no more spaces
        for (int y = 0 ; y <= 3 ; y ++)
        {
            for (int x = 0 ; x <= 3 ; x++)
            {
                if(theGameLogic.grid[y][x].equals("."))
                {
                    return;
                }
            }
        }

        //TEST 2 - if all future moves fail. no possible future moves
        //TODO code
            gameOver = true;
    }


    /** TAKES INPUT FROM HUMAN PLAYER THEN PROCESSES IT IN processNextCommand*/
    private void doNextCommand(GameLogic theGameLogic)
    {
        System.out.println ("What is your next command?");

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        try
        {
            String humanInput = input.readLine();
            processNextCommand(humanInput, theGameLogic);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /** If the human input is valid, the game will call the appropriate functions to elicit its intended action*/
    private void processNextCommand(String command, GameLogic theGameLogic)
    {
        switch (command.toUpperCase())
        {
            case"":
                break;

            case"UP":
                tilt("UP", theGameLogic);

                //theGameLogic.placeNewNumber();                    //TODO put a sleep 1 second then place new number [cuz it's hard to see what is the new number lol
                break;

            case"DOWN":
                tilt("DOWN", theGameLogic);
                //theGameLogic.placeNewNumber();
                break;

            case"LEFT":
                tilt("LEFT", theGameLogic);
                //theGameLogic.placeNewNumber();
                break;

            case"RIGHT":
                tilt("RIGHT", theGameLogic);
                //theGameLogic.placeNewNumber();
                break;

            case"RESTART":
                //TODO implement in the future
                break;

            default:
                System.out.println("Invalid Move");
        }
    }


    private void tilt (String direction, GameLogic theGameLogic)
    {

        // COUNTS THE NUMBER OF TILES, STORES THEIR COORDINATES
        //NOTE, DOES NOT SAVE THE ACTUAL TILE SYMBOL/NUMBER
        HashMap<Integer, String> hashMap = new HashMap<>();


        int index = 1;

        /**The following is necessary, to appease my unease about the "0 2 2 2" problem**/

        if(direction.toUpperCase().equals("UP"))
        {
            for (int y = 0 ; y <= 3 ; y ++)
            {
                for (int x = 0 ; x <= 3 ; x++)
                {
                    if(theGameLogic.getTile(y,x)!='.')
                    {
                        String numbersTogether = Integer.toString(y) + Integer.toString(x);
                        hashMap.put( index , numbersTogether);
                        index ++;
                    }
                }
            }
        }

        else if(direction.toUpperCase().equals("DOWN"))
        {
            for (int y = 3 ; y >= 0 ; y --)
            {
                for (int x = 0 ; x <= 3 ; x++)
                {
                    if(theGameLogic.getTile(y,x)!='.')
                    {
                        String numbersTogether = Integer.toString(y) + Integer.toString(x);
                        hashMap.put( index , numbersTogether);
                        index ++;
                    }
                }
            }
        }

        else if(direction.toUpperCase().equals("LEFT"))
        {
            for (int x = 0 ; x <= 3 ; x++)
            {
                for (int y = 0 ; y <= 3 ; y++)
                {
                    if(theGameLogic.getTile(y,x)!='.')
                    {
                        String numbersTogether = Integer.toString(y) + Integer.toString(x);
                        hashMap.put( index , numbersTogether);
                        index ++;
                    }
                }
            }
        }

        else if(direction.toUpperCase().equals("RIGHT"))
        {
            for (int x = 3 ; x >= 0 ; x--)
            {
                for (int y = 0 ; y <= 3 ; y++)
                {
                    if(theGameLogic.getTile(y,x)!='.')
                    {
                        String numbersTogether = Integer.toString(y) + Integer.toString(x);
                        hashMap.put( index , numbersTogether);
                        index ++;
                    }
                }
            }
        }




        //REMINDER: coordinates given are as (y,x) and numbers begin top left, starting with 0
        for (int counter = 1 ; counter <= hashMap.size() ; counter++)
        {
            String numbers2gether = hashMap.get(counter);
            char yChar = numbers2gether.charAt(0);
            char xChar = numbers2gether.charAt(1);

            int yCoordinate = Character.getNumericValue(yChar);
            int xCoordinate = Character.getNumericValue(xChar);

            magicMove(theGameLogic, yCoordinate, xCoordinate, direction);

        }

    }

    private void magicMove(GameLogic theGameLogic, int yCoordinate, int xCoordinate, String direction)
    {
        //WHERE THE MAGIC HAPPENS
        if(isThereATileInTheNextDirection(theGameLogic,yCoordinate,xCoordinate,direction))
        {
            if (direction.toUpperCase().equals("UP"))
            {
                theGameLogic.grid[yCoordinate-1][xCoordinate] = theGameLogic.grid[yCoordinate][xCoordinate];
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate-1, xCoordinate, direction);
            }
            else if (direction.toUpperCase().equals("DOWN"))
            {
                theGameLogic.grid[yCoordinate+1][xCoordinate] = theGameLogic.grid[yCoordinate][xCoordinate];
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate+1, xCoordinate, direction);
            }
            else if (direction.toUpperCase().equals("LEFT"))
            {
                theGameLogic.grid[yCoordinate][xCoordinate-1] = theGameLogic.grid[yCoordinate][xCoordinate];
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate, xCoordinate-1, direction);
            }
            else if (direction.toUpperCase().equals("RIGHT"))
            {
                theGameLogic.grid[yCoordinate][xCoordinate+1] = theGameLogic.grid[yCoordinate][xCoordinate];
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate, xCoordinate+1, direction);
            }
        }
    }


    //WHEN NUMBERS ARE THE SAME
    private void magicOverride(GameLogic theGameLogic, int yCoordinate, int xCoordinate, String direction)
    {
        int newValue = Integer.parseInt(theGameLogic.grid[yCoordinate][xCoordinate]);
            newValue = newValue * 2;

            if (direction.toUpperCase().equals("UP"))
            {
                theGameLogic.grid[yCoordinate-1][xCoordinate] = Integer.toString(newValue);
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate-1, xCoordinate, direction);
            }
            else if (direction.toUpperCase().equals("DOWN"))
            {
                theGameLogic.grid[yCoordinate+1][xCoordinate] = Integer.toString(newValue);
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate+1, xCoordinate, direction);
            }
            else if (direction.toUpperCase().equals("LEFT"))
            {
                theGameLogic.grid[yCoordinate][xCoordinate-1] = Integer.toString(newValue);
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate, xCoordinate-1, direction);
            }
            else if (direction.toUpperCase().equals("RIGHT"))
            {
                theGameLogic.grid[yCoordinate][xCoordinate+1] = Integer.toString(newValue);
                theGameLogic.grid[yCoordinate][xCoordinate] = ".";
                magicMove(theGameLogic, yCoordinate, xCoordinate+1, direction);
            }

    }



    /** NECESSARY STEP BEFORE MOVING - tests if there is space*/
    protected boolean isThereATileInTheNextDirection(GameLogic theGameLogic, int y, int x, String direction) {
        try {
            if (direction.toUpperCase().equals("UP")) {
                if (theGameLogic.grid[y - 1][x].equals(".")) {
                    return true;
                }
                else if (theGameLogic.grid[y-1][x].equals(theGameLogic.grid[y][x]))
                {
                    magicOverride(theGameLogic,y,x,direction);
                }
            } else if (direction.toUpperCase().equals("DOWN")) {
                if (theGameLogic.grid[y + 1][x].equals(".")) {
                    return true;
                }
                else if (theGameLogic.grid[y+1][x].equals(theGameLogic.grid[y][x]))
                {
                    magicOverride(theGameLogic,y,x,direction);
                }
            } else if (direction.toUpperCase().equals("LEFT")) {
                if (theGameLogic.grid[y][x - 1].equals(".")) {
                    return true;
                }
                else if (theGameLogic.grid[y][x-1].equals(theGameLogic.grid[y][x]))
                {
                    magicOverride(theGameLogic,y,x,direction);
                }
            } else if (direction.toUpperCase().equals("RIGHT")) {
                if (theGameLogic.grid[y][x + 1].equals(".")) {
                    return true;
                }
                else if (theGameLogic.grid[y][x+1].equals(theGameLogic.grid[y][x]))
                {
                    magicOverride(theGameLogic,y,x,direction);
                }
            } else {
                //This should never happen
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            //do not use "e.printStackTrace();"... it will print it
        }
        return false;

    }

    //TODO future implemntation
    public boolean RoundCountOverTen()
    {
        if (roundCount > 10)
        {
            return true;
        }
        else return false;
    }



}
