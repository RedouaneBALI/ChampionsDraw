package io.github.redouanebali.controller;

import io.github.redouanebali.model.Team;
import io.github.redouanebali.service.DrawService;
import io.github.redouanebali.service.TeamService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DrawController {

  private final DrawService drawService;
  private final TeamService teamService;

  public DrawController(DrawService drawService, TeamService teamService) {
    this.drawService = drawService;
    this.teamService = teamService;
  }

  @PostMapping("/draw/start")
  public String startDraw(@RequestParam(defaultValue = "2", required = false) int nbGamesPerPot,
                          @RequestBody List<Team> teams, Model model) {
    List<Team> drawnTeams = drawService.startDraw(teams, nbGamesPerPot);
    model.addAttribute("teams", drawnTeams);
    return "draw-results :: resultsFragment";
  }

  @GetMapping("/draw/results")
  public String showDrawResults(Model model) {
    List<Team> teams = (List<Team>) model.asMap().get("teams");
    if (teams == null) {
      teams = new ArrayList<>();
    }
    model.addAttribute("teams", teams);
    return "draw-results";
  }

  @GetMapping("/")
  public String init() {
    return "draw-results";
  }
}
