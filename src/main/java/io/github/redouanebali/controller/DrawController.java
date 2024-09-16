package io.github.redouanebali.controller;

import io.github.redouanebali.model.Team;
import io.github.redouanebali.service.DrawService;
import io.github.redouanebali.service.TeamService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DrawController {

  private final DrawService drawService;
  private final TeamService teamService;

  public DrawController(DrawService drawService, TeamService teamService) {
    this.drawService = drawService;
    this.teamService = teamService;
  }

  @PostMapping("/draw/start")
  @ResponseBody
  public List<Team> startDraw(@RequestParam(defaultValue = "2", required = false) int nbGamesPerPot,
                              @RequestBody List<Team> teams) {
    return drawService.startDraw(teams, nbGamesPerPot);
  }

  @GetMapping("/draw-results")
  public String showDrawResults(Model model) {
    return "draw-results";
  }

  @GetMapping("/")
  public String init() {
    return "draw-results";
  }
}
