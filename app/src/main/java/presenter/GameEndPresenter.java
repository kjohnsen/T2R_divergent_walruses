package presenter;

import java.util.ArrayList;
import java.util.List;

import activity.IGameEndView;
import model.IUIFacade;
import model.UIFacade;
import modelclasses.GameInfo;
import modelclasses.Player;
import modelclasses.PlayerSummary;

public class GameEndPresenter implements IGameEndPresenter {
    private IUIFacade iuiFacade = UIFacade.getInstance();
    private IGameEndView gameEndView;

    public GameEndPresenter(IGameEndView gameEndView) {
        this.gameEndView = gameEndView;
    }

    @Override
    public List<PlayerSummary> getPlayerSummaryInfo() {
        GameInfo currentGame = iuiFacade.getCurrentGame();
        List<Player> players = currentGame.getPlayers();
        List<PlayerSummary> playerSummaries = new ArrayList<>();
        int maxRoutes = players.get(0).getRoutes().size();
        for(Player player : players) {
            PlayerSummary playerSummary = new PlayerSummary();
            playerSummary.setPlayerName(player.getUsername());
            playerSummary.setPointsFromRoutes(player.getPoints());
            //TODO: check for points gained and lost from destination cards
            if(player.getRoutes().size() > maxRoutes) {
                maxRoutes = player.getRoutes().size();
            }
            int totalPoints = playerSummary.getPointsFromRoutes(); //TODO: add everything else...
            playerSummary.setTotalPoints(totalPoints);
            playerSummaries.add(playerSummary);
        }

        for(int i = 0; i < players.size(); ++i) {
            Player player = players.get(i);
            if(player.getRoutes().size() == maxRoutes) {
                playerSummaries.get(i).setMostClaimedRoutesPoints(10);
                playerSummaries.get(i).setTotalPoints(playerSummaries.get(i).getTotalPoints() + 10);
            }
        }

        int maxScore = playerSummaries.get(0).getTotalPoints();
        for(int i = 1; i < playerSummaries.size(); ++i) {
            if(playerSummaries.get(i).getTotalPoints() > maxScore) {
                maxScore = playerSummaries.get(i).getTotalPoints();
            }
        }

        for(int i = 0; i < playerSummaries.size(); ++i) {
            if(playerSummaries.get(i).getTotalPoints() == maxScore) {
                playerSummaries.get(i).setWinner(true);
            }
        }

        return playerSummaries;
    }

    public IUIFacade getIuiFacade() {
        return iuiFacade;
    }

    public void setIuiFacade(IUIFacade iuiFacade) {
        this.iuiFacade = iuiFacade;
    }

    public IGameEndView getGameEndView() {
        return gameEndView;
    }

    public void setGameEndView(IGameEndView gameEndView) {
        this.gameEndView = gameEndView;
    }
}
