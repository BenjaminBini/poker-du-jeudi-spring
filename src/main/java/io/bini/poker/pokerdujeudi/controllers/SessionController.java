package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.model.Place;
import io.bini.poker.pokerdujeudi.model.Player;
import io.bini.poker.pokerdujeudi.model.Season;
import io.bini.poker.pokerdujeudi.model.Session;
import io.bini.poker.pokerdujeudi.service.place.PlaceService;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.season.SeasonService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sessions")
public class SessionController {
    private final SeasonService seasonService;
    private final SessionService sessionService;
    private final PlayerService playerService;
    private final PlaceService placeService;

    @Autowired
    public SessionController(SeasonService seasonService, SessionService sessionService, PlayerService playerService, PlaceService placeService) {
        this.seasonService = seasonService;
        this.sessionService = sessionService;
        this.playerService = playerService;
        this.placeService = placeService;
    }

    @GetMapping("")
    public String sessions(Model model) {
        List<Season> seasons = this.seasonService.list();
        seasons.sort(Comparator.comparingInt(Season::getName).reversed());

        List<Session> sessions = this.sessionService.list();
        Map<Integer, List<Session>> sessionsBySeason = sessions.stream()
                .collect(Collectors.groupingBy(Session::getSeasonId));

        model.addAttribute("sessions", sessionsBySeason);
        model.addAttribute("seasons", seasons);
        return "sessions";
    }

    @GetMapping("add")
    public String addSession(Model model) {
        List<Player> players = this.playerService.list();
        model.addAttribute("players", players);

        List<Place> places = this.placeService.list();
        model.addAttribute("places", places);
        return "add-session";
    }

}
