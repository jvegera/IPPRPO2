package by.student.hotel;

import by.student.hotel.model.Booking;
import by.student.hotel.service.BookingCalculator;


public class Main {

    public static void main(String[] args) {

        Booking booking = new Booking(120, 5, 2, true);

        BookingCalculator calculator = new BookingCalculator();

        System.out.println(calculator.buildReport(booking));
        System.out.printf("Загрузка отеля: %.1f%%%n",
                calculator.calculateOccupancy(30, 40));
    }
}
//zc
