package SKProjekat2.Servis2.repository;

import SKProjekat2.Servis2.entity.Plane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaneRepository extends JpaRepository<Plane,Long> {
    public Plane findByName(String name);
}
