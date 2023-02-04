package cinema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class Seats {
    @JsonProperty
    public final int TOTAL_ROWS = 9;
    @JsonProperty
    public final int TOTAL_COLUMNS = 9;
    @JsonProperty
    public List<Seat> available_seats = new ArrayList<>();

    public int price;
    public Seats() {
        for (int i = 1; i <= TOTAL_ROWS; i++){
            if (i <= 4) {
                price = 10;
            } else {
                price = 8;
            }
            for (int j = 1; j <= TOTAL_COLUMNS; j++) {
                available_seats.add(new Seat(i,j,price,true));
            }
        }
    }

    public Seat getSeat(int row, int column){
        for (Seat seat : available_seats) {
            if (seat.getRow() == row && seat.getColumn() == column) {
                return seat;
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The number of a row or a column is out of bounds!");
    }

    public List<Seat> getAvailableSeats() {
        List<Seat> seatsList = new ArrayList<>();
        for (Seat seat : available_seats) {
            if (seat.isAvailable()) {
                seatsList.add(seat);
            }
        }
        available_seats = seatsList;
        return available_seats;
    }

    public Seat purchaseSeat(int row, int column) {
        Seat currentSeat;
        if (getSeat(row, column).isAvailable()) {
            currentSeat = getSeat(row, column);
            currentSeat.setAvailable(false);
            getAvailableSeats();
            return currentSeat;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "The ticket has been already purchased!");
    }

}

class Seat {
    public int row;
    public int column;
    public int price;
    @JsonIgnore
    private boolean available;
    public Seat(int row, int column, int price, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.available = isAvailable;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
