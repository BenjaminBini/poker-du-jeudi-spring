package io.bini.poker.pokerdujeudi.controllers;

import io.bini.poker.pokerdujeudi.dto.SessionDTO;
import io.bini.poker.pokerdujeudi.model.*;
import io.bini.poker.pokerdujeudi.service.place.PlaceService;
import io.bini.poker.pokerdujeudi.service.player.PlayerService;
import io.bini.poker.pokerdujeudi.service.season.SeasonService;
import io.bini.poker.pokerdujeudi.service.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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
        Map<Long, List<Session>> sessionsBySeason = sessions.stream()
                .collect(Collectors.groupingBy(s -> s.getSeason().getSeasonId()));

        model.addAttribute("sessions", sessionsBySeason);
        model.addAttribute("seasons", seasons);
        return "sessions";
    }

    @GetMapping("{sessionId}")
    public String session(Model model, @PathVariable long sessionId) {
        Optional<Session> session = this.sessionService.get(sessionId);
        model.addAttribute("session", session.get());
        return "session";
    }

    @GetMapping("add")
    public String addSession(Model model) {
        List<Player> players = this.playerService.list();
        model.addAttribute("players", players);

        List<Place> places = this.placeService.list();
        model.addAttribute("places", places);

        List<Season> seasons = this.seasonService.list();
        model.addAttribute("seasons", seasons);
        return "add-session";
    }

    @PostMapping("add")
    public String addSession(Model model, @ModelAttribute SessionDTO sessionDTO) {
        Session session = new Session();
        List<PlayerResult> playerResults = new ArrayList<>();
        for (long playerId : sessionDTO.getPlayerIds()) {
            Optional<Player> player = this.playerService.get(playerId);
            if (player.isPresent()) {
                PlayerResult playerResult = new PlayerResult();
                playerResult.setPlayer(player.get());
                playerResult.setSession(session);
                playerResults.add(playerResult);
            }
        }
        session.setPlayerResults(playerResults);
        session.setDate(sessionDTO.getDate());
        Optional<Place> place = this.placeService.get(sessionDTO.getPlaceId());
        if (place.isPresent()) {
            session.setPlace(place.get());
        }

        Optional<Season> season = this.seasonService.get(sessionDTO.getSeasonId());
        if (season.isPresent()) {
            session.setSeason(season.get());
        }
        this.sessionService.save(session);
        return "redirect:/sessions/" + session.getSessionId();
    }

    @GetMapping("delete/{sessionId}")
    public String deletePlayer(@PathVariable Long sessionId) {
        this.sessionService.delete(sessionId);
        return "redirect:/sessions";
    }

}
