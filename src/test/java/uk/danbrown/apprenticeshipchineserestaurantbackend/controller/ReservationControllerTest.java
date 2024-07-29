package uk.danbrown.apprenticeshipchineserestaurantbackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Reservation;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.ReservationService;

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationControllerTest {

    @Mock
    private ReservationService reservationService;

    private ReservationController reservationController;

    @BeforeEach
    void setUp() {
        reservationController = new ReservationController(reservationService);
    }

    @Test
    void createReservation_givenReservation_shouldCallReservationService() throws FailureInsertingEntityException {
        when(reservationService.createReservation(any())).thenReturn(getReservation());

        ResponseEntity<Reservation> result = reservationController.createReservation(getReservation());

        verify(reservationService).createReservation(getReservation());
        assertThat(result).isEqualTo(ResponseEntity.status(201).body(getReservation()));
    }

    @Test
    void getReservationsByUserId_givenUserId_shouldCallReservationService() {
        when(reservationService.getReservationsByUserId("dan")).thenReturn(singletonList(getReservation()));

        ResponseEntity<java.util.List<Reservation>> result = reservationController.getReservationsByUserId("dan");

        verify(reservationService).getReservationsByUserId("dan");
        assertThat(result).isEqualTo(ResponseEntity.ok(singletonList(getReservation())));
    }

    @Test
    void getReservations_shouldCallReservationService() {
        when(reservationService.getReservations()).thenReturn(singletonList(getReservation()));

        ResponseEntity<java.util.List<Reservation>> result = reservationController.getReservations();

        verify(reservationService).getReservations();
        assertThat(result).isEqualTo(ResponseEntity.ok(singletonList(getReservation())));
    }

    private Reservation getReservation() {
        return new Reservation("dan", LocalDateTime.MAX, 5);
    }
}