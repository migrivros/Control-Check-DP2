package acme.features.patron.dashboards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.repositories.AbstractRepository;

@Repository
public interface PatronDashboardRepository extends AbstractRepository{
	
	@Query("select count(p) from Patronage p where (p.status = acme.entities.PatronageStatus.PROPOSED AND p.patron.id = :id)")
	Double  numberOfProposedPatronages(int id);
	
	@Query("select count(p) from Patronage p where (p.status = acme.entities.PatronageStatus.ACCEPTED AND p.patron.id = :id)")
	Double  numberOfAcceptedPatronages(int id);
	
	@Query("select count(p) from Patronage p where (p.status = acme.entities.PatronageStatus.DENIED AND p.patron.id = :id)")
	Double  numberOfDeniedPatronages(int id);
	
	
	// AVERAGE
	
	@Query("select avg(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.PROPOSED AND p.patron.id = :id) GROUP BY p.status")
	Double averageBudgetProposedPatronages(int id);
	
	@Query("select avg(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.ACCEPTED AND p.patron.id = :id) GROUP BY p.status")
	Double averageBudgetAcceptedPatronages(int id);
	
	@Query("select avg(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.DENIED AND p.patron.id = :id) GROUP BY p.status")
	Double averageBudgetDeniedPatronages(int id);
	
	
	@Query("select stddev(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.PROPOSED AND p.patron.id = :id)")
	Double deviationBudgetProposedPatronages(int id);

	@Query("select stddev(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.ACCEPTED AND p.patron.id = :id)")
	Double deviationBudgetAcceptedPatronages(int id);
	
	@Query("select stddev(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.DENIED AND p.patron.id = :id)")
	Double deviationBudgetDeniedPatronages(int id);
	
	
	
	@Query("select min(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.PROPOSED AND p.patron.id = :id)")
	Double minBudgetProposedPatronages(int id);
	
	@Query("select min(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.ACCEPTED AND p.patron.id = :id)")
	Double minBudgetAcceptedPatronages(int id);
	
	@Query("select min(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.DENIED AND p.patron.id = :id)")
	Double minBudgetDeniedPatronages(int id);
	

	
	@Query("select max(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.PROPOSED AND p.patron.id = :id)")
	Double maxBudgetProposedPatronages(int id);
	
	@Query("select max(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.ACCEPTED AND p.patron.id = :id)")
	Double maxBudgetAcceptedPatronages(int id);
	
	@Query("select max(p.budget.amount) from Patronage p where (p.status = acme.entities.PatronageStatus.DENIED AND p.patron.id = :id)")
	Double maxBudgetDeniedPatronages(int id);
	
}
