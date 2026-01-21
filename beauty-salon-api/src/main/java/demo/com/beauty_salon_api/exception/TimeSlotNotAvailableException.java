package demo.com.beauty_salon_api.exception;
 
import java.time.LocalDateTime;

public class TimeSlotNotAvailableException extends RuntimeException {
    public TimeSlotNotAvailableException(Long masterId, LocalDateTime startTime) {
        super(String.format("Мастер с id=%s занят в %s", masterId, startTime));
    }
}