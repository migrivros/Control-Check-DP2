package acme.features.inventor.patronage;


import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Patronage;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InventorPatronageRepository extends AbstractRepository {

	@Query("SELECT p FROM Patronage p WHERE p.id = :id")
    Patronage findOnePatronageById(int id);

    @Query("SELECT p FROM Patronage p WHERE p.inventor.id = :inventorId and p.published=true")
    Collection<Patronage> findManyPatronagesByInventorId(int inventorId);
    
}
