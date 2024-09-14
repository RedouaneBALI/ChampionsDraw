package io.github.redouanebali.controller;

import io.github.redouanebali.model.Team;
import io.github.redouanebali.service.DrawService;
import io.github.redouanebali.service.TeamService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DrawController {

  private final DrawService drawService;
  private final TeamService teamService;

  public DrawController(DrawService drawService, TeamService teamService) {
    this.drawService = drawService;
    this.teamService = teamService;
  }

  @GetMapping("/")
  public String startDraw(Model model,
                          @RequestParam(defaultValue = "4", required = false) int nbPots,
                          @RequestParam(defaultValue = "2", required = false) int nbGamesPerPot) {
    List<Team> teams      = teamService.getTeams();
    List<Team> drawnTeams = drawService.startDraw(teams, nbPots, nbGamesPerPot);
    model.addAttribute("teams", drawnTeams);
    return "draw-results";
  }
}
