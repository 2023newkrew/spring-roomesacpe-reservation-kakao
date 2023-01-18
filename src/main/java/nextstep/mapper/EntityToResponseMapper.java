package nextstep.mapper;

public interface EntityToResponseMapper<E, R> {
    R entityToResponse(E entity);
}
