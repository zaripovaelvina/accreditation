package project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.dto.*;
import project.manager.MemberManager;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberManager manager;

    @RequestMapping("/membersOfEvent")
    public MembersOfEventResponseDTO membersOfEvent(@RequestParam long eventId) {
        return manager.membersOfEvent(eventId);    }

    @RequestMapping("/getWinners")
    public WinnersOfEventResponseDTO getWinners(@RequestParam long eventId, @RequestParam int winner) {
        return manager.getWinners(eventId, winner);    }

    @RequestMapping("/memberCount")
    public MembersCountResponseDTO memberCount() {
        return manager.memberCount();
    }
}


