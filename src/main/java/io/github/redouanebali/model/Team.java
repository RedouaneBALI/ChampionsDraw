package io.github.redouanebali.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class Team {

  String name;
  int    pot;
  @JsonIgnore
  List<Team> homeOpponents = new ArrayList<>();
  // playing home
  // @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonIgnore
  List<Team> awayOpponents = new ArrayList<>();
  // playing away
  Locale country;
  private String logoUrl;

  public Team(String name, int pot, Locale country, String logoUrl) {
    this.name          = name;
    this.pot           = pot;
    this.homeOpponents = new ArrayList<>();
    this.awayOpponents = new ArrayList<>();
    this.country       = country;
    this.logoUrl       = logoUrl;
  }

  public Team(String name, int pot) {
    this(name, pot, null, "");
  }

  public Team(String name, int pot, Locale country) {
    this(name, pot, country, "");
  }

  // @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonIgnore
  public List<Team> getAllOpponents() {
    List<Team> allTeams = new ArrayList<>(homeOpponents.size() + awayOpponents.size());
    allTeams.addAll(homeOpponents);
    allTeams.addAll(awayOpponents);
    allTeams.sort(Comparator.comparingInt(Team::getPot));
    return Collections.unmodifiableList(allTeams);
  }

  public List<String> getAllOpponentsNames() {
    return getAllOpponents().stream().map(t -> t.getName()).toList();
  }

  public int getNbHomeOpponentByPot(int pot) {
    return (int) homeOpponents.stream().filter(t -> t.getPot() == pot).count();
  }

  public int getNbAwayOpponentByPot(int pot) {
    return (int) awayOpponents.stream().filter(t -> t.getPot() == pot).count();
  }

  public int getNbOpponentBycountry(Locale country) {
    return (int) getAllOpponents().stream().filter(t -> t.getCountry().equals(country)).count();
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
