import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.redouanebali.model.DrawGenerator;
import io.github.redouanebali.model.Team;
import java.io.InputStream;
import java.util.List;
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
    team2B.addOpponent(team1A, true);
    team2B.addOpponent(team1B, false);
    DrawGenerator generator              = new DrawGenerator();
    List<Team>    potentialOpponentsHome = generator.getPotentialOpponents(teams3x3, team1C, 2, 2, true);
    List<Team>    potentialOpponentsAway = generator.getPotentialOpponents(teams3x3, team1C, 2, 2, false);
    assertFalse(potentialOpponentsHome.contains(team2B));
    assertFalse(potentialOpponentsAway.contains(team2B));
  }

  @Test
  public void testGetPotentialOpponents2() {
    team1A.addOpponent(team1B, true);
    team1A.addOpponent(team1C, false);
    DrawGenerator generator = new DrawGenerator();
    assertTrue(generator.getPotentialOpponents(teams3x3, team1A, 2, 2, false).contains(team2B));
    assertTrue(generator.getPotentialOpponents(teams3x3, team1B, 2, 2, true).contains(team2B));
  }

  @Test
  public void testGetPotentialOpponentsHomeAway() {
    DrawGenerator generator = new DrawGenerator();
    team1A.addOpponent(team2B, true); // 1A vs 2B
    team2A.addOpponent(team1C, true); // 2A vs 1C
    // 1C cannot play away VS 2A
    assertFalse(generator.getPotentialOpponents(teams3x3, team1C, 2, 2, true).contains(team2A));
    // 2C cannot play away VS 1A
    assertFalse(generator.getPotentialOpponents(teams3x3, team2C, 1, 2, true).contains(team1A));
    team1A.addOpponent(team3C, false); // 3C vs 1A
    team2A.addOpponent(team3C, false); // 3C vs 2A
    // 1B cannot play away vs 3C
    assertFalse(generator.getPotentialOpponents(teams3x3, team1B, 3, 2, true).contains(team3C));
    // 2B cannot play away vs 3C
    assertFalse(generator.getPotentialOpponents(teams3x3, team2B, 3, 2, true).contains(team3C));
  }


  @Test
  public void testStartDraw3x3() {
    DrawGenerator generator = new DrawGenerator();
    generator.startDraw(teams3x3, 2);

    for (Team team : teams3x3) {
      assertEquals(6, team.getAllOpponents().size());
      assertEquals(3, team.getHomeOpponents().size());
      assertEquals(3, team.getAwayOpponents().size());
      assertEquals(1, team.getNbHomeOpponentByPot(1));
      assertEquals(1, team.getNbHomeOpponentByPot(1));
      assertEquals(1, team.getNbHomeOpponentByPot(2));
      assertEquals(1, team.getNbHomeOpponentByPot(2));
      assertEquals(1, team.getNbHomeOpponentByPot(3));
      assertEquals(1, team.getNbHomeOpponentByPot(3));
    }
  }

  @Test
  public void testStartDraw4x4() {
    DrawGenerator generator = new DrawGenerator();
    generator.startDraw(teams4x4, 2);
    for (Team team : teams4x4) {
      assertEquals(8, team.getAllOpponents().size());
      assertEquals(1, team.getNbHomeOpponentByPot(1));
      assertEquals(1, team.getNbAwayOpponentByPot(1));
      assertEquals(1, team.getNbHomeOpponentByPot(2));
      assertEquals(1, team.getNbAwayOpponentByPot(2));
      assertEquals(1, team.getNbHomeOpponentByPot(3));
      assertEquals(1, team.getNbAwayOpponentByPot(3));
      assertEquals(1, team.getNbHomeOpponentByPot(4));
      assertEquals(1, team.getNbAwayOpponentByPot(4));
    }
  }

  @Test
  public void testStartC1Draw() {
    DrawGenerator generator = new DrawGenerator();
    try {
      ObjectMapper mapper      = new ObjectMapper();
      InputStream  inputStream = getClass().getResourceAsStream("/teams-c1.json");
      List<Team> realTeams = mapper.readValue(inputStream, new TypeReference<List<Team>>() {
      });
      assertEquals(36, realTeams.size());
      generator.startDraw(realTeams, 2);
      for (Team team : realTeams) {
        assertEquals(8, team.getAllOpponents().size());
        assertEquals(1, team.getNbHomeOpponentByPot(1));
        assertEquals(1, team.getNbAwayOpponentByPot(1));
        assertEquals(1, team.getNbHomeOpponentByPot(2));
        assertEquals(1, team.getNbAwayOpponentByPot(2));
        assertEquals(1, team.getNbHomeOpponentByPot(3));
        assertEquals(1, team.getNbAwayOpponentByPot(3));
        assertEquals(1, team.getNbHomeOpponentByPot(4));
        assertEquals(1, team.getNbAwayOpponentByPot(4));
      }
    } catch (Exception e) {
      throw new RuntimeException("JSON Error", e);
    }
  }

  @Test
  public void testStartC3Draw() {
    DrawGenerator generator = new DrawGenerator();
    try {
      ObjectMapper mapper      = new ObjectMapper();
      InputStream  inputStream = getClass().getResourceAsStream("/teams-c3.json");
      List<Team> realTeams = mapper.readValue(inputStream, new TypeReference<List<Team>>() {
      });
      assertEquals(36, realTeams.size());
      generator.startDraw(realTeams, 2);
      for (Team team : realTeams) {
        assertEquals(8, team.getAllOpponents().size());
        assertEquals(1, team.getNbHomeOpponentByPot(1));
        assertEquals(1, team.getNbAwayOpponentByPot(1));
        assertEquals(1, team.getNbHomeOpponentByPot(2));
        assertEquals(1, team.getNbAwayOpponentByPot(2));
        assertEquals(1, team.getNbHomeOpponentByPot(3));
        assertEquals(1, team.getNbAwayOpponentByPot(3));
        assertEquals(1, team.getNbHomeOpponentByPot(4));
        assertEquals(1, team.getNbAwayOpponentByPot(4));
      }
    } catch (Exception e) {
      throw new RuntimeException("JSON Error", e);
    }
  }

  @Test
  public void testAddGameDeleteGame() {
    DrawGenerator generator = new DrawGenerator();
    generator.startDraw(teams4x4, 2);

    Team teamA = generator.getGames().get(0).getTeamA();
    Team teamB = generator.getGames().get(0).getTeamB();

    generator.deleteAllGamesBasedOnPots(teamA.getPot(), teamB.getPot());

    assertFalse(teamA.getAllOpponents().contains(teamB));
    assertFalse(teamB.getAllOpponents().contains(teamA));
  }


  @Test
  public void testGetNbOpponentsByPot() {
    DrawGenerator generator = new DrawGenerator();
    assertEquals(0, generator.getNbOpponentByPot(team1A, 1, true));
    generator.startDraw(teams4x4, 2);
    assertEquals(1, generator.getNbOpponentByPot(team1A, 1, true));
    assertEquals(1, generator.getNbOpponentByPot(team1A, 1, false));
  }

}
