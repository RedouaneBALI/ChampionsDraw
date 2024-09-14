package io.github.redouanebali.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Game {

  private Team teamA; // home
  private Team teamB; //away

  @Override
  public String toString() {
    return this.teamA + " - " + this.teamB;
  }

}
