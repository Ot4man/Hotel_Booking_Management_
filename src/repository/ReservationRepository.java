package repository;

import model.Reservation;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface ReservationRepository {

    void save(Reservation reservation);

    Optional<Reservation> findByid(UUID id);

    List<Reservation> findByUserId(UUID userId);

    List<Reservation> findRoomByNumber(String roomNumber);

    Optional<Reservation> findByCode(String reservationCode);
}
