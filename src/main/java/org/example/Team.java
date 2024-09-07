package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Team {

  String     name;
  int        pot;
  List<Team> opponents;
  Locale     country;

  public Team(String name, int pot) {
    this.name      = name;
    this.pot       = pot;
    this.opponents = new ArrayList<>();
  }

  public Team(String name, int pot, Locale country) {
    this.name      = name;
    this.pot       = pot;
    this.country   = country;
    this.opponents = new ArrayList<>();
  }

  public int getNbOpponentByPot(int pot) {
    return (int) opponents.stream().filter(t -> t.getPot() == pot).count();
  }

  public int getNbOpponentBycountry(Locale country) {
    return (int) opponents.stream().filter(t -> t.getCountry().getCountry().equals(country.getCountry())).count();
  }

  public void addOpponent(Team opponent) {
    this.opponents.add(opponent);
  }

  public boolean hasEnoughOpponents(int expectedGamesCount) {
    return this.getOpponents().size() == expectedGamesCount;
  }

  public void sortTeamDrawByPot() {
    opponents.sort(Comparator.comparingInt(Team::getPot));
  }

  @Override
  public String toString() {
    return this.name;
  }
}
