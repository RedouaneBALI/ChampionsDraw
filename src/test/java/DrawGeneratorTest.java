import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.example.DrawGenerator;
import org.example.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DrawGeneratorTest {

  private Team       team1A;
  private Team       team1B;
  private Team       team1C;
  private Team       team1D;
  private Team       team2A;
  private Team       team2B;
  private Team       team2C;
  private Team       team2D;
  private Team       team3A;
  private Team       team3B;
  private Team       team3C;
  private Team       team3D;
  private Team       team4A;
  private Team       team4B;
  private Team       team4C;
  private Team       team4D;
  private List<Team> teams3x3;
  private List<Team> teams4x4;

  @BeforeEach
  public void initTeams() {
    team1A   = new Team("1A", 1);
    team1B   = new Team("1B", 1);
    team1C   = new Team("1C", 1);
    team1D   = new Team("1D", 1);
    team2A   = new Team("2A", 2);
    team2B   = new Team("2B", 2);
    team2C   = new Team("2C", 2);
    team2D   = new Team("2D", 2);
    team3A   = new Team("3A", 3);
    team3B   = new Team("3B", 3);
    team3C   = new Team("3C", 3);
    team3D   = new Team("3D", 3);
    team4A   = new Team("4A", 4);
    team4B   = new Team("4B", 4);
    team4C   = new Team("4C", 4);
    team4D   = new Team("4D", 4);
    teams3x3 = List.of(team1A, team1B, team1C, team2A, team2B, team2C, team3A, team3B, team3C);
    teams4x4 =
        List.of(team1A, team1B, team1C, team1D, team2A, team2B, team2C, team2D, team3A, team3B, team3C, team3D, team4A, team4B, team4C, team4D);

  }

  @Test
  public void testGetPotentialOpponents() {
    team2B.addOpponent(team1A);
    team2B.addOpponent(team1B);
    DrawGenerator generator = new DrawGenerator(teams3x3);
    assertFalse(generator.getPotentialOpponents(team1C, 2, 2).contains(team2B));
  }

  @Test
  public void testGetPotentialOpponents2() {
    team1A.addOpponent(team1B);
    team1A.addOpponent(team1C);
    DrawGenerator generator = new DrawGenerator(teams3x3);
    assertTrue(generator.getPotentialOpponents(team1A, 2, 2).contains(team2B));
    assertTrue(generator.getPotentialOpponents(team1B, 2, 2).contains(team2B));
  }

  @Test
  public void testStartDraw3x3() {
    DrawGenerator generator = new DrawGenerator(teams3x3);
    generator.startDraw(3, 2);

    for (Team team : teams3x3) {
      assertEquals(6, team.getOpponents().size());
      assertEquals(2, team.getNbOpponentByPot(1));
      assertEquals(2, team.getNbOpponentByPot(2));
      assertEquals(2, team.getNbOpponentByPot(3));
    }
  }

  @Test
  public void testStartDraw4x4() {
    DrawGenerator generator = new DrawGenerator(teams4x4);
    generator.startDraw(4, 2);

    for (Team team : teams4x4) {
      assertEquals(8, team.getOpponents().size());
      assertEquals(2, team.getNbOpponentByPot(1));
      assertEquals(2, team.getNbOpponentByPot(2));
      assertEquals(2, team.getNbOpponentByPot(3));
      assertEquals(2, team.getNbOpponentByPot(4));
    }
  }

  @Test
  public void testAddGameDeleteGame() {
    DrawGenerator generator = new DrawGenerator(teams4x4);
    generator.startDraw(4, 2);

    Team teamA = generator.getGames().get(0).getTeamA();
    Team teamB = generator.getGames().get(0).getTeamB();

    generator.deleteAllGamesBasedOnPots(teamA.getPot(), teamB.getPot());

    assertFalse(teamA.getOpponents().contains(teamB));
    assertFalse(teamB.getOpponents().contains(teamA));
  }
}
