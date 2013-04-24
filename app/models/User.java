package models;

import java.util.*;
import javax.persistence.*;
import javax.validation.*;

import play.db.ebean.*;
import play.data.format.*;
import play.data.validation.Constraints.*;

import com.avaje.ebean.*;

/**
 * User entity managed by Ebean
 */
@Entity 
@Table(name="user")
public class User extends Model {

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
    public String username;
    
    @Required(groups = {All.class, Step1.class})
    @MinLength(value = 6, groups = {All.class, Step1.class})
    public String password;
	
	public String user_type;
	
	@Required(groups = {All.class, Step2.class})
	public String phone;	
	public String address;	
	public String city;
	public String state_;
	public String country;
	
	@Min(value = 18, groups = {All.class, Step2.class}) @Max(value = 100, groups = {All.class, Step2.class})
	public Integer age;
    
    public User() {}

    public User(Integer id, String username, String email, String password, String user_type, String phone, String address, String city, String state_, String country, Integer age) {
        this.id = id;
		this.username = username;
        this.email = email;
        this.password = password;
		this.user_type = user_type;
        this.phone = phone;		
        this.address = address;
        this.city = city;	
        this.state_ = state_;		
        this.country = country;
        this.age = age;		
    }
	
    // -- Queries
    
   // public static Model.Finder<String,User> find = new Model.Finder(String.class, User.class);
 
    /**
     * Generic query helper for entity User with email String
     */
    public static Finder<String,User> find = new Finder<String,User>(String.class, User.class); 
	
    /**
     * Retrieve all users.
     */
    public static List<User> findAll() {
        return find.all();
    }
	
    /**
     * Return a page of all users
     *
     * @param page Page to display
     * @param pageSize Number of computers per page
     * @param sortBy User property used for sorting
     * @param order Sort order (either or asc or desc)
	 * @param filterable User property to which filter is applied
     * @param filter Filter applied on the name column
     */
    public static Page<User> page(int page, int pageSize, String sortBy, String order, String filter, String filterable) {
        return 
            find.where()
                .ilike(filterable, "%" + filter + "%")
                .orderBy(sortBy + " " + order)
                .findPagingList(pageSize)
                .getPage(page);
    }	
	
	/**
	 * Create an empty user
	 */
	 public static User emptyUser() {
		Integer id = User.incrementId();	 
	    User emptyUser = new User(
            id, null, null, null, "NewUser", null, null, null, null, null, null
        );
		return emptyUser;
	}	

	/**
     * Retrieve new id, incremented by 1 from row count of users.
     */
	public static Integer incrementId() {
		Integer newId = find.findRowCount() + 1;
		return newId;
	}
	
    /**
     * Retrieve max id from users.
     */
    public static Integer findMaxId() {
		Integer maxId = find.findRowCount();
        return maxId;
    }	

    /**
     * Retrieve a User by email.
     */
    public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }  	
	
    /**
     * Retrieve a User by id.
     */
    public static User findById(Integer id) {
        return find.where().eq("id", id).findUnique();
    }  	
    
    /**
     * Authenticate a User.
     */
    public static User authenticate(String email, String password) {
        return find.where()
            .eq("email", email)
            .eq("password", password)
            .findUnique();
    }
    
    // --
    
    public String toString() {
        return "User(" + email + ")";
    }
}

