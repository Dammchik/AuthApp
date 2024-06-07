package booking.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    //- `from_station_id` (integer, foreign key): идентификатор станции отправления
    //- `to_station_id` (integer, foreign key): идентификатор станции назначения
    //- `status` (integer): текущий статус заказа (1 – check, 2 – success, 3 – rejection)

    @Column(name = "from_station_id", nullable = false)
    private long fromStation;

    @Column(name = "to_station_id", nullable = false)
    private long toStation;

    @Column(name = "status", nullable = false)
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;
}


