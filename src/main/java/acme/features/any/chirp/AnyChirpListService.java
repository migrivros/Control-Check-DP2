package acme.features.any.chirp;


import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Chirp;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyChirpListService implements AbstractListService<Any, Chirp> {

	@Autowired
	protected AnyChirpRepository repository;

	@Override
	public boolean authorise(final Request<Chirp> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Chirp> findMany(final Request<Chirp> request) {
		assert request != null;

		List<Chirp> result;
		final List<Chirp> result2 = new ArrayList<>();

		result = this.repository.findManyChirps();
		
		for(int i = 0; i<result.size();i++) {
			final Timestamp d = (Timestamp) result.get(i).getCreationMoment();
			final Duration duration = Duration.between(d.toLocalDateTime(), LocalDateTime.now());
			final long diff = duration.toDays();
			if(diff <= 30) {
				result2.add(result.get(i));
			}
			
		}

		return result2;
	}

	@Override
	public void unbind(final Request<Chirp> request, final Chirp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationMoment", "title", "author", "body", "email");
	}
}