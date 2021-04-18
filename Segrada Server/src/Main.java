import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//reference https://github.com/8549/Sagrada/blob/master/src/main/java/it/polimi/ingsw/network/server/MainServer.java
public class Main
{
    public static final int DEFAULT_SOCKET_PORT= 3130;
    private static final int DEFAULT_CONNECTION_TIMEOUT = 5000;
    private static int connectionTimeout;
    private static  int turnTimeout;

    public int getTurnTimeout() {
        return turnTimeout;
    }

    private List<SegradaInterClient> connectedClients = new ArrayList<>();
    private List<SegradaInterClient> inGameClients = new ArrayList<>();
    private Game gm;


    public static void main(String[] args) {
        if (args.length > 1) {
            try {
                connectionTimeout = Integer.valueOf(args[0]);
                System.out.println("[DEBUG] Connection timer set to " + connectionTimeout);
            } catch (NumberFormatException e) {
                System.err.println("Couldn't parse args[0] from command line, using the default connection timeout (" + DEFAULT_CONNECTION_TIMEOUT + "ms)");
                connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
            }
            try {
                turnTimeout = Integer.valueOf(args[1]);
                System.out.println("[DEBUG] Turn timer set to " + turnTimeout);
            } catch (NumberFormatException e) {
                System.err.println("Couldn't parse args[1] from command line, using the default turn timer timeout (" + GameManager.DEFAULT_TURN_TIMEOUT + "ms)");
                turnTimeout = Game.DEFAULT_TURN_TIMEOUT;
            }
        } else if (args.length > 0) {
            try {
                connectionTimeout = Integer.valueOf(args[0]);
                turnTimeout = Integer.valueOf(args[0]);
                System.out.println("[DEBUG] Both connection and turn timers set to " + connectionTimeout);
            } catch (NumberFormatException e) {
                System.err.print("Couldn't parse args[0] from command line, using the default connection timeout (" + DEFAULT_CONNECTION_TIMEOUT + "ms)");
                System.err.print(" and turn timeout (" + Game.DEFAULT_TURN_TIMEOUT + "ms)\n");
                connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
                turnTimeout = Game.DEFAULT_TURN_TIMEOUT;
            }
        } else {
            System.out.println("[DEBUG] Using default timers (connection " + DEFAULT_CONNECTION_TIMEOUT + "ms, turn " + GameManager.DEFAULT_TURN_TIMEOUT + "ms)");
            connectionTimeout = DEFAULT_CONNECTION_TIMEOUT;
            turnTimeout = Game.DEFAULT_TURN_TIMEOUT;
        }
    }



    public  List<Player> getPlayersFromClients(List<SegradaInterClient> clients){
        List<Player> players= new ArrayList<>();

        for (SegradaInterClient c : clients){
            try {
                players.add(c.getPlayer());
            } catch (IOException e) {
                e.getMessage();
            }
        }
        return players;
    }

    public  void addLoggedPlayer(Player p) {
        for(SegradaInterClient c : connectedClients){
            try {
                if (!c.getPlayer().getName().equals(p.getName())){
                    c.pushLoggedPlayer(p);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }

    }




    public  void gameStartedProcedures(List<Player> players, int timeoutMove){
        List<Player> p = new ArrayList<>(players);
        inGameClients.addAll(connectedClients);
        for (SegradaInterClient c : inGameClients){
            try {
                c.notifyGameStarted(p, timeoutMove);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }









    public  void initPlayersData(List<Player> players){
        for(SegradaInterClient client1 : inGameClients){
            List<Player> thinPlayers = new ArrayList<>();
            for(Player client2 : players){
                try {
                    if (!client1.getPlayer().getName().equals(client2.getName())){

                                         }
                } catch (IOException e) {
                    e.getMessage();
                }
            }
            System.out.println("Pushing opponents: " + thinPlayers.toString());
            try {
                client1.pushOpponentsInit(thinPlayers);
            } catch (IOException e) {
                e.getMessage();
            }
        }

    }






    public  void setDraft(List<Die> draft){
        for (SegradaInterClient c: inGameClients){
            try {
                c.pushDraft(draft);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public  void notifyBeginTurn(Player p, int round, int turn){
        for (SegradaInterClient c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName()) && gm.getPlayerByName(c.getPlayer().getName()).getStatus().equals(PlayerStatus.ACTIVE) ) {
                    c.notifyTurn(p, round, turn);}
            } catch (IOException e) {
                e.getMessage();
            }
        }


    }


    public  void notifyPlacementResponse(boolean response, Player p, Die d, int row, int column){
        for(SegradaInterClient c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName()) && gm.getPlayerByName(c.getPlayer().getName()).getStatus().equals(PlayerStatus.ACTIVE) ){
                    c.notifyMoveResponse(response, p.getName(), d, row, column);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }


    }

    public  void handleMove(Die d, int row, int column, Player p ){
        gm.processMove(d, row, column, p);
    }

    public void moveTimeOut(Player p){
        for(SegradaInterClient c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName())){
                    c.notifyEndTimeOut(p);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }


    }

    public void notifyEndTurn(Player p){
        for(SegradaInterClient c : inGameClients){
            try {
                if(!c.getPlayer().getName().equals(p.getName())){
                    c.notifyEndTurn(p);
                }
            } catch (IOException e) {
                e.getMessage();
            }
        }



    }
    public void notifyEndRound( List<Die> dice){
        for(SegradaInterClient c: inGameClients){
            try {
                c.notifyEndRound(dice);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }



    public void passTurn(String name){
        if(gm.getCurrentPlayer().getName().equals(name)){
            gm.endCurrentTurn();
        }
    }



    public void notifyScore(List<Player> playersWithPoints) {
        for(SegradaInterClient c : inGameClients){
            try {
                c.pushFinalScore(playersWithPoints);
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }



    public void notifyFinishUpdate(SegradaInterClient c, Player p) {
        try {
            c.notifyFinishUpdate(p.getName());
        } catch (IOException e) {
            e.getMessage();
        }

    }
}
