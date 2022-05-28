package acme.features.any.chirp;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Chirp;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyChirpRepository extends AbstractRepository{

	@Query("SELECT c FROM Chirp c WHERE c.id = ?1")
 	Chirp findOneChirpById(int id);

 	@Query("SELECT c FROM Chirp c")
 	List<Chirp> findManyChirps();
 	
}
