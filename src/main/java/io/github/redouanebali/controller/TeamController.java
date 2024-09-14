package io.github.redouanebali.controller;

import io.github.redouanebali.model.Team;
import io.github.redouanebali.service.Level;
import io.github.redouanebali.service.TeamService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {

  private final TeamService teamService;

  public TeamController(TeamService teamService) {
    this.teamService = teamService;
  }

  @GetMapping("/teams/c1")
  public List<Team> getC1Teams() {
    return teamService.getTeams(Level.C1);
  }

  @GetMapping("/teams/c3")
  public List<Team> getC3Teams() {
    return teamService.getTeams(Level.C1);
  }
}