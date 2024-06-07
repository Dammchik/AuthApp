package booking.controllers;

import booking.dtos.requests.BuyTicketRequest;
import booking.security.SecurityManager;
import booking.services.abstractions.OrderService;
import booking.services.models.BuyTicketCommand;
import jakarta.servlet.http.HttpServletRequest;
import org.springdoc.core.service.SecurityService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Controller("ticket")
public class TicketController {

    private final SecurityManager securityManager;
    private final OrderService orderService;

    public TicketController(
            SecurityManager securityManager,
            OrderService orderService) {

        this.securityManager = securityManager;
        this.orderService = orderService;
    }

    @PostMapping("/buy")
    public ResponseEntity buyTicket(@RequestBody BuyTicketRequest req, HttpServletRequest request) {
        var userId = securityManager.AuthenticateUser(request);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        long userIdLong = Long.parseLong(userId);
        var command = BuyTicketCommand.builder()
                .user_id(userIdLong)
                .station_from(req.getStation_from())
                .station_to(req.getStation_to())
                .build();

        var result = orderService.buyTicket(command);

        if (!result.isSuccess())
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("status/{id}")
    public ResponseEntity checkStatus(@PathVariable long id, HttpServletRequest request) {
        var userId = securityManager.AuthenticateUser(request);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var result = orderService.checkStatus(id);

        if (!result.isSuccess())
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
