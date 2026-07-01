package br.com.seuespacounb.turing.controller;

import br.com.seuespacounb.turing.dto.response.GoogleCalendarResponse;
import br.com.seuespacounb.turing.exception.BadRequestException;
import br.com.seuespacounb.turing.exception.NotFoundException;
import br.com.seuespacounb.turing.service.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/turing")
@RequiredArgsConstructor
public class GoogleCalendarController {
    private final GoogleCalendarService calendarService;

    @GetMapping("/agenda/evento/{id}")
    public ResponseEntity<GoogleCalendarResponse> pegarUrlGoogleCalendar(
            @PathVariable Long id)throws NotFoundException, BadRequestException{
        return ResponseEntity.ok(calendarService.retornarUrlGoogleCalendar(id));
    }
}
