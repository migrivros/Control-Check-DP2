package acme.features.inventor.patronageReport;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.entities.Patronage;
import acme.entities.PatronageReport;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

public interface InventorPatronageReportRepository extends AbstractRepository {
	
	@Query("select pr from PatronageReport pr where pr.id = :id")
	PatronageReport findOneById(int id);
	
	@Query("select pr from PatronageReport pr where pr.patronage.inventor.id = :inventorId")
	Collection<PatronageReport> findManyPatronagesReportByInventorId(int inventorId);
	
	@Query("select i from Inventor i where i.id = :id")
	Inventor findOneInventorById(int id);
	
	@Query("select p from Patronage p where p.inventor.id = :id")
	Patronage findPatronageByInventorId(int id);

//	@Query("select patronage from Patronage patronage where patronage.id = :id")
//	Patronage findOnePatronageById(int id);
	
//	@Query("select patronageReport.sequenceNumber from PatronageReport patronageReport where patronageReport.patronage.id = :id")
//	Collection<String> getPatronageReportSequenceNumbersByPatronageId(int id);
	
	@Query("select a from Patronage a where a.id = :id")
	Patronage findOnePatronageById(int id);

	@Query("select pr from PatronageReport pr")
	List<PatronageReport> findAllPatronageReports();

	@Query("select a from PatronageReport a where a.patronage.id = :id")
	Collection<PatronageReport> findAllPatronageReportsByPatronageId(int id);


}
