package models;

import java.util.*;
import javax.persistence.*;
import javax.validation.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.Constraints.*;

import com.avaje.ebean.*;

/**
 * Exam entity managed by Ebean
 */
@Entity 
@Table(name="exam")
public class Exam extends Model {

    public interface All {}
    public interface Step1{}    
	public interface Step2{} 	

    @Id
    public Integer id;
    
    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 4, groups = {All.class, Step1.class})
    public String patientName;	
	
	@Required(groups = {All.class, Step1.class})
    @MinLength(value = 4, groups = {All.class, Step1.class})
    public String staffName; //attending physician
 
 	@Required(groups = {All.class, Step2.class})
    public Float systolic;	        //Systolic Blood pressure, in units of mm Hg (millimeters of Mercury)
    public Float diastolic;	        //Diastolic Blood pressure, in units of mm Hg (millimeters of Mercury)	
    public Float bloodSugar;		//in units of mg/dL (milligrams per decilitre)
    public Float weight;			//in units of lbs.
    public Float height;			//in units of inches

	@Required(groups = {All.class, Step2.class})	
    @Formats.DateTime(pattern="yyyy-MM-dd")
    public Date date;		
	
    public Exam(Integer id, String patientName, String staffName, Float systolic, Float diastolic, Float bloodSugar, Float weight, Float height, Date date) {
        this.id = id;
		this.patientName = patientName;
        this.staffName = staffName;
        this.systolic = systolic;		
        this.diastolic = diastolic;
        this.bloodSugar = bloodSugar;	
        this.weight = weight;
        this.height = height;		
        this.date = date;			
    }	

    // -- Queries
	
    /**
     * Generic query helper for entity Exam with id Integer
     */
    public static Finder<Integer,Exam> find = new Finder<Integer,Exam>(Integer.class, Exam.class); 	

    /**
     * Return a page of prescriptions
     *
     * @param page Page to display
     * @param pageSize Number of prescriptions per page
     * @param sortBy Exam property used for sorting
     * @param order Sort order (either or asc or desc)
	 * @param filterable Exam property to which filter is applied	 
     * @param filter Filter applied on the filterable column
     */
    public static Page<Exam> page(int page, int pageSize, String sortBy, String order, String filter, String filterable) {
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
	 public static Exam emptyExam() {
		Integer id = Exam.incrementId();	 
	    Exam emptyExam = new Exam(
            id, null, null, null, null, null, null, null, null
        );
		return emptyExam;
	}	
    
    /**
     * Retrieve all Exams.
     */
    public static List<Exam> findAll() {
        return find.all();
    }

    /**
     * Retrieve a prescription by id.
     */
    public static Exam findById(Integer id) {
        return find.where().eq("id", id).findUnique();
    } 
    
	/**
     * Retrieve new id, incremented by 1 from row count of users.
     */
	public static Integer incrementId() {
		Integer newId = find.findRowCount() + 1;
		return newId;
	}
	
    /**
     * Retrieve max id from exams.
     */
    public static Integer findMaxId() {
		Integer maxId = find.findRowCount();
        return maxId;
    }	    
	
}

