package by.student.hotel.service;

import by.student.hotel.model.Booking;

/**
 * Правила:
 *  - скидка на проживание: от 3 ночей — 5%, от 7 ночей — 10%;
 *  - скидка применяется только к стоимости номера;
 *  - завтрак стоит 15 BYN за гостя в сутки.
 */
public class BookingCalculator {

    public static final double BREAKFAST_PRICE = 15.0;

    public static final int LONG_STAY_NIGHTS = 7;
    public static final double LONG_STAY_DISCOUNT = 10.0;

    public static final int MEDIUM_STAY_NIGHTS = 3;
    public static final double MEDIUM_STAY_DISCOUNT = 5.0;

    public double calculateRoomCost(Booking booking) {
        return booking.getNightlyRate() * booking.getNights();
    }

    public double getDiscountPercent(Booking booking) {
        if (booking.getNights() >= LONG_STAY_NIGHTS) {
            return LONG_STAY_DISCOUNT;
        }
        if (booking.getNights() >= MEDIUM_STAY_NIGHTS) {
            return MEDIUM_STAY_DISCOUNT;
        }
        return 0.0;
    }

    public double calculateDiscount(Booking booking) {
        return calculateRoomCost(booking)
                * getDiscountPercent(booking) / 100.0;
    }

    public double calculateBreakfastCost(Booking booking) {
        if (!booking.isBreakfast()) {
            return 0.0;
        }
        return BREAKFAST_PRICE
                * booking.getGuests()
                * booking.getNights();
    }

    public double calculateTotal(Booking booking) {
        return calculateRoomCost(booking)
                + calculateBreakfastCost(booking)
                - calculateDiscount(booking);
    }

    /**
     * Загрузка отеля, %: занятые номера / всего номеров.
     * Ключевой показатель отельного бизнеса (Occupancy Rate).
     */
    public double calculateOccupancy(int occupiedRooms, int totalRooms) {
        if (totalRooms <= 0) {
            throw new IllegalArgumentException(
                    "Количество номеров должно быть > 0");
        }
        if (occupiedRooms < 0 || occupiedRooms > totalRooms) {
            throw new IllegalArgumentException(
                    "Занятых номеров должно быть от 0 до "
                            + totalRooms);
        }
        return occupiedRooms * 100.0 / totalRooms;
    }

    public String buildReport(Booking booking) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("=== Отчёт по бронированию ===%n"));
        sb.append(String.format(
                "Стоимость ночи: %.2f BYN%n", booking.getNightlyRate()));
        sb.append(String.format(
                "Ночей: %d%n", booking.getNights()));
        sb.append(String.format(
                "Гостей: %d%n", booking.getGuests()));
        sb.append(String.format(
                "Завтрак: %s%n", booking.isBreakfast() ? "да" : "нет"));
        sb.append(String.format(
                "Стоимость номера: %.2f BYN%n",
                calculateRoomCost(booking)));
        sb.append(String.format(
                "Завтраки: %.2f BYN%n",
                calculateBreakfastCost(booking)));
        sb.append(String.format(
                "Скидка: %.2f BYN%n", calculateDiscount(booking)));
        sb.append(String.format(
                "Итого к оплате: %.2f BYN%n", calculateTotal(booking)));

        return sb.toString();
    }
}
