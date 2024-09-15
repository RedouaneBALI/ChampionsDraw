package io.github.redouanebali.controller;

import io.github.redouanebali.model.Team;
import io.github.redouanebali.service.Level;
import io.github.redouanebali.service.TeamService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

  private final TeamService teamService;

  public TeamController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping("/teams")
  public List<Team> getTeams() {
    List<Team> allTeams = new ArrayList<>(teamService.getTeams(Level.C1).size() + teamService.getTeams(Level.C3).size());
    allTeams.addAll(teamService.getTeams(Level.C1));
    allTeams.addAll(teamService.getTeams(Level.C3));
    return Collections.unmodifiableList(allTeams);
  }

  @GetMapping("/teams/C1")
  public List<Team> getC1Teams() {
    return teamService.getTeams(Level.C1);
  }

  @GetMapping("/teams/C3")
  public List<Team> getC3Teams() {
    return teamService.getTeams(Level.C3);
  }
}