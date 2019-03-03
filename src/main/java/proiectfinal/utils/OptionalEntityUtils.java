package proiectfinal.utils;

import java.util.Optional;

public class OptionalEntityUtils<T> {

    public T getEntityOrNull(Optional<T> optionalEntity) {
        return optionalEntity.orElse(null);
    }

    public T getEntityOrException(Optional<T> optionalEntity, Exception exception) throws Exception {
        if (optionalEntity != null && optionalEntity.isPresent()) {
            return optionalEntity.get();
        } else {
            throw exception;
        }
    }
}
