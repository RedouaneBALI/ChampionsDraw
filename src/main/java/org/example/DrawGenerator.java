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
            while (team.getNbHomeOpponentByPot(pot) < nbGamesPerPot / 2) {
              Team opponent = getRandomTeam(team, pot, nbGamesPerPot, false);
              addGame(team, opponent); //home
            }
            while (team.getNbAwayOpponentByPot(pot) < nbGamesPerPot / 2) {
              Team opponent = getRandomTeam(team, pot, nbGamesPerPot, true);
              addGame(opponent, team); //away
            }
          }
        }
      }
    }
    printDraw();
  }

  private void printDraw() {

    // display draw
    for (Team team : teams) {
      team.sortTeamDrawByPot();
      System.out.println(team.getName() + " : " + team.getHomeOpponents() + " " + team.getAwayOpponents());
    }
    System.out.println();
    // display games
    for (Game game : games) {
      System.out.println(game);
    }
  }

  public boolean allOpponentsDefined(int expectedGamesCount) {
    return teams.stream().allMatch(team -> team.hasEnoughOpponents(expectedGamesCount));
  }

  public int getNbOpponentByPot(Team team, int pot, boolean isHome) {
    int count = 0;
    for (Game game : games) {
      if (isHome && game.getTeamA() == team && game.getTeamB().getPot() == pot
          || !isHome && game.getTeamB() == team && game.getTeamA().getPot() == pot) {
        count++;
      }
    }
    return count;
  }

  public void addGame(Team homeTeam, Team awayTeam) {
    homeTeam.addOpponent(awayTeam, true);
    //  awayTeam.addOpponent(homeTeam, false);
    games.add(new Game(homeTeam, awayTeam));
  }

  public void deleteOpponents(Game game) {
    game.getTeamA().getHomeOpponents().removeIf(t -> t.equals(game.getTeamB()));
    game.getTeamA().getAwayOpponents().removeIf(t -> t.equals(game.getTeamB()));
    game.getTeamB().getHomeOpponents().removeIf(t -> t.equals(game.getTeamA()));
    game.getTeamB().getAwayOpponents().removeIf(t -> t.equals(game.getTeamA()));
  }

  public Team getRandomTeam(Team team, int pot, int nbGamesPerPot, boolean opponentHome) {
    List<Team> potentialOpponents = getPotentialOpponents(team, pot, nbGamesPerPot, opponentHome);

    if (potentialOpponents.size() == 0) {
      deleteAllGamesBasedOnPots(team.getPot(), pot);
      return getRandomTeam(team, pot, nbGamesPerPot, opponentHome);
    }
    Random random      = new Random();
    int    randomIndex = random.nextInt(potentialOpponents.size());
    return potentialOpponents.get(randomIndex);
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

  public List<Team> getPotentialOpponents(Team team, int pot, int nbGamesPerPot, boolean opponentHome) {

    return teams.stream()
                .filter(t -> !t.getName().equals(team.getName())) // other teams
                .filter(t -> t.getPot() == pot) // right pot
                .filter(t -> !team.getAllOpponents().contains(t)) // not already opponent
                .filter(t -> opponentHome && t.getNbHomeOpponentByPot(team.getPot()) < nbGamesPerPot / 2
                             || !opponentHome && t.getNbAwayOpponentByPot(team.getPot()) < nbGamesPerPot / 2)
                .filter(t -> t.getCountry() == null || !t.getCountry()
                                                         .getCountry()
                                                         .equals(team.getCountry().getCountry())) // from another country
                .filter(t -> t.getCountry() == null
                             || t.getNbOpponentBycountry(team.getCountry())
                                < MAX_OPPONENTS_SAME_COUNTRY) // opponent didn't play too much same country
                .filter(t -> t.getCountry() == null
                             || team.getNbOpponentBycountry(t.getCountry()) < MAX_OPPONENTS_SAME_COUNTRY) // team didn't play too much same country
                .toList();
  }

}
