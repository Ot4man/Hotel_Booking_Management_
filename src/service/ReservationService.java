package service;

import exception.*;
import model.Reservation;
import model.ReservationStatus;
import model.Room;
import model.RoomStatus;
import model.User;
import repository.ReservationRepository;
import repository.RoomRepository;
import util.DateUtils;
import util.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    private int reservationCompteur = 1;

    public ReservationService(
            ReservationRepository reservationRepository,
            RoomRepository roomRepository
    ) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public Reservation createReservation(
            User user,
            String roomNumber,
            LocalDate checkIn,
            LocalDate checkOut,
            int numberOfGuests
    ) {

        if (!DateUtils.areValidDates(checkIn, checkOut)) {
            throw new InvalidReservationDateException(
                    "Checkout date must be after checkin date"
            );
        }

        Room room = roomRepository.findByRoomNumber(roomNumber);

        if (room == null) {
            throw new RoomNotFoundException(
                    "Room not found: " + roomNumber
            );
        }

        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RoomUnavailableException(
                    "Room " + roomNumber + " is not available."
            );
        }

        if (numberOfGuests <= 0) {
            throw new RoomCapacityExceededException(
                    "Number of guests must be greater than 0."
            );
        }

        if (numberOfGuests > room.getCapacity()) {
            throw new RoomCapacityExceededException(
                    "Room capacity is " + room.getCapacity()
                            + ". Guests requested: " + numberOfGuests
            );
        }

        List<Reservation> roomReservations =
                reservationRepository.findRoomByNumber(roomNumber);

        for (Reservation reservation : roomReservations) {

            if (reservation.getStatus() != ReservationStatus.CONFIRMED) {
                continue;
            }

            boolean overlap = DateUtils.datesOverlap(
                    checkIn,
                    checkOut,
                    reservation.getCheckIn(),
                    reservation.getCheckOut()
            );

            if (overlap) {
                throw new RoomUnavailableException(
                        "Room " + roomNumber
                                + " is already reserved for these dates."
                );
            }
        }

        long numberOfNights =
                DateUtils.calculteNights(checkIn, checkOut);

        var totalPrice =
                MoneyUtils.calculateTotal(
                        room.getPricePerNight(),
                        numberOfNights
                );

        UUID id = UUID.randomUUID();

        String reservationCode =
                String.format(
                        "RES-%04d",
                        reservationCompteur++
                );

        Reservation reservation = new Reservation(
                id,
                LocalDateTime.now(),
                totalPrice,
                ReservationStatus.CONFIRMED,
                numberOfNights,
                numberOfGuests,
                checkOut,
                checkIn,
                room.getRoomNumber(),
                user.getId(),
                reservationCode
        );

        reservationRepository.save(reservation);

        return reservation;

    }

    public List<Reservation> getMyReservations(User user) {
        return reservationRepository.findByUserId(user.getId());
    }

    public void cancelReservation(User user, String reservationcode) {
        Reservation reservation = reservationRepository.findByCode(reservationcode).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));


        if (!reservation.getUserId().equals(user.getId())) {
            throw new UnauthorizedReservationAccessException("you not allowed to cancel this reservation");
        }
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationAlreadyCancelledException("Reservation already cancelled");
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

    }

    public Reservation updateReservation(
            User user, String reservationCode, String newRoomNumber, LocalDate newCheckIn, LocalDate newCheckout, int newNumberOfGuests) {
        Reservation reservation = reservationRepository.findByCode(reservationCode).orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        if (!reservation.getUserId().equals(user.getId())) {
            throw new UnauthorizedReservationAccessException("not allowed to update");
        }
        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new ReservationAlreadyCancelledException("Canceled reservation cant updates");
        }
        if (!DateUtils.areValidDates(newCheckIn, newCheckout)) {
            throw new InvalidReservationDateException("Checkout must be after checkin");
        }
        Room room = roomRepository.findByRoomNumber(newRoomNumber);
        
        if (room == null) {
            throw new RoomNotFoundException("Room not found: " + newRoomNumber);
        }

        if (room.getStatus() != RoomStatus.AVAILABLE) {
            throw new RoomUnavailableException("Room " + newRoomNumber + " not available");
        }
        if (newNumberOfGuests <= 0) {
            throw new RoomCapacityExceededException("Number of guest must be more than 0");
        }
        if (newNumberOfGuests > room.getCapacity()) {
            throw new RoomCapacityExceededException("Room capacity is " + room.getCapacity() + "not" + newNumberOfGuests);
        }
        List<Reservation> roomReservations = reservationRepository.findRoomByNumber(newRoomNumber);
        for (Reservation existe : roomReservations) {
            if (existe.getStatus() != ReservationStatus.CONFIRMED) {
                continue;
            }
            if (existe.getId().equals(reservation.getId())) continue;

            boolean overlap = DateUtils.datesOverlap(newCheckIn, newCheckout, existe.getCheckIn(), existe.getCheckOut());

            if (overlap) {
                throw new RoomUnavailableException("Romm " + newRoomNumber + "is already reserved for these dates");
            }
        }
        long newNumberOfNights = DateUtils.calculteNights(newCheckIn, newCheckout);
        BigDecimal newTotalPrice = MoneyUtils.calculateTotal(room.getPricePerNight(), newNumberOfNights);


        reservation.setRoomNumber(newRoomNumber);
        reservation.setCheckIn(newCheckIn);
        reservation.setCheckOut(newCheckout);
        reservation.setNumberOfGuests(newNumberOfGuests);
        reservation.setNumberOfNights(newNumberOfNights);
        reservation.setTotalPrice(newTotalPrice);
        
        reservationRepository.save(reservation);

        return reservation;
    }

}

