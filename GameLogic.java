import java.io.*;
import java.util.*;

public class GameLogic {

    public String grid[][] = new String[4][4];
    Random random = new Random();

    int latestY = 3;
    int latestX = 3;


    /**Constructor*/
    public GameLogic()
    {
        setUpMap();
        printMap();
        start();
    }


    private void setUpMap()
    {
        for (int y = 0 ; y <= 3 ; y ++)
        {
            for (int x = 0 ; x <= 3 ; x++)
            {
                grid[y][x] = ".";
            }
        }
    }

    protected void printMap()
    {
        for (int y = 0 ; y <= 3 ; y ++)
        {
            for (int x = 0 ; x <= 3 ; x++)
            {
                if (latestY == y && latestX == x)
                {
                    System.out.print("\u001B[34m" + grid[y][x] + "\u001B[0m");
                }
                else
                {
                    System.out.print(grid[y][x]);
                }
            }
            System.out.println();
        }
        System.out.println();

        try {
            grid[latestY][latestX] = Character.toString(grid[latestY][latestX].charAt(11));
        }
        catch(StringIndexOutOfBoundsException e){
        }
    }



    private void start()
    {
        System.out.println ("Press enter start the game.");

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            String humanInput = input.readLine();

            if( !humanInput.equals(null))
            {
                placeNewNumber();
                printMap();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void placeNewNumber()
    {
        int j;
        int i;

        do
        {
            j = random.nextInt(4);
            i = random.nextInt(4);
        }
        while (getTile(j,i)!= '.');



        //TODO future implementation: turn 10+ allow to add 4 into
        /*
        if (greaterThanRoundTenBa) {
            String value;
            int determinator = random.nextInt(2);
            if (determinator == 1) {
                value = "2";
                setTile(j,i,value);
            }
            if (determinator == 2) {
                value = "4";
                setTile(j,i,value);
            }
        }
        else
        {
            setTile(j, i, "2");
        }
        */

        //For the meantime...
        setTile(j, i, "2");


    }


    protected char getTile(int y, int x)
    {
        String characterAsString = grid[y][x];
        return characterAsString.charAt(0);
    }


    private void setTile(int y, int x, String value)
    {
        grid[y][x] = value;

        latestY = y;
        latestX = x;
    }



}
