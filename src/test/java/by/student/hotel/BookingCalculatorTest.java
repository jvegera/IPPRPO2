package by.student.hotel;

import by.student.hotel.model.Booking;
import by.student.hotel.service.BookingCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты калькулятора бронирования отеля")
class BookingCalculatorTest {

    private BookingCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new BookingCalculator();
    }

    // ---------- Позитивные сценарии ----------

    @Test
    @DisplayName("Без скидки: 100 BYN, 2 ночи, без завтрака = 200")
    void totalWithoutDiscount() {
        Booking booking = new Booking(100, 2, 2, false);

        assertEquals(200.0, calculator.calculateTotal(booking), 0.001);
    }

    @Test
    @DisplayName("Скидка 5%: 100 BYN, 3 ночи = 285")
    void mediumStayDiscount() {
        Booking booking = new Booking(100, 3, 1, false);

        assertEquals(285.0, calculator.calculateTotal(booking), 0.001);
    }

    @Test
    @DisplayName("Скидка 10%: 100 BYN, 7 ночей = 630")
    void longStayDiscount() {
        Booking booking = new Booking(100, 7, 1, false);

        assertEquals(630.0, calculator.calculateTotal(booking), 0.001);
    }

    @Test
    @DisplayName("Завтрак: 100 BYN, 3 ночи, 2 гостя = 300 + 90 - 15 = 375")
    void breakfastIsAdded() {
        Booking booking = new Booking(100, 3, 2, true);

        assertEquals(90.0, calculator.calculateBreakfastCost(booking), 0.001);
        assertEquals(375.0, calculator.calculateTotal(booking), 0.001);
    }

    @Test
    @DisplayName("Загрузка отеля: 30 из 40 номеров = 75%")
    void occupancyRate() {
        assertEquals(75.0, calculator.calculateOccupancy(30, 40), 0.001);
    }

    @Test
    @DisplayName("Отчёт содержит ключевые поля")
    void reportContainsKeyFields() {
        Booking booking = new Booking(120, 5, 2, true);

        String report = calculator.buildReport(booking);

        assertAll(
                () -> assertTrue(report.contains("Итого к оплате")),
                () -> assertTrue(report.contains("Скидка")),
                () -> assertTrue(report.contains("Завтрак"))
        );
    }

    // ---------- Граничные сценарии ----------

    @Test
    @DisplayName("Граница: 2 ночи — скидки нет, 3 ночи — 5%")
    void discountBoundaryAtThreeNights() {
        assertEquals(0.0, calculator.getDiscountPercent(
                new Booking(100, 2, 1, false)), 0.001);
        assertEquals(5.0, calculator.getDiscountPercent(
                new Booking(100, 3, 1, false)), 0.001);
    }

    @Test
    @DisplayName("Граница: 6 ночей — 5%, 7 ночей — 10%")
    void discountBoundaryAtSevenNights() {
        assertEquals(5.0, calculator.getDiscountPercent(
                new Booking(100, 6, 1, false)), 0.001);
        assertEquals(10.0, calculator.getDiscountPercent(
                new Booking(100, 7, 1, false)), 0.001);
    }

    @Test
    @DisplayName("Граница: 1 ночь, 1 гость — минимальное бронирование")
    void minimalBooking() {
        Booking booking = new Booking(80, 1, 1, false);

        assertEquals(80.0, calculator.calculateTotal(booking), 0.001);
    }

    @Test
    @DisplayName("Граница: 4 гостя — максимальная вместимость допустима")
    void maxGuestsAllowed() {
        assertDoesNotThrow(() -> new Booking(100, 1, Booking.MAX_GUESTS, false));
    }

    // ---------- Ошибочные сценарии ----------

    @Test
    @DisplayName("Нулевая стоимость ночи вызывает исключение")
    void zeroRateIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Booking(0, 3, 2, false));
    }

    @Test
    @DisplayName("Отрицательное число ночей вызывает исключение")
    void negativeNightsIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Booking(100, -1, 2, false));
    }

    @Test
    @DisplayName("Ноль гостей вызывает исключение")
    void zeroGuestsIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Booking(100, 3, 0, false));
    }

    @Test
    @DisplayName("Пять гостей (больше вместимости) вызывает исключение")
    void tooManyGuestsIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Booking(100, 3, 5, false));
    }

    @Test
    @DisplayName("Занятых номеров больше, чем всего — исключение")
    void occupiedMoreThanTotalIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.calculateOccupancy(41, 40));
    }
}
