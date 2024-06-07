package booking.services.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusResult {
    private boolean isSuccess;
    private String errorMessage;
    private String status;
    private long toStationId;
    private long fromStationId;

    public static StatusResult Success(String status, long toStationId, long fromStationId) {
        return new StatusResult(true, null, status, toStationId, fromStationId);
    }

    public static StatusResult Failure(String error) {
        return new StatusResult(false, error, null, -1, -1);
    }
}
