package io.github.redouanebali.service;

import io.github.redouanebali.model.DrawGenerator;
import io.github.redouanebali.model.Team;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DrawService {

  public List<Team> startDraw(List<Team> teams, int nbPots, int nbGamesPerPot) {
    DrawGenerator drawGenerator = new DrawGenerator();
    return drawGenerator.startDraw(teams, nbPots, nbGamesPerPot);
  }

}