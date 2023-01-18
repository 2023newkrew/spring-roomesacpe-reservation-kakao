package nextstep.mapper;

/**
 *
 * @param <T> Request type
 * @param <E> Entity type
 * @param <R> Response type
 */
public interface EntityMapper<T, E, R> extends RequestToEntityMapper<T, E>, EntityToResponseMapper<E, R> {
}
