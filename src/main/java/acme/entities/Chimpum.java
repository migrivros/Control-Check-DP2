package acme.entities;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;


// ENTREGABLE INDIVIDUAL

@Entity
@Getter
@Setter
public class Chimpum extends AbstractEntity{
	
	// Serialisation identifier

		protected static final long	serialVersionUID	= 1L;
			
	// Attributes
		
		
		@NotBlank
		@Column(unique=true)
		@Pattern(regexp = "^[A-Z]{2}-[A-Z]{2}-$")  //AA-BB- +yymmdd  (luego, autogenerado)
		private String pattern;
		
		@NotNull
		@Past
		@Temporal(TemporalType.TIMESTAMP)
		protected Date creationMoment;
		
		@NotBlank
		@Length(min=1,max=100)
		protected String title;
		
		@NotBlank
		@Length(min=1,max=255)
		protected String description;
		
		@NotNull
	    @Temporal(TemporalType.TIMESTAMP)
	    protected Date startDate;

	    @NotNull
	    @Temporal(TemporalType.TIMESTAMP)
	    protected Date endDate;
	    
		@NotNull
		@Valid
		//@Min(value = 1) Habría que hacer custom constraint para ponerle un mínimo a la cantidad
		protected Money budget;
		
		@URL
		private String moreInfo;
		
		
	// Derived attributes -----------------------------------------------------
		
		public String getCode() {
			final Calendar cal = Calendar.getInstance();
			cal.setTime(this.creationMoment);
		    final String yy = String.valueOf(cal.get(Calendar.YEAR)).substring(2);
	        final String mm = String.valueOf(cal.get(Calendar.MONTH));
	        final String dd = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	        
	        return this.pattern + yy + mm + dd;
			
		}
		
	    //Se hace en el validate
		
		/*public Period getPeriod() {
			final LocalDate start = this.creationMoment.toInstant()
			      .atZone(ZoneId.systemDefault())
			      .toLocalDate();
			final LocalDate end = this.endDate.toInstant()
				.atZone(ZoneId.systemDefault())
			      .toLocalDate();
			this.period = Period.between(start, end);
			return this.period;
		}*/
		
		
	// Relationships ----------------------------------------------------------

}
