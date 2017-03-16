package soccer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {
    @Spy
    private Game game;
    private String firstTeamName = "Metallist";
    private String secondTeamName = "Arsenal";

    @Before
    public void setUp() {
        game.setFirstTeamName(firstTeamName);
        game.setSecondTeamName(secondTeamName);
    }

    @Test
    public void testScoreIs10() {
        game.teamShotSuccessful(1, true, null);
        game.teamShotSuccessful(2, false, null);
        assertThat(game.score(), is("(1) Metallist : Arsenal (0)"));
    }

    @Test
    public void testScoreIs01() {
        game.teamShotSuccessful(1, false, null);
        game.teamShotSuccessful(2, true, null);
        assertThat(game.score(), is("(0) Metallist : Arsenal (1)"));
    }

    @Test
    public void testScoreIs43() {
        bothTeamShots(3, true);
        firstTeamShots(1, true);
        assertThat(game.score(), is("(4) Metallist : Arsenal (3)"));
    }

    @Test
    public void testIsNotFinishedJustAfterStart() {
        assertThat(game.isFinished(), is(false));
        game.setFirstTeamName(firstTeamName);
        game.setSecondTeamName(secondTeamName);
    }

    @Test
    public void testIsFinishedAfter10ShotsAndScore54() {
        bothTeamShots(4, true);
        firstTeamShots(1, true);
        secondTeamShots(1, false);
        assertThat(game.isFinished(), is(true));
    }

    @Test
    public void testIsFinishedAfter10ShotsAndScore55() {
        bothTeamShots(5, true);
        assertThat(game.isFinished(), is(false));
    }

    @Test
    public void testIsNotFinishedAfter9ShotsAndScore50() {
        bothTeamShots(4, true);
        firstTeamShots(1, true);
        assertThat(game.isFinished(), is(false));
    }

    @Test
    public void testIsNotFinishedAfter11ShotsAndScore65() {
        bothTeamShots(5, true);
        firstTeamShots(1, true);
        assertThat(game.isFinished(), is(false));
    }

    @Test
    public void testIsFinishedAfter12ShotsAndScore65() {
        bothTeamShots(5, true);
        firstTeamShots(1, true);
        secondTeamShots(1, false);
        assertThat(game.isFinished(), is(true));
    }

    @Test
    public void testIsFinishedAfter6ShotsAndScore30() {
        firstTeamShots(3, true);
        secondTeamShots(3, false);
        assertThat(game.isFinished(), is(true));
    }

    @Test
    public void testReturnsStatAfterShot() {
        boolean[] shotsHistory = new boolean[]{true, false, true, true, true, true, true, true, true, false};
        when(game.getPlayersStatistics("Player1"))
                .thenReturn(shotsHistory);
        assertThat(game.teamShotSuccessful(1, false, "Player1"), is(shotsHistory));
    }

    @Test
    public void testDoNotShowsFailedPlayersSumAfter6Series() {
        bothTeamShots(6, false);
        assertThat(game.score(), is("(0) Metallist : Arsenal (0)"));
    }

    @Test
    public void testShowsFailedPlayersSumAfter7Series() {
        when(game.getPlayerCostByName(anyString())).thenReturn(10);
        bothTeamShots(7, false);
        assertThat(game.score(), is("(0) Metallist [70] : Arsenal (0) [70]"));
    }

    private void firstTeamShots(int quantity, boolean isSuccessful) {
        for (int i = 0; i < quantity; i++) {
            game.teamShotSuccessful(1, isSuccessful, null);
        }
    }

    private void secondTeamShots(int quantity, boolean isSuccessful) {
        for (int i = 0; i < quantity; i++) {
            game.teamShotSuccessful(2, isSuccessful, null);
        }
    }
    private void bothTeamShots(int quantity, boolean isSuccessful) {
        for (int i = 0; i < quantity; i++) {
            game.teamShotSuccessful(1, isSuccessful, null);
            game.teamShotSuccessful(2, isSuccessful, null);
        }
    }


}
