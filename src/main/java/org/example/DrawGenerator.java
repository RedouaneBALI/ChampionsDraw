package org.example;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter
public class DrawGenerator {

  private final List<Team> teams;
  private final List<Game> games                      = new ArrayList<>();
  private final int        MAX_OPPONENTS_SAME_COUNTRY = 2;

  public DrawGenerator(List<Team> teams) {
    this.teams = teams;
  }

  public void startDraw(int nbPots, int nbGamesPerPot) {
    while (!allOpponentsDefined(nbPots * nbGamesPerPot)) {
      for (Team team : teams) {
        if (!team.hasEnoughOpponents(nbPots * nbGamesPerPot)) {
          for (int pot = 1; pot <= nbPots; pot++) {
            while (team.getNbOpponentByPot(pot) < nbGamesPerPot) {
              Team opponent = getRandomTeam(team, pot, nbGamesPerPot);
              if (team.getNbOpponentByPot(pot) % 2 == 0) {
                addGame(team, opponent); //home
              } else {
                addGame(opponent, team); //away
              }
            }
          }
        }
      }
    }
    for (Team team : teams) {
      team.sortTeamDrawByPot();
      System.out.println(team.getName() + " : " + team.getOpponents());
    }
    System.out.println();
    for (Game game : games) {
      System.out.println(game);
    }
  }

  public boolean allOpponentsDefined(int expectedGamesCount) {
    return teams.stream().allMatch(team -> team.hasEnoughOpponents(expectedGamesCount));
  }

  public void addGame(Team team, Team opponent) {
    team.addOpponent(opponent);
    opponent.addOpponent(team);
    games.add(new Game(team, opponent));
  }

  public void deleteOpponents(Game game) {
    game.getTeamA().getOpponents().removeIf(t -> t.equals(game.getTeamB()));
    game.getTeamB().getOpponents().removeIf(t -> t.equals(game.getTeamA()));
  }

  public void deleteAllGamesBasedOnPots(int teamPot, int opponentPot) {
    Iterator<Game> iterator = games.iterator();

    System.out.println("rolling back pots " + teamPot + "&" + opponentPot + "...");
    while (iterator.hasNext()) {
      Game game = iterator.next();
      if (areTeamsFromPots(game, teamPot, opponentPot)) {
        deleteOpponents(game);
        iterator.remove();
      }
    }

  }

  private boolean areTeamsFromPots(Game game, int pot1, int pot2) {
    int teamAPot = game.getTeamA().getPot();
    int teamBPot = game.getTeamB().getPot();

    return (teamAPot == pot1 && teamBPot == pot2) || (teamAPot == pot2 && teamBPot == pot1);
  }

  public Team getRandomTeam(Team team, int pot, int nbGamesPerPot) {
    List<Team> potentialOpponents = getPotentialOpponents(team, pot, nbGamesPerPot);
    if (potentialOpponents.size() == 0) {
      deleteAllGamesBasedOnPots(team.getPot(), pot);
      return getRandomTeam(team, pot, nbGamesPerPot);
    }
    Random random      = new Random();
    int    randomIndex = random.nextInt(potentialOpponents.size());
    return potentialOpponents.get(randomIndex);
  }

  public List<Team> getPotentialOpponents(Team team, int pot, int nbGamesPerPot) {

    return teams.stream()
                .filter(t -> !t.getName().equals(team.getName())) // other teams
                .filter(t -> t.getPot() == pot) // right pot
                .filter(t -> !team.getOpponents().contains(t)) // not already opponent
                .filter(t -> t.getNbOpponentByPot(team.getPot()) < nbGamesPerPot) // didn't already play the quantity of needed games
                .filter(t -> !t.getCountry().getCountry().equals(team.getCountry().getCountry())) // from another country
                .filter(t -> t.getNbOpponentBycountry(team.getCountry()) < MAX_OPPONENTS_SAME_COUNTRY)
                .filter(t -> team.getNbOpponentBycountry(t.getCountry()) < MAX_OPPONENTS_SAME_COUNTRY)
                .toList();
  }

}
