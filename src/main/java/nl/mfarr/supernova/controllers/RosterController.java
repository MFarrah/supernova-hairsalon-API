package nl.mfarr.supernova.controllers;

import nl.mfarr.supernova.dtos.RosterRequestDto;
import nl.mfarr.supernova.dtos.RosterResponseDto;
import nl.mfarr.supernova.entities.RosterEntity;
import nl.mfarr.supernova.mappers.RosterMapper;
import nl.mfarr.supernova.services.RosterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rosters")
public class RosterController {

    private final RosterService rosterService;
private final RosterMapper rosterMapper;




        public RosterController(RosterService rosterService, RosterMapper rosterMapper) {
            this.rosterService = rosterService;
            this.rosterMapper = rosterMapper;
        }

        @PostMapping("/create-and-copy")
        public ResponseEntity<RosterResponseDto> createAndCopyRoster(@RequestBody RosterRequestDto requestDto) {
            RosterEntity roster = rosterService.createAndCopyRoster(requestDto.getEmployeeId());
            RosterResponseDto responseDto = rosterMapper.toDto(roster);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        }
    }

