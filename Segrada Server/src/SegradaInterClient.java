import java.io.IOException;
import java.util.List;

public interface SegradaInterClient   {


    void pushLoggedPlayer(Player player) throws IOException;


    void notifyGameStarted(List<Player> players, int timeout) throws IOException;

    void pushDraft(List<Die> draft) throws IOException;

    void notifyTurn(Player p, int round, int turn) throws IOException;

    void notifyMoveResponse(boolean response, String name, Die d, int row, int column) throws IOException;

    void notifyEndTurn(Player p) throws IOException;

    void notifyEndRound(List<Die> dice) throws IOException;

    Player getPlayer() throws IOException;

    void pushFinalScore(List<Player> players) throws IOException;
    void notifyFinishUpdate(String name) throws IOException;

    void pushOpponentsInit(List<Player> thinPlayers) throws IOException;

    void notifyEndTimeOut(Player p);
}
