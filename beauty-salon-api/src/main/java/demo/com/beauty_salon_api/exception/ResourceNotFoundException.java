package demo.com.beauty_salon_api.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Object resourceId) {
        super(String.format("%s с id=%s не найден(а)", resourceName, resourceId));
    }
}