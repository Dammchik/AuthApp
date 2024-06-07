package booking.services;

import booking.models.Order;
import booking.models.StatusType;
import booking.repositories.OrderRepository;
import booking.repositories.StationRepository;
import booking.services.abstractions.OrderService;
import booking.services.models.BuyTicketCommand;
import booking.services.results.BuyResult;
import booking.services.results.StatusResult;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final StationRepository stationRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository,
            StationRepository stationRepository) {

        this.orderRepository = orderRepository;
        this.stationRepository = stationRepository;
    }

    @Override
    public BuyResult buyTicket(BuyTicketCommand req) {
        var validationRes = validateStation(req.getStation_to(), req.getStation_from());
        if (!validationRes.isSuccess())
            return BuyResult.Failure(validationRes.getError());

        var order = Order.builder()
                .fromStation(req.getStation_from())
                .toStation(req.getStation_to())
                .userId(req.getUser_id())
                .status(StatusType.Check)
                .build();

        var entity = orderRepository.save(order);

        return BuyResult.Success(entity.getId());
    }

    @Override
    public StatusResult checkStatus(long orderId) {
        var entity = orderRepository.findById(orderId);
        return entity.map(
                order -> StatusResult.Success(order.getStatus(), order.getToStation(), order.getFromStation()))
                .orElseGet(() -> StatusResult.Failure("Order not found"));

    }

    private BuyResult validateStation(long to, long from) {
        var toEntity = stationRepository.findById(to);
        if (toEntity.isEmpty())
            return BuyResult.Failure("toStation not found");

        var fromEntity = stationRepository.findById(from);
        if (fromEntity.isEmpty())
            return BuyResult.Failure("fromStation not found");

        return BuyResult.Success(-1);
    }
}
