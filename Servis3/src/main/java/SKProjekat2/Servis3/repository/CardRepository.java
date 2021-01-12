package SKProjekat2.Servis3.repository;


import SKProjekat2.Servis3.entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>{
	
	@Query("select count(c) from Card c where flight_id = :id")
	int countCardsWithFlights(long id);
	
	@Query("select user_id from Card c where flight_id = :id")
	List<Long> getAllUsers(long id);
	
	@Query("select c from Card c where flight_id = :id")
	List<Card> getAllCards(long id);
	
	@Query("select c from Card c where user_id = :id order by date desc")
	List<Card> findCardsOfUser(long id);
	
}
