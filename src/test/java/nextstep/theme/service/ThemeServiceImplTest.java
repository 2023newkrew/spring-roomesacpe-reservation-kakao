package nextstep.theme.service;

import nextstep.etc.exception.ErrorMessage;
import nextstep.etc.exception.ThemeConflictException;
import nextstep.theme.dto.ThemeRequest;
import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.mapper.ThemeMapper;
import nextstep.theme.repository.ThemeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.List;

class ThemeServiceImplTest {

    ThemeRepository repository;

    ThemeService service;


    @BeforeEach
    void setUp() {
        this.repository = new FakeThemeRepository();
        this.service = new ThemeServiceImpl(repository, Mappers.getMapper(ThemeMapper.class));
    }

    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class create {

        @DisplayName("생성 성공")
        @ParameterizedTest
        @MethodSource
        void should_returnResponse_when_givenRequest(ThemeRequest request) {
            var actual = service.create(request);

            Assertions.assertThat(actual)
                    .extracting(
                            ThemeResponse::getId,
                            ThemeResponse::getName,
                            ThemeResponse::getDesc,
                            ThemeResponse::getPrice
                    )
                    .contains(
                            1L,
                            request.getName(),
                            request.getDesc(),
                            request.getPrice()
                    );
        }

        List<Arguments> should_returnResponse_when_givenRequest() {
            return List.of(
                    Arguments.of(new ThemeRequest("theme1", "theme1", 1000)),
                    Arguments.of(new ThemeRequest("theme2", "theme2", 2000)),
                    Arguments.of(new ThemeRequest("theme3", "theme3", 3000))
            );
        }

        @DisplayName("이름이 중복될 경우 예외 발생")
        @Test
        void should_throwException_when_nameDuplicated() {
            var request = new ThemeRequest("theme1", "theme1", 1000);
            service.create(request);

            Assertions.assertThatThrownBy(() -> service.create(request))
                    .isInstanceOf(ThemeConflictException.class)
                    .hasMessage(ErrorMessage.THEME_CONFLICT.getErrorMessage());
        }
    }
}