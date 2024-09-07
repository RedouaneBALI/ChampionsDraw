import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Team;
import org.junit.jupiter.api.Test;

public class TeamTest {

  @Test
  public void testGetNbOpponentByPot() {
    Team team = new Team("A", 1);
    team.getOpponents().add(new Team("1B", 1));
    team.getOpponents().add(new Team("1C", 1));
    team.getOpponents().add(new Team("2A", 2));
    team.getOpponents().add(new Team("3A", 3));
    assertEquals(team.getNbOpponentByPot(1), 2);
    assertEquals(team.getNbOpponentByPot(2), 1);
    assertEquals(team.getNbOpponentByPot(3), 1);
    assertEquals(team.getNbOpponentByPot(4), 0);
  }


}
