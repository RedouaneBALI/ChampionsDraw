package org.example;

import java.util.List;
import java.util.Locale;

public class Main {

  public static void main(String[] args) {
    // pot 1
    Team realMadrid     = new Team("Real Madrid", 1, new Locale("", "ES"));
    Team manchesterCity = new Team("Manchester City", 1, new Locale("", "GB"));
    Team bayernMunich   = new Team("Bayern Munich", 1, new Locale("", "DE"));
    Team psg            = new Team("Paris Saint-Germain", 1, new Locale("", "FR"));
    Team liverpool      = new Team("Liverpool", 1, new Locale("", "GB"));
    Team inter          = new Team("Inter", 1, new Locale("", "IT"));
    Team dortmund       = new Team("Dortmund", 1, new Locale("", "DE"));
    Team leipzig        = new Team("Leipzig", 1, new Locale("", "DE"));
    Team barcelona      = new Team("Barcelona", 1, new Locale("", "ES"));
    // pot 2
    Team leverkusen      = new Team("Leverkusen", 2, new Locale("", "DE"));
    Team atleticoMadrid  = new Team("Atletico de Madrid", 2, new Locale("", "ES"));
    Team atalanta        = new Team("Atalanta", 2, new Locale("", "IT"));
    Team juventus        = new Team("Juventus", 2, new Locale("", "IT"));
    Team benfica         = new Team("Benfica", 2, new Locale("", "PT"));
    Team arsenal         = new Team("Arsenal", 2, new Locale("", "GB"));
    Team clubBrugge      = new Team("Club Bruges", 2, new Locale("", "BE"));
    Team shakhtarDonetsk = new Team("Shakhtar Donetsk", 2, new Locale("", "UA"));
    Team acMilan         = new Team("AC Milan", 2, new Locale("", "IT"));
    // pot 3
    Team feyenoord    = new Team("Feyenoord", 3, new Locale("", "NL"));
    Team sporting     = new Team("Sporting", 3, new Locale("", "PT"));
    Team psvEindhoven = new Team("PSV Eindhoven", 3, new Locale("", "NL"));
    Team gnkDinamo    = new Team("GNK Dinamo", 3, new Locale("", "HR"));
    Team salzburg     = new Team("Salzburg", 3, new Locale("", "AT"));
    Team lille        = new Team("Lille", 3, new Locale("", "FR"));
    Team etoileRouge  = new Team("Etoile Rouge", 3, new Locale("", "RS"));
    Team youngBoys    = new Team("Young Boys", 3, new Locale("", "CH"));
    Team celtic       = new Team("Celtic", 3, new Locale("", "GB"));
    // pot 4
    Team slovanBratislava = new Team("Slovan Bratislava", 4, new Locale("", "SK"));
    Team monaco           = new Team("Monaco", 4, new Locale("", "FR"));
    Team spartaPrague     = new Team("Sparta Prague", 4, new Locale("", "CZ"));
    Team astonVilla       = new Team("Aston Villa", 4, new Locale("", "GB"));
    Team bologne          = new Team("Bologne", 4, new Locale("", "IT"));
    Team gerona           = new Team("Gerona", 4, new Locale("", "ES"));
    Team stuttgart        = new Team("Stuttgart", 4, new Locale("", "DE"));
    Team sturmGraz        = new Team("Sturm Graz", 4, new Locale("", "AT"));
    Team brest            = new Team("Brest", 4, new Locale("", "FR"));
    
    List<Team> allTeams = List.of(
        realMadrid, manchesterCity, bayernMunich, psg, liverpool, inter, dortmund, leipzig, barcelona,
        leverkusen, atleticoMadrid, atalanta, juventus, benfica, arsenal, clubBrugge, shakhtarDonetsk, acMilan,
        feyenoord, sporting, psvEindhoven, gnkDinamo, salzburg, lille, etoileRouge, youngBoys, celtic,
        slovanBratislava, monaco, spartaPrague, astonVilla, bologne, gerona, stuttgart, sturmGraz, brest
    );
    DrawGenerator generator = new DrawGenerator(allTeams);
    generator.startDraw(4, 2);
  }


}