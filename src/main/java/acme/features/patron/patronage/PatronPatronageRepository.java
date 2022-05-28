package acme.features.patron.patronage;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Patronage;
import acme.entities.PatronageReport;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;
import acme.roles.Patron;

@Repository
public interface PatronPatronageRepository extends AbstractRepository{
	
	@Query("select p from Patronage p WHERE p.id = :id")
    Patronage findOnePatronageById(int id);
	
	@Query("select pr from PatronageReport pr WHERE pr.patronage.id = :id")
	Collection<PatronageReport> findPatronageReportsByPatronageId(int id);
	
	@Query("select p from Patronage p WHERE p.code = :code")
    Patronage findOnePatronageByCode(String code);

    @Query("select p from Patronage p WHERE p.patron.id = :patronId")
    Collection<Patronage> findManyPatronagesByPatronId(int patronId);
    
    @Query("select p from Patron p where p.id = :id")
    Patron findOnePatronById(int id);

    @Query("select i from Inventor i")
    Collection<Inventor> findInventors();

	@Query("select inventor from Inventor inventor WHERE inventor.id = :id")
    Inventor findInventorById(int id);
    
}
