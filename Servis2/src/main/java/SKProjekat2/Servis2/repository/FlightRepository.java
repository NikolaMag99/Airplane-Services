package SKProjekat2.Servis2.repository;


import SKProjekat2.Servis2.entity.Flight;
import SKProjekat2.Servis2.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>{

    @Query("select f from Flight f where plane = :plane and startPlace = :startPlace and endPlace = :endPlace")
    Flight findFlight(Plane plane, String startPlace, String endPlace);

//    @Query("select f from Flight f where rownum > :beg and rownum <= :end")
//    List<Flight> findFlightsByPage(int beg, int end);

    @Query("select f from Flight f where plane = :plane")
    List<Flight> findFlightsByPlane(Plane plane);
}
