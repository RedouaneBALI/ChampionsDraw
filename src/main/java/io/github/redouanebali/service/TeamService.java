package io.github.redouanebali.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.redouanebali.model.Team;
import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TeamService {

  public List<Team> getTeams(Level level) {
    try {
      ObjectMapper mapper      = new ObjectMapper();
      InputStream  inputStream = getClass().getResourceAsStream(level.getTeamsUrl());
      return mapper.readValue(inputStream, new TypeReference<List<Team>>() {
      });
    } catch (Exception e) {
      throw new RuntimeException("Erreur lors de la lecture du fichier JSON", e);
    }
  }
}
