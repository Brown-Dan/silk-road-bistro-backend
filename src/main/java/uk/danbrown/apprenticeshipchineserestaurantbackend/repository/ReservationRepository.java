package uk.danbrown.apprenticeshipchineserestaurantbackend.repository;

import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import uk.co.autotrader.generated.tables.pojos.ReservationEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.context.RequestContextManager;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Reservation;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;

import java.util.List;
import java.util.Optional;

import static uk.co.autotrader.generated.tables.Reservation.RESERVATION;

@Repository
public class ReservationRepository {

    private final DSLContext db;
    private final RequestContextManager requestContextManager;

    public ReservationRepository(DSLContext db, RequestContextManager requestContextManager) {
        this.db = db;
        this.requestContextManager = requestContextManager;
    }

    public Reservation insertReservation(Reservation reservation) throws FailureInsertingEntityException {
        Optional<ReservationEntity> insertedEntity = db.insertInto(RESERVATION)
                .set(RESERVATION.ORGANIZATION_ID, requestContextManager.getRequestContext().currentId())
                .set(RESERVATION.USER_ID, reservation.username())
                .set(RESERVATION.TIME, reservation.reservationTime())
                .set(RESERVATION.PEOPLECOUNT, reservation.numberOfPeople())
                .returningResult().fetchOptionalInto(ReservationEntity.class);

        return insertedEntity.map(this::mapReservationEntityToReservation).orElseThrow(() -> new FailureInsertingEntityException(reservation));
    }

    public Reservation getReservationByUserId(String userId) {
        Optional<ReservationEntity> reservationEntity = db.selectFrom(RESERVATION)
                .where(RESERVATION.USER_ID.eq(userId).and(RESERVATION.ORGANIZATION_ID.eq(requestContextManager.getRequestContext().currentId())))
                .fetchOptionalInto(ReservationEntity.class);

        return reservationEntity.map(this::mapReservationEntityToReservation).orElse(null);
    }

    public List<Reservation> getReservations() {
        List<ReservationEntity> reservationEntities = db.selectFrom(RESERVATION)
                .where(RESERVATION.ORGANIZATION_ID.eq(requestContextManager.getRequestContext().currentId()))
                .fetchInto(ReservationEntity.class);

        return reservationEntities.stream().map(this::mapReservationEntityToReservation).toList();
    }

    private Reservation mapReservationEntityToReservation(ReservationEntity reservationEntity) {
        return new Reservation(reservationEntity.getUserId(), reservationEntity.getTime(), reservationEntity.getPeoplecount());
    }
}
