package soccer;

/**
 * Created by Oleksii_Onysymchuk on 3/14/2017.
 */
public class Game {
    private Team[] teams = {new Team(), new Team()};
    private int strikesCount = 0;

    private class Team {
        int score = 0;
        int failedPenaltiesPlayersCost = 0;
        public String name;
    }

    public boolean[] teamShotSuccessful(int teamNumber, boolean isSuccessful, String playerName) {
        teamNumber = checkAndPrepareTeamNumber(teamNumber);
        if (isFinished()) {
            throw new IllegalStateException("The game is OVER!");
        }
        strikesCount++;
        addPlayerStatistics(playerName, isSuccessful);
        if (isSuccessful) {
            teams[teamNumber].score++;
        } else {
            teams[teamNumber].failedPenaltiesPlayersCost += getPlayerCostByName(playerName);
        }
        return getPlayersStatistics(playerName);
    }

    private int checkAndPrepareTeamNumber(int teamNumber) {
        if ((teamNumber == 1) || (teamNumber == 2)) {
            return --teamNumber;
        }
        throw new IllegalArgumentException("Team number should be 1 or 2");
    }

    public String score() {
        Team team1 = getFirstTeam();
        Team team2 = getSecondTeam();
        String firstPart = String.format("(%d) %s", team1.score,team1.name);
        String secondPart = String.format("%s (%d)", team2.name, team2.score);
        if (is7SeriesPassed()) {
            firstPart = String.format("%s [%d]", firstPart, team1.failedPenaltiesPlayersCost);
            secondPart = String.format("%s [%d]", secondPart, team2.failedPenaltiesPlayersCost);
        }
        return firstPart + " : " + secondPart;
    }

    private boolean is7SeriesPassed() {
        return strikesCount / 2 >= 7;
    }

    public boolean isFinished() {
        if (isMainSeries() && isSerieFinished()) {
            return scoreDifference() > strikesTo5();
        }
        if (isMainSeriesFinished()) {
            return !isEqualScore();
        }
        if (isAdditionalShots()) {
            return isSerieFinished() && !isEqualScore();
        }
        return false;
    }
    private boolean isMainSeriesFinished() {
        return strikesCount == 10;
    }

    private boolean isMainSeries() {
        return strikesCount < 10;
    }

    private int strikesTo5() {
        return 5 - strikesCount / 2;
    }

    private int scoreDifference() {
        return Math.abs(getFirstTeam().score - getSecondTeam().score);
    }

    private boolean isAdditionalShots() {
        return strikesCount > 10;
    }

    private boolean isSerieFinished() {
        return strikesCount % 2 == 0;
    }

    private boolean isEqualScore() {
        return getFirstTeam().score == getSecondTeam().score;
    }


    protected int getPlayerCostByName(String playerName) {
        // Here should be service method call
        return 0;
    }

    protected void addPlayerStatistics(String playerName, boolean isSuccessful) {
        // Here should be service method call
    }

    protected boolean[] getPlayersStatistics(String playerName) {
        // Here should be service method call
        return new boolean[10];
    }

    private Team getFirstTeam() {
        return teams[0];
    }

    private Team getSecondTeam() {
        return teams[1];
    }

    public void setFirstTeamName(String firstTeamName) {
        getFirstTeam().name = firstTeamName;
    }

    public void setSecondTeamName(String secondTeamName) {
        getSecondTeam().name = secondTeamName;
    }
}
