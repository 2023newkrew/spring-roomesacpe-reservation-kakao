package nextstep.reservations.util.mapper;

import javax.annotation.processing.Generated;
import nextstep.reservations.domain.entity.reservation.Reservation;
import nextstep.reservations.domain.entity.theme.Theme;
import nextstep.reservations.dto.reservation.ReservationRequestDto;
import nextstep.reservations.dto.reservation.ReservationResponseDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-01-12T17:39:17+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 18.0.2 (Amazon.com Inc.)"
)
@Component
public class ReservationMapperImpl implements ReservationMapper {

    @Override
    public Reservation requestDtoToReservation(ReservationRequestDto requestDto) {
        if ( requestDto == null ) {
            return null;
        }

        Reservation.ReservationBuilder reservation = Reservation.builder();

        reservation.theme( reservationRequestDtoToTheme( requestDto ) );
        reservation.date( requestDto.getDate() );
        reservation.time( requestDto.getTime() );
        reservation.name( requestDto.getName() );

        return reservation.build();
    }

    @Override
    public ReservationResponseDto reservationToResponseDto(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponseDto.ReservationResponseDtoBuilder reservationResponseDto = ReservationResponseDto.builder();

        reservationResponseDto.themeName( reservationThemeName( reservation ) );
        reservationResponseDto.themeDesc( reservationThemeDesc( reservation ) );
        reservationResponseDto.themePrice( reservationThemePrice( reservation ) );
        reservationResponseDto.id( reservation.getId() );
        reservationResponseDto.date( reservation.getDate() );
        reservationResponseDto.time( reservation.getTime() );
        reservationResponseDto.name( reservation.getName() );

        return reservationResponseDto.build();
    }

    protected Theme reservationRequestDtoToTheme(ReservationRequestDto reservationRequestDto) {
        if ( reservationRequestDto == null ) {
            return null;
        }

        Theme.ThemeBuilder theme = Theme.builder();

        theme.name( reservationRequestDto.getThemeName() );
        theme.desc( reservationRequestDto.getThemeDesc() );
        theme.price( reservationRequestDto.getThemePrice() );

        return theme.build();
    }

    private String reservationThemeName(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Theme theme = reservation.getTheme();
        if ( theme == null ) {
            return null;
        }
        String name = theme.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String reservationThemeDesc(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Theme theme = reservation.getTheme();
        if ( theme == null ) {
            return null;
        }
        String desc = theme.getDesc();
        if ( desc == null ) {
            return null;
        }
        return desc;
    }

    private Integer reservationThemePrice(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }
        Theme theme = reservation.getTheme();
        if ( theme == null ) {
            return null;
        }
        Integer price = theme.getPrice();
        if ( price == null ) {
            return null;
        }
        return price;
    }
}
