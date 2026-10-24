package by.student.hotel.model;

public class Booking {

    public static final int MAX_GUESTS = 4;

    private final double nightlyRate;
    private final int nights;
    private final int guests;
    private final boolean breakfast;

    public Booking(
            double nightlyRate,
            int nights,
            int guests,
            boolean breakfast) {

        if (nightlyRate <= 0) {
            throw new IllegalArgumentException(
                    "Стоимость ночи должна быть > 0");
        }

        if (nights <= 0) {
            throw new IllegalArgumentException(
                    "Количество ночей должно быть > 0");
        }

        if (guests <= 0 || guests > MAX_GUESTS) {
            throw new IllegalArgumentException(
                    "Количество гостей должно быть от 1 до "
                            + MAX_GUESTS);
        }

        this.nightlyRate = nightlyRate;
        this.nights = nights;
        this.guests = guests;
        this.breakfast = breakfast;
    }

    public double getNightlyRate() {
        return nightlyRate;
    }

    public int getNights() {
        return nights;
    }

    public int getGuests() {
        return guests;
    }

    public boolean isBreakfast() {
        return breakfast;
    }
}
