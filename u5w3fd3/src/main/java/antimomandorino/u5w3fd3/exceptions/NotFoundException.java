package antimomandorino.u5w3fd3.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Il record con id " + String.valueOf(id) + " non è stato trovato!");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
