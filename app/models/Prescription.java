package models;

import java.util.*;
import javax.persistence.*;
import javax.validation.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.Constraints.*;

import com.avaje.ebean.*;

/**
 * Prescription entity managed by Ebean
 */
@Entity 
@Table(name="prescription")
public class Prescription extends Model {

    public interface All {}
    public interface Step1{}    
	public interface Step2{} 

    @Id
    public Integer id;
    
    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 4, groups = {All.class, Step1.class})
    public String username;	
	
	@Required(groups = {All.class, Step1.class})
    @MinLength(value = 4, groups = {All.class, Step1.class})
    public String staffName; //attending physician
	
 	@Required(groups = {All.class, Step2.class})	
    public String medicationName;   
    public String dosage;                   // in milligrams
    public String frequency;                // per # of hours
    
	@Required(groups = {All.class, Step2.class})	
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date endDate;                        //When to stop taking the medication
	
    public Prescription(Integer id, String username, String staffName, String medicationName, String dosage, String frequency, Date endDate) {
        this.id = id;
		this.username = username;
        this.staffName = staffName;
        this.medicationName = medicationName;		
        this.dosage = dosage;
        this.frequency = frequency;	
        this.endDate = endDate;			
    }	

    // -- Queries
	
    /**
     * Generic query helper for entity Prescription with id Integer
     */
    public static Finder<Integer,Prescription> find = new Finder<Integer,Prescription>(Integer.class, Prescription.class); 	

    /**
     * Return a page of prescriptions
     *
     * @param page Page to display
     * @param pageSize Number of prescriptions per page
     * @param sortBy Prescription property used for sorting
     * @param order Sort order (either or asc or desc)
	 * @param filterable Prescription property to which filter is applied	 
     * @param filter Filter applied on the filterable column
     */
    public static Page<Prescription> page(int page, int pageSize, String sortBy, String order, String filter, String filterable) {
        return 
            find.where()
                .ilike(filterable, "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }		

	/**
	 * Create an empty prescription
	 */
	 public static Prescription emptyPrescription() {
		Integer id = Prescription.incrementId();	 
	    Prescription emptyPrescription = new Prescription(
            id, null, null, null, null, null, null
        );
		return emptyPrescription;
	}	
    
    /**
     * Retrieve all Prescriptions.
     */
    public static List<Prescription> findAll() {
        return find.all();
    }

    /**
     * Retrieve a prescription by id.
     */
    public static Prescription findById(Integer id) {
        return find.where().eq("id", id).findUnique();
    } 
    
	/**
     * Retrieve new id, incremented by 1 from row count of prescriptions.
     */
	public static Integer incrementId() {
		Integer newId = find.findRowCount() + 1;
		return newId;
	}
	
    /**
     * Retrieve max id from prescriptions.
     */
    public static Integer findMaxId() {
		Integer maxId = find.findRowCount();
        return maxId;
    }	    
}

