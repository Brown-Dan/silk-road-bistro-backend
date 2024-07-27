package uk.danbrown.apprenticeshipchineserestaurantbackend.service;

import org.springframework.stereotype.Service;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Reservation;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public Reservation createReservation(Reservation reservation) throws FailureInsertingEntityException {
        return repository.insertReservation(reservation);
    }

    public Reservation getReservationByUserId(String userId) {
        return repository.getReservationByUserId(userId);
    }

    public List<Reservation> getReservations() {
        return repository.getReservations();
    }
}
