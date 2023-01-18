package nextstep.mapper;

public interface RequestToEntityMapper<R, E> {
    E requestToEntity(R request);
}
