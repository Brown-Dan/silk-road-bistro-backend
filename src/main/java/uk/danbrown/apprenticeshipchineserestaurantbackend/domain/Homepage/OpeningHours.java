package uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage;

import java.time.LocalTime;

import static uk.danbrown.apprenticeshipchineserestaurantbackend.domain.Homepage.OpeningHours.Builder.anOpeningHours;

public record OpeningHours(
        OpenCloseTime monday,
        OpenCloseTime tuesday,
        OpenCloseTime wednesday,
        OpenCloseTime thursday,
        OpenCloseTime friday,
        OpenCloseTime saturday,
        OpenCloseTime sunday
)
{
    public static OpeningHours getDefault() {
        return new OpeningHours(
                getDefaultOpenCloseTime(),
                getDefaultOpenCloseTime(),
                getDefaultOpenCloseTime(),
                getDefaultOpenCloseTime(),
                getDefaultOpenCloseTime(),
                getDefaultOpenCloseTime(),
                getDefaultOpenCloseTime()
        );
    }

    public static OpenCloseTime getDefaultOpenCloseTime() {
        return new OpenCloseTime(LocalTime.of(0, 0), LocalTime.of(0, 0), false);
    }

    public static final class Builder {
        private OpenCloseTime monday;
        private OpenCloseTime tuesday;
        private OpenCloseTime wednesday;
        private OpenCloseTime thursday;
        private OpenCloseTime friday;
        private OpenCloseTime saturday;
        private OpenCloseTime sunday;

        private Builder() {
        }

        public static Builder anOpeningHours() {
            return new Builder();
        }

        public Builder withMonday(OpenCloseTime monday) {
            this.monday = monday;
            return this;
        }

        public Builder withTuesday(OpenCloseTime tuesday) {
            this.tuesday = tuesday;
            return this;
        }

        public Builder withWednesday(OpenCloseTime wednesday) {
            this.wednesday = wednesday;
            return this;
        }

        public Builder withThursday(OpenCloseTime thursday) {
            this.thursday = thursday;
            return this;
        }

        public Builder withFriday(OpenCloseTime friday) {
            this.friday = friday;
            return this;
        }

        public Builder withSaturday(OpenCloseTime saturday) {
            this.saturday = saturday;
            return this;
        }

        public Builder withSunday(OpenCloseTime sunday) {
            this.sunday = sunday;
            return this;
        }

        public OpeningHours build() {
            return new OpeningHours(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
        }
    }

    public OpeningHours.Builder cloneBuilder() {
        return anOpeningHours()
                .withMonday(monday)
                .withMonday(tuesday)
                .withMonday(wednesday)
                .withMonday(thursday)
                .withMonday(friday)
                .withMonday(saturday)
                .withMonday(saturday);
    }
}
