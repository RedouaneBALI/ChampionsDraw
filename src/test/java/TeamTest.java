import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.redouanebali.model.Team;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class TeamTest {

  @Test
  public void testGetNbOpponentByPot() {
    Team team = new Team("A", 1);
    team.addOpponent(new Team("1B", 1, Locale.FRANCE), true);
    team.addOpponent(new Team("1C", 1, Locale.UK), false);
    team.addOpponent(new Team("2A", 2, Locale.ITALY), true);
    team.addOpponent(new Team("2B", 2, Locale.ITALY), false);
    team.addOpponent(new Team("3C", 3, Locale.FRANCE), true);
    team.addOpponent(new Team("3A", 3, Locale.FRANCE), false);
    // opponents
    assertEquals(3, team.getHomeOpponents().size());
    assertEquals(3, team.getAwayOpponents().size());
    assertEquals(6, team.getAllOpponents().size());
    assertEquals(3, team.getNbOpponentBycountry(Locale.FRANCE));
    assertEquals(2, team.getNbOpponentBycountry(Locale.ITALY));
    assertEquals(1, team.getNbOpponentBycountry(Locale.UK));
    // nb opponents by pot
    assertEquals(1, team.getNbHomeOpponentByPot(1));
    assertEquals(1, team.getNbAwayOpponentByPot(1));
    assertEquals(1, team.getNbHomeOpponentByPot(2));
    assertEquals(1, team.getNbAwayOpponentByPot(2));
    assertEquals(1, team.getNbHomeOpponentByPot(3));
    assertEquals(1, team.getNbAwayOpponentByPot(3));

  }


}
