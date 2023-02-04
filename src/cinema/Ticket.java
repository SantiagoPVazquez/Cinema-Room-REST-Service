package cinema;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Ticket {
    public String token;
    public Seat ticket;
    private Seat returned_ticket;
    public Ticket(String token, Seat ticket) {
        this.token = token;
        this.ticket = ticket;
    }
    public int getPrice(){
        return ticket.getPrice();
    }
    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = ticket;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seat returnTicket(String token) {
        if (this.token == token){
            returned_ticket = ticket;
            returned_ticket.setAvailable(true);
            return returned_ticket;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Wrong token!");
    }

}