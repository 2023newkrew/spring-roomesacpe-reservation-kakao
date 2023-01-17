package web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.dto.ReservationRequestDto;
import web.dto.ReservationResponseDto;
import web.dto.ThemeRequestDto;
import web.dto.ThemeResponseDto;
import web.service.RoomEscapeService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class RoomEscapeController {

    private final RoomEscapeService roomEscapeService;

    public RoomEscapeController(RoomEscapeService roomEscapeService) {
        this.roomEscapeService = roomEscapeService;
    }

    @PostMapping("/reservations")
    public ResponseEntity<Void> reservation(@RequestBody @Valid ReservationRequestDto requestDto) {
        try {
            long createdId = roomEscapeService.reservation(requestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .location(URI.create("/reservations/" + createdId))
                    .build();

        } catch (IllegalArgumentException E) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/reservations/{reservationId}")
    public ResponseEntity<ReservationResponseDto> reservation(@PathVariable long reservationId) {
        ReservationResponseDto responseDto = roomEscapeService.findReservationById(reservationId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDto);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<Void> cancelReservation(@PathVariable long reservationId) {
        roomEscapeService.cancelReservation(reservationId);
        return ResponseEntity.noContent()
                .build();
    }

    @PostMapping("/themes")
    public ResponseEntity<Void> createTheme(@RequestBody @Valid ThemeRequestDto requestDto) {
        Long createdId = roomEscapeService.createTheme(requestDto);
        if (createdId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/themes/" + createdId))
                .build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponseDto>> getThemes() {
        List<ThemeResponseDto> themeResponseDtos = roomEscapeService.getThemes();

        themeResponseDtos.forEach(a -> System.out.println("id: " + a.getId() + "   name: " + a.getName()));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(themeResponseDtos);
    }

    @DeleteMapping("/themes/{themeId}")
    public ResponseEntity<Void> deleteTheme(@PathVariable long themeId) {
        roomEscapeService.deleteTheme(themeId);
        return ResponseEntity.noContent().build();
    }
}
