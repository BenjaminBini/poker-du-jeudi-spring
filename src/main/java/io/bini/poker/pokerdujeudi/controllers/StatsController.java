package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.model.Season;
import io.bini.poker.pokerdujeudi.service.place.PlaceService;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.result.PlayerResultService;
import io.bini.poker.pokerdujeudi.service.season.SeasonService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatsController {
    private final SeasonService seasonService;
    private final SessionService sessionService;
    private final PlayerService playerService;
    private final PlaceService placeService;
    private final PlayerResultService playerResultService;

    @Autowired
    public StatsController(SeasonService seasonService, SessionService sessionService, PlayerService playerService, PlaceService placeService, PlayerResultService playerResultService) {
        this.seasonService = seasonService;
        this.sessionService = sessionService;
        this.playerService = playerService;
        this.placeService = placeService;
        this.playerResultService = playerResultService;
    }

    @GetMapping("")
    public String stats(Model model) {;
        List<Season> seasons = this.seasonService.list();
        seasons.sort(Comparator.comparingInt(Season::getYear).reversed());
        model.addAttribute("seasons", seasons);
        model.addAttribute("active", "stats");
        return "stats";
    }


}
