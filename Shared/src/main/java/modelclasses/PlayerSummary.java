package modelclasses;

import java.io.Serializable;

public class PlayerSummary implements Serializable {
    private int pointsFromRoutes;
    private int pointsGainedFromDestinations;
    private int pointsLostFromDestinations;
    private int mostClaimedRoutesPoints;
    private int totalPoints;
    private boolean isWinner;
    private String playerName;

    public PlayerSummary() {
        pointsFromRoutes = 0;
        pointsGainedFromDestinations = 0;
        pointsLostFromDestinations = 0;
        mostClaimedRoutesPoints = 0;
        totalPoints = 0;
        isWinner = false;
        playerName = "";
    }

    public PlayerSummary(int pointsFromRoutes, int pointsGainedFromDestinations, int pointsLostFromDestinations, int mostClaimedRoutesPoints, int totalPoints, boolean isWinner, String playerName) {
        this.pointsFromRoutes = pointsFromRoutes;
        this.pointsGainedFromDestinations = pointsGainedFromDestinations;
        this.pointsLostFromDestinations = pointsLostFromDestinations;
        this.mostClaimedRoutesPoints = mostClaimedRoutesPoints;
        this.totalPoints = totalPoints;
        this.isWinner = isWinner;
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void addRoutePoints(int points) {
        this.pointsFromRoutes += points;
    }

    public void addGainedDestinationPoints(int points) {
        this.pointsGainedFromDestinations += points;
    }

    public void addLostDestinationPoints(int points) {
        this.pointsLostFromDestinations += points;
    }

    public int getPointsFromRoutes() {
        return pointsFromRoutes;
    }

    public void setPointsFromRoutes(int pointsFromRoutes) {
        this.pointsFromRoutes = pointsFromRoutes;
    }

    public int getPointsGainedFromDestinations() {
        return pointsGainedFromDestinations;
    }

    public void setPointsGainedFromDestinations(int pointsGainedFromDestinations) {
        this.pointsGainedFromDestinations = pointsGainedFromDestinations;
    }

    public int getPointsLostFromDestinations() {
        return pointsLostFromDestinations;
    }

    public void setPointsLostFromDestinations(int pointsLostFromDestinations) {
        this.pointsLostFromDestinations = pointsLostFromDestinations;
    }

    public int getMostClaimedRoutesPoints() {
        return mostClaimedRoutesPoints;
    }

    public void setMostClaimedRoutesPoints(int mostClaimedRoutesPoints) {
        this.mostClaimedRoutesPoints = mostClaimedRoutesPoints;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public boolean isWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }
}
