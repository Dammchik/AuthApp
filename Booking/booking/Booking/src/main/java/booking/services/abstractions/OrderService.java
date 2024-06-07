package booking.services.abstractions;

import booking.dtos.requests.BuyTicketRequest;
import booking.services.models.BuyTicketCommand;
import booking.services.results.BuyResult;
import booking.services.results.StatusResult;

public interface OrderService {
    BuyResult buyTicket(BuyTicketCommand req);
    StatusResult checkStatus(long orderId);
}
