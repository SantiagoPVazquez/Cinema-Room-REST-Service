package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
public class CinemaController {
    private Seats seats = new Seats();
    private Ticket ticket;
    private List<Ticket> soldTickets;
    private int totalIncome = 0;


    @GetMapping("/seats")
    public Seats getSeats(){
        return seats;
    }

    @PostMapping("/purchase")
    public Ticket purchaseSeat(@RequestBody int row, int column){
        String uuid = UUID.randomUUID().toString();
        ticket = new Ticket(uuid, seats.purchaseSeat(row, column));
        totalIncome += ticket.getPrice();
        soldTickets.add(ticket);
        return ticket;
    }

    @PostMapping("/return")
    public Seat returnTicket(@RequestBody String token) {
        Seat return_ticket = ticket.returnTicket(token);
        seats.getAvailableSeats();
        totalIncome += ticket.getPrice();
        soldTickets.remove(ticket);
        return return_ticket;
    }

    @PostMapping("/stats")
    public Stats getStats(@RequestBody String password) {
        if (password.equals("super_secret")) {
            Stats stats = new Stats(seats.getAvailableSeats().size(), soldTickets.size());
            stats.setCurrent_income(totalIncome);
            return stats;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "The password is wrong!");
    }
}
