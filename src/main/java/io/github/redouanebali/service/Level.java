package io.github.redouanebali.service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

public enum Level {

  C1("C1", "/teams-c1.json"),
  C3("C3", "/teams-c3.json");

  private static final Map<String, Level> LEVELS_BY_ALIAS = Stream.of(values())
                                                                  .collect(Collectors.toMap(Level::getCompetition, level -> level));
  @Getter
  private final        String             competition;
  @Getter
  private final        String             teamsUrl;

  Level(String competition, String teamsUrl) {
    this.competition = competition;
    this.teamsUrl    = teamsUrl;
  }

  public static Level getLevel(String alias) {
    return LEVELS_BY_ALIAS.getOrDefault(alias, null);
  }
}
