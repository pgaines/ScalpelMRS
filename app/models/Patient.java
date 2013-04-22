package models;

import java.util.*;
import javax.persistence.*;
import javax.validation.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.Constraints.*;

import com.avaje.ebean.*;

/**
 * Patient entity managed by Ebean
 */
@Entity 
@Table(name="patient")
public class Patient extends User {

    public interface All {}
    public interface Step1{}    
	public interface Step2{}    

    @Id
    public Integer id;	
	
    @Required(groups = {All.class, Step1.class})
    @Email(groups = {All.class, Step1.class})
    public String email;	
	
    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 4, groups = {All.class, Step1.class})
    public String patientname;
    
    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 6, groups = {All.class, Step1.class})
    public String password;
	
	public String patient_type;
	
	@Required(groups = {All.class, Step2.class})
	public String phone;	
	public String address;	
	public String city;
	public String state_;
	public String country;
	
	@Min(value = 18, groups = {All.class, Step2.class}) @Max(value = 100, groups = {All.class, Step2.class})
	public Integer age;
	
    
    public Patient() {}

    public Patient(Integer id, String patientname, String email, String password, String patient_type, String phone, String address, String city, String state_, String country, Integer age) {
        this.id = id;
		this.patientname = patientname;
        this.email = email;
        this.password = password;
		this.patient_type = patient_type;
        this.phone = phone;		
        this.address = address;
        this.city = city;	
        this.state_ = state_;		
        this.country = country;
        this.age = age;		
    }
}

