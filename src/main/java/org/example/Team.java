package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import lombok.Getter;


@Getter
public class Team {

  String     name;
  int        pot;
  List<Team> homeOpponents; // playing home
  List<Team> awayOpponents; // playing away
  Locale     country;

  public Team(String name, int pot) {
    this.name          = name;
    this.pot           = pot;
    this.homeOpponents = new ArrayList<>();
    this.awayOpponents = new ArrayList<>();
  }

  public Team(String name, int pot, Locale country) {
    this(name, pot);
    this.country = country;
  }

  public List<Team> getAllOpponents() {
    List<Team> allTeams = new ArrayList<>(homeOpponents.size() + awayOpponents.size());
    allTeams.addAll(homeOpponents);
    allTeams.addAll(awayOpponents);
    return Collections.unmodifiableList(allTeams); // Optional: to make it read-only
  }

  public int getNbHomeOpponentByPot(int pot) {
    return (int) homeOpponents.stream().filter(t -> t.getPot() == pot).count();
  }

  public int getNbAwayOpponentByPot(int pot) {
    return (int) awayOpponents.stream().filter(t -> t.getPot() == pot).count();
  }

  public int getNbOpponentBycountry(Locale country) {
    return (int) getAllOpponents().stream().filter(t -> t.getCountry().getCountry().equals(country.getCountry())).count();
  }

  public void addOpponent(Team opponent, boolean isHome) {
    if (isHome) {
      this.homeOpponents.add(opponent);
      opponent.getAwayOpponents().add(this);
    } else {
      this.awayOpponents.add(opponent);
      opponent.getHomeOpponents().add(this);
    }
  }

  public boolean hasEnoughOpponents(int expectedGamesCount) {
    return this.getAllOpponents().size() == expectedGamesCount;
  }

  public void sortTeamDrawByPot() {
    homeOpponents.sort(Comparator.comparingInt(Team::getPot));
    awayOpponents.sort(Comparator.comparingInt(Team::getPot));
  }

  @Override
  public String toString() {
    return this.name;
  }
}
