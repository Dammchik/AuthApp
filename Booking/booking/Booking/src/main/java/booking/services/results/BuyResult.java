package booking.services.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyResult {
    private boolean success;
    private String error;
    private long orderId;

    public static BuyResult Success(long orderId) {
        return new BuyResult(true, null, orderId);
    }

    public static BuyResult Failure(String error) {
        return new BuyResult(false, error, -1);
    }

}
