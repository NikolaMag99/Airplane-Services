package SKProjekat2.Servis1.repository;


import SKProjekat2.Servis1.Entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public boolean existsByEmail(String email);

    @Query("select u from User u where :id = id")
    public User getById(long id);
}
