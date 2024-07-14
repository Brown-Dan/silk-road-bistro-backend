package uk.danbrown.apprenticeshipchineserestaurantbackend.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpenCloseTime;
import uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours;
import uk.danbrown.apprenticeshipchineserestaurantbackend.exception.FailureInsertingEntityException;
import uk.danbrown.apprenticeshipchineserestaurantbackend.repository.homepage.OpeningHoursRepository;
import uk.danbrown.apprenticeshipchineserestaurantbackend.service.homepage.OpeningHoursService;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours.Builder.anOpeningHours;

@ExtendWith(MockitoExtension.class)
public class OpeningHoursServiceTest {

    @Mock
    private OpeningHoursRepository openingHoursRepository;

    private OpeningHoursService openingHoursService;

    @BeforeEach
    void setUp() {
        openingHoursService = new OpeningHoursService(openingHoursRepository);
    }

    @Test
    void insertOpeningHours_givenOpeningHours_shouldCallOpeningHoursRepository() throws FailureInsertingEntityException {
        when(openingHoursRepository.insertOpeningHours(any())).thenReturn(getOpeningHours());

        OpeningHours result = openingHoursService.insertOpeningHours(getOpeningHours());

        verify(openingHoursRepository).insertOpeningHours(getOpeningHours());
        assertThat(result).isEqualTo(getOpeningHours());
    }

    @Test
    void getOpeningHours_givenOpeningHoursExist_shouldReturnOptionalOpeningHours() {
        when(openingHoursRepository.getOpeningHours()).thenReturn(Optional.of(getOpeningHours()));

        Optional<OpeningHours> result = openingHoursService.getOpeningHours();

        assertThat(result).hasValue(getOpeningHours());
    }

    @Test
    void getOpeningHours_givenOpeningHoursDoNotExist_shouldReturnOptionalOpeningHours() {
        when(openingHoursRepository.getOpeningHours()).thenReturn(Optional.empty());

        Optional<OpeningHours> result = openingHoursService.getOpeningHours();

        assertThat(result).isEmpty();
    }

    private OpeningHours getOpeningHours() {
        return anOpeningHours()
                .withMonday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withTuesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withWednesday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withThursday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withFriday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withSaturday(new OpenCloseTime(LocalTime.of(7, 0, 0), LocalTime.of(19, 0, 0), false))
                .withSunday(new OpenCloseTime(null, null, true))
                .build();
    }
}
