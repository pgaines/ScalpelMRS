package models;

import java.util.*;
import javax.persistence.*;
import javax.validation.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.Constraints.*;

import com.avaje.ebean.*;

/**
 * Nurse entity managed by Ebean
 */
@Entity 
@Table(name="nurse")
public class Nurse extends User {

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
    public String nursename;
    
    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 6, groups = {All.class, Step1.class})
    public String password;
	
	public String nurse_type;
	
	@Required(groups = {All.class, Step2.class})
	public String phone;	
	public String address;	
	public String city;
	public String state_;
	public String country;
	
	@Min(value = 18, groups = {All.class, Step2.class}) @Max(value = 100, groups = {All.class, Step2.class})
	public Integer age;
	
    
    public Nurse() {}

    public Nurse(Integer id, String nursename, String email, String password, String nurse_type, String phone, String address, String city, String state_, String country, Integer age) {
        this.id = id;
		this.nursename = nursename;
        this.email = email;
        this.password = password;
		this.nurse_type = nurse_type;
        this.phone = phone;		
        this.address = address;
        this.city = city;	
        this.state_ = state_;		
        this.country = country;
        this.age = age;		
    }
}

