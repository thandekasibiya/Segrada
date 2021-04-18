package com.example.segradaclient;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public class Game extends AppCompatActivity implements DiePoolAdapter.ActivityAListener {


    public static List<Dice> diceList = new ArrayList<>(); //90 dices
    public static List<Dice> PoolDice = new ArrayList<>(); //Pool die
    public static List<Player> playerList = new ArrayList<>(); //All players
    public List<String> colourList=new ArrayList<>();
    //This is the list that combines all the rows and columns in the list
    public static List<RowColumn> Rows = new ArrayList<>();
    public static List<RowColumn> columns = new ArrayList<>();
    //Shuffled dice
    public static List<Dice> recyclerViewList= DiePoolAdapter.recyclerViewList;
    private int[][] GridValues= new int[4][5];

    private RecyclerView recyclerView; //recyclerview
    private DiePoolAdapter adapter; //adapter


    Button NextPlayer;
    Button PreviousPlayer;
    Button Shuffle;
    Button Play;
    TextView Turns;
    TextView Rounds;
    TextView  player;
    Button Chat;
    int round = 0;
    int turn=0;
    int score=0;
    Player curPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        NextPlayer =findViewById(R.id.Next);
        PreviousPlayer =findViewById(R.id.Previous);
        Shuffle =findViewById(R.id.shuffle);
        Turns =findViewById(R.id.NumberofTurns);
        Rounds =findViewById(R.id.Round);
        player=findViewById(R.id.PlayerName);
        Play =findViewById(R.id.Play);
        Chat= findViewById(R.id.Chat);

 Chat.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         MessagingScreen(view);
     }
 });

      //Board to be placed in the activity
        Player2Board fragment = new Player2Board();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment4, fragment)
                .commit();
        newRound();
        AddPlayers();
        Add90Die();
        Turns.setText("Turn: "+String.valueOf(turn));
        Rounds.setText("Round: "+String.valueOf(round));
        Shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewShuffle();
            }
        });
        curPlayer=playerList.get(0);


        NextPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curPlayer.getPos()!=playerList.size()-1){
                    player.setText((playerList.get(curPlayer.getPos()+1)).name);
                    if (curPlayer.getPos()==0)
                    {
                        player.setBackgroundColor(Color.RED);
                        onAddB(v);
                    }
                    if (curPlayer.getPos()==1)
                    {
                        player.setBackgroundColor(Color.GREEN);
                        onReplaceWithA(v);
                    }
                    if (curPlayer.getPos()==2)
                    {
                        player.setBackgroundColor(Color.YELLOW);
                        onReplaceWithB(v);
                    }
                    curPlayer=(playerList.get(curPlayer.getPos()+1));
                }
            }
        });
        PreviousPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {{}
                if (curPlayer.pos!=0){
                    player.setText( (playerList.get(curPlayer.getPos()-1)).name);

                    if (curPlayer.getPos()==1)
                    {
                        player.setBackgroundColor(Color.BLUE);
                        onAddA(v);
                    }
                    if (curPlayer.getPos()==2)
                    {
                        player.setBackgroundColor(Color.RED);
                        onAddB(v);
                    }
                    if (curPlayer.getPos()==3)
                    {
                        player.setBackgroundColor(Color.GREEN);
                        onReplaceWithA(v);
                    }
                    curPlayer=(playerList.get(curPlayer.getPos()-1));
                   // PlayerScore.setText("Score: "+String.valueOf(score));
                }
            }
        });

    }



 public void MessagingScreen(View view){
        Intent intent= new Intent(Game.this,Messaging.class);
        startActivity(intent);
 }

    /**
     * adding players to the list
     */
    public void AddPlayers(){
        String red="red";
        String blue="blue";
        String green="greed";
        String purple="purple";
        String yellow="yellow";
        colourList.add(red);
        colourList.add(blue);
        colourList.add(green);
        colourList.add(purple);
        colourList.add(yellow);
        Collections.shuffle(colourList);
        Player player= new Player("Player1",colourList.get(0),false,0);
        Player player1= new Player("Player2",colourList.get(1),false,1);
        Player player2= new Player("Player3",colourList.get(2),false,2);
        Player player3= new Player("Player4",colourList.get(3),false,3);
        playerList.add(player);
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
    }


    /**
     * add die in the dice list
     */
    public void Add90Die() {
        addBlueColourToNinetyDieList();
        addGreenColourToNinetyDieList();
        addPurpleColourToNinetyDieList();
        addRedColourToNinetyDieList();
        addYellowColourToNinetyDieList();
        Collections.shuffle(diceList);
    }
    public void addRedColourToNinetyDieList() {

        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Red", 1, getResources().getDrawable(R.drawable.red1)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Red", 2, getResources().getDrawable(R.drawable.red2)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Red", 3, getResources().getDrawable(R.drawable.red3)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Red", 4, getResources().getDrawable(R.drawable.red4)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Red", 5, getResources().getDrawable(R.drawable.red5)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Red", 6, getResources().getDrawable(R.drawable.red6)));
        }
    }

    public void addBlueColourToNinetyDieList() {

        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Blue", 1, getResources().getDrawable(R.drawable.blue1)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Blue", 2, getResources().getDrawable(R.drawable.blue2)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Blue", 3, getResources().getDrawable(R.drawable.blue3)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Blue", 4, getResources().getDrawable(R.drawable.blue4)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Blue", 5, getResources().getDrawable(R.drawable.blue5)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Blue", 6, getResources().getDrawable(R.drawable.blue6)));
        }
    }

    public void addYellowColourToNinetyDieList() {

        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Yellow", 1, getResources().getDrawable(R.drawable.yellow1)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Yellow", 2, getResources().getDrawable(R.drawable.yellow2)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Yellow", 3, getResources().getDrawable(R.drawable.yellow3)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Yellow", 4, getResources().getDrawable(R.drawable.yellow4)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Yellow", 5, getResources().getDrawable(R.drawable.yellow5)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Yellow", 6, getResources().getDrawable(R.drawable.yellow6)));
        }
    }

    public void addPurpleColourToNinetyDieList() {

        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Purple", 1, getResources().getDrawable(R.drawable.purple1)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Purple", 2, getResources().getDrawable(R.drawable.purple2)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Purple", 3, getResources().getDrawable(R.drawable.purple3)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Purple", 4, getResources().getDrawable(R.drawable.purple4)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Purple", 5, getResources().getDrawable(R.drawable.purple5)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Purple", 6, getResources().getDrawable(R.drawable.purple6)));
        }
    }

    public void addGreenColourToNinetyDieList() {

        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Green", 1, getResources().getDrawable(R.drawable.green1)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Green", 2, getResources().getDrawable(R.drawable.green2)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Green", 3, getResources().getDrawable(R.drawable.green3)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Green", 4, getResources().getDrawable(R.drawable.green4)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Green", 5, getResources().getDrawable(R.drawable.green5)));
        }
        for (int i = 0; i < 3; i++) {
            diceList.add(new Dice("Green", 6, getResources().getDrawable(R.drawable.green6)));
        }
    }

    /**
     * @param imgBtn
     * compare the pool and board die values
     */
    public void CompareBoardAndPoolDie (ImageButton imgBtn) {

        int count = 9;
        for (int i = 0; i < count; i++) {
            if (imgBtn.getTag().equals(1) || imgBtn.getTag().equals(2) || imgBtn.getTag().equals(3) || imgBtn.getTag().equals(4) || imgBtn.getTag().equals(5) || imgBtn.getTag().equals(6)) {
                String temp = String.valueOf(PoolDice.get(i).number);
                if (imgBtn.getTag().equals(temp)) {
                    imgBtn.setImageDrawable(PoolDice.get(i).image);
                    count--;
                }
            }
            if (imgBtn.getTag().equals("blue") || imgBtn.getTag().equals("yellow") || imgBtn.getTag().equals("red") || imgBtn.getTag().equals("green") || imgBtn.getTag().equals("purple")) {
                if (imgBtn.getTag().equals(PoolDice.get(i).color)) {
                    imgBtn.setImageDrawable(PoolDice.get(i).image);
                    count--;
                }
            }
            if (imgBtn.getTag().equals("grey")) {
                imgBtn.setImageDrawable(PoolDice.get(i).image);
                count--;
            }
        }
    }

    /**
     * Shuffling a new die pool
     */
    public void NewShuffle() {
        round++;
        for (int i = 0; i < 9; i++) {
            PoolDice.add(diceList.get(i));
            diceList.remove(i);
        }
        initRecyclerView();
        Intent intent =new Intent(this, DiePool.class);
        startActivity(intent);

    }

    /**
     * Empty the list in a new round
     */
    public void emptyList(){
        for (int i = 0; i < PoolDice.size() ; i++) {
            PoolDice.remove(i);
        }
    }

    private void initRecyclerView(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    recyclerView = Game.this.findViewById(R.id.recyclerViewID);
                                    adapter = new DiePoolAdapter(PoolDice, Game.this);
                                    recyclerView.setAdapter(adapter);
                                    GridLayoutManager gridLayoutManager = new GridLayoutManager(Game.this, 2);
                                    recyclerView.setLayoutManager(gridLayoutManager);
                                }
                            }
                ,2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void newRound() {

        if (turn==0) {
            round++;
            turn++;
            Turns.setText("Turn: "+String.valueOf(turn));
            Rounds.setText("Turn: "+String.valueOf(round));
        }
        if (turn==1)
        {
            Shuffle.setVisibility(View.VISIBLE);

        }
        else{
            Shuffle.setVisibility(View.GONE);
        }
        if (turn==9){
            turn=0;
            emptyList();
        }
        if (round==10)
        {
            CalculateScore();
            Intent intent=new Intent(Game.this,ScoreBoard.class);
            startActivity(intent);
        }


    }

    public void onAddA(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment4, new Player1Board())
                .commit();
    }

    public void onAddB(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment4, new Player2Board())
                .commit();
    }

    public void onReplaceWithA(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment4, new Player3Board())
                .addToBackStack(null)
                .commit();
    }

    public void onReplaceWithB(View view) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment4, new Player4Board())
                .addToBackStack(null)
                .commit();
    }

    public void placeDieOnCell(Dice dice){
    }

    @Override
    public void onInputASent(ArrayList input) {
        if (input==null){
            recyclerViewList=new ArrayList<>();
        }
        recyclerViewList=input;
    }

    public void frag1(ArrayList list){
        Rows=list;
        Bundle b=new Bundle();
        columns = (List<RowColumn>) b.getSerializable("valuesArray");
    }

    public static boolean CheckifsafetoplaceDieonEdges(int row, int column) {
        if (row == Rows.size() - 1 || row == 0) {
            return true;
        }
        if (column == columns.size()-1 || column == 0) {
            return true;
        }
        return false;
    }

    public static boolean ChecktheplacingCondition(Cell grid[][], int row, int column) {
        for (int x = row - 1; x <= row + 1; x++) {
            for (int y = column - 1; y <= column + 1; y++) {
                try {
                    if (!grid[x][y].isEmpty()) {
                        return true;
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Checked");
                }
            }
        }

        return false;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public long calculateFreeSlots(){
        long count;
        Stream<RowColumn> streams=  Rows.stream().filter((new Predicate<RowColumn>() {
            @Override
            public boolean test(RowColumn x) {
                return x.getCell().OccupiedCell = false;
            }
        }));
        Stream<RowColumn> stream=  columns.stream().filter((new Predicate<RowColumn>() {
            @Override
            public boolean test(RowColumn x) {
                return x.getCell().OccupiedCell = false;
            }
        }));
        count=stream.count() + streams.count();

        return count;
    }

    //checking if the values around where you want to place your die allows you to place it.
    public boolean CheckBlocksAround(int row, int column, int Value) {

        if (GridValues[row - 1][column] != Value && row > 1) {
            if (GridValues[row + 1][column] != Value) {
                if (GridValues[row][column + 1] == Value) {
                    if (GridValues[row][column - 1] == Value) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    /**
     * @param number1
     * @param number2
     * @param gridValues
     * This method gets the sets of numbers in the board
     * @return
     */
    public int GetNumberSets(int number1, int number2, Cell[][] gridValues) {
        int numbers[] = new int[6];
        int Repeated;
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 0;
        }

        for (int i = 0; i < Rows.size(); i++) {
            for (int j = 0; j <columns.size(); j++) {
                if (!gridValues[i][j].isEmpty()) {
                    numbers[gridValues[i][j].getDie().getNumber() - 1]++;
                }
            }
        }
        if (number1 == 0 && number2 == 0) {
            Repeated = numbers[0];
            for (int number : numbers) {
                if (number < Repeated) {
                    Repeated = number;
                }
            }
            return Repeated;
        } else {
            return (numbers[number1 - 1] > numbers[number2 - 1]) ? numbers[number2 - 1] : numbers[number1 - 1];
        }

    }

    /**
     * @param grid
     * this method gets the sets of colors in the board
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int GetColorSets(Cell[][] grid) {
        Map<String, Integer> colors = new HashMap<>();
        for (String color : Dice.values()) {
            colors.put(color, 0);
        }
        for (int x = 0; x < Rows.size(); x++) {
            for (int y = 0; y < columns.size(); y++) {
                if (!grid[x][y].isEmpty()) {
                    String key = grid[x][y].getDie().getColor();
                    int PrevValue = colors.get(key);
                    colors.replace(key, PrevValue++);
                }
            }
        }
        return colors.values()
                .stream()
                .mapToInt(new ToIntFunction<Integer>() {
                    @Override
                    public int applyAsInt(Integer num) {
                        return num;
                    }
                })
                .min()
                .orElse(0);

    }

    /**
     * @param column
     * gets holes
     * @return
     */
    private boolean GetHoles(Cell[] column) {
        for (Cell cell : column) {
            if (cell.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param gridValues
     * @param color
     * gets total of numbers in the colored dice
     * @return
     */
    public int GetTotalofColors(Cell[][] gridValues, Dice color) {
        int total = 0;
        int dice = 0;
        for (int x = 0; x < Rows.size(); x++) {
            for (int y = 0; y < columns.size(); y++) {
                if (!gridValues[x][y].isEmpty()) {
                    if (gridValues[x][y].getDie().getColor().equals(color))
                        total = total + gridValues[x][y].getDie().getNumber();
                    dice++;
                }
            }
        }
        int numberOfDice = dice;
        return total;
    }

    /**
     * @param gridValues
     * gets the rows with different nnumbers in die
     * @return
     */


    /**
     * @param gridValues
     * gets number of column with different colors
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public int GetDifferentColorcolumn(Cell[][] gridValues) {
        int repeated = 0;
        Cell[] column;
        for (int x = 0; x < columns.size(); x++) {
            column = new Cell[Rows.size()];
            for (int y = 0; y < Rows.size(); y++) {
                column[y] = gridValues[y][x];
            }
            if (!GetHoles(column)) {
                if (Rows.size() == Arrays.stream(column)
                        .mapToInt(new ToIntFunction<Cell>() {
                            @Override
                            public int applyAsInt(Cell cell) {
                                return cell.getDie().getColor();
                            }
                        })
                        .distinct()
                        .count()) {
                    repeated++;
                }
            }
        }
        return repeated;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int CalculateScore() {
        Cell[][] grid = new Cell[4][5];
        Cell[] row = new Cell[4];
        int num=6;
        int num2=5;
        Dice Color = Player.getColour();
        int score = GetTotalofColors(grid, Color);

        if (GetHoles(row))
            score--;
        if (GetDifferentColorcolumn(grid) != 0)
            score=+5;
        if (GetColorSets(grid)!=0)
            score=+4;
        if (GetNumberSets(5,6,grid)!=0)
            score=+2;
        return score;



    }


}

