package booking.background;

import booking.models.Order;
import booking.models.StatusType;
import booking.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StatusJobHandler {
    @Autowired
    private OrderRepository orderRepository;

    private final Random random = new Random();

    @Scheduled(fixedRate = 5000) // This schedules the method to run every 5000 milliseconds (5 seconds)
    public void processOrders() {
        System.out.println("Processing orders...");

        int pageSize = 5;
        int pageNumber = 0;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Order> ordersToCheck;

        do {
            // Получаем заказы с пагинацией
            ordersToCheck = orderRepository.findByStatus(StatusType.Check, pageable);

            for (Order order : ordersToCheck) {
                try {
                    // Simulate processing delay
                    Thread.sleep(random.nextInt(2000) + 1000);

                    // Randomly change status to success (2) or rejection (3)
                    order.setStatus(random.nextBoolean() ? StatusType.Success : StatusType.Rejection);
                    orderRepository.save(order);

                    System.out.println("Processed order id: " + order.getId() + " new status: " + order.getStatus());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Переходим на следующую страницу
            pageNumber++;
            pageable = PageRequest.of(pageNumber, pageSize);

        } while (!ordersToCheck.isEmpty());
    }
}
