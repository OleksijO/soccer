package soccer;

/**
 * Created by Oleksii_Onysymchuk on 3/14/2017.
 */
public class Game {
    private String firstTeamName;
    private String secondTeamName;
    private int strikesCount = 0;
    private int firstTeamScore = 0;
    private int secondTeamScore = 0;
    private int firstTeamFailedPenaltiesPlayersCost;
    private int secondTeamFailedPenaltiesPlayersCost;

    public Game() {
    }

    public Game(String firstTeamName, String secondTeamName) {
        this.firstTeamName = firstTeamName;
        this.secondTeamName = secondTeamName;
    }

    public boolean[] teamShotSuccessful(int teamNumber, boolean isSuccessful, String playerName) {
        if (isFinished()){
            throw new IllegalStateException("The game is OVER!");
        }
        strikesCount++;
        addPlayerStatistics(playerName, isSuccessful);
        if (isSuccessful) {
            if (teamNumber == 1) {
                firstTeamScore++;
            } else {
                secondTeamScore++;
            }
        } else {
            if (teamNumber == 1) {
                firstTeamFailedPenaltiesPlayersCost += getPlayerCostByName(playerName);
            } else {
                secondTeamFailedPenaltiesPlayersCost += getPlayerCostByName(playerName);
            }
        }
        return getPlayersStatistics(playerName);
    }

    protected int getPlayerCostByName(String playerName) {
        return 0;
    }

    protected void addPlayerStatistics(String playerName, boolean isSuccessful) {

    }

    protected boolean[] getPlayersStatistics(String playerName) {
        return new boolean[10];
    }

    public String score() {
        String firstPart = "(" + firstTeamScore + ") " + firstTeamName;
        String secondPart = secondTeamName + " (" + secondTeamScore + ")";
        if (strikesCount / 2 >= 7) {
            firstPart = firstPart + " [" + firstTeamFailedPenaltiesPlayersCost + "]";
            secondPart = secondPart + " [" + secondTeamFailedPenaltiesPlayersCost + "]";
        }
        return firstPart + " : " + secondPart;
    }

    public boolean isFinished() {
        if ((strikesCount < 10) && isPairOfShotsFull()) {
            return scoreDifference() > strikesTo5();
        }
        if ((strikesCount == 10)) {
            if (isEqualScore()) {
                return false;
            } else {
                return true;
            }
        }
        if (isAdditionalShots()) {
            if (isPairOfShotsFull()) {
                if (isEqualScore()) {
                    return false;
                } else {
                    return true;
                }

            } else {
                return false;
            }
        }
        return false;
    }

    public int strikesTo5() {
        return 5 - strikesCount / 2;
    }

    public int scoreDifference() {
        return Math.abs(firstTeamScore - secondTeamScore);
    }

    public boolean isAdditionalShots() {
        return strikesCount > 10;
    }

    public boolean isPairOfShotsFull() {
        return strikesCount % 2 == 0;
    }

    private boolean isEqualScore() {
        return firstTeamScore == secondTeamScore;
    }

    public void setFirstTeamName(String firstTeamName) {
        this.firstTeamName = firstTeamName;
    }

    public void setSecondTeamName(String secondTeamName) {
        this.secondTeamName = secondTeamName;
    }
}
