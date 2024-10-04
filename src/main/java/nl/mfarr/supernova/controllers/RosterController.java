package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.services.RosterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rosters")
public class RosterController {

    private final RosterService rosterService;
private final RosterMapper rosterMapper;



@Autowired
        public RosterController(RosterService rosterService, RosterMapper rosterMapper) {
            this.rosterService = rosterService;
            this.rosterMapper = rosterMapper;
        }


    }

