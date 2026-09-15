package repository.impl;

import model.Reservation;
import repository.ReservationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class InMemoryReservationRepository implements ReservationRepository {

    private final List<Reservation> reservations = new ArrayList<>();

    @Override
    public void save(Reservation reservation) {
        reservations.add(reservation);
    }

    @Override
    public Optional<Reservation> findByid(UUID id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                return Optional.of(reservation);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Reservation> findByUserId(UUID userId) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getUserId().equals(userId)) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public List<Reservation> findRoomByNumber(String roomNumber) {
        List<Reservation> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getRoomNumber().equals(roomNumber)) {
                result.add(reservation);
            }
        }
        return result;
    }

    @Override
    public Optional<Reservation> findByCode(String reservationCode) {

        for (Reservation reservation : reservations) {

            if (reservation.getReservationCode().equals(reservationCode)) {
                return Optional.of(reservation);
            }
        }

        return Optional.empty();
    }
}