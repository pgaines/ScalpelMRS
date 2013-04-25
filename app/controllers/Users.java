package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import java.util.*;

import models.*;

import views.html.*;
import views.html.users.*;
import views.html.users.graphs.*;

/**
 * Manage users related operations.
 */
@Security.Authenticated(Secured.class)
public class Users extends Controller {
   /**
     * Defines a form wrapping the User class.
     */ 
    final static Form<User> userForm = form(User.class, User.All.class);
 
    /**
     * This result directly redirect to application home. Pulls a list of users filtered by username.
     */
    public static Result GO_HOME = redirect(
        routes.Users.viewUsers(0, "username", "asc", "", "username")
    );
    
    /**
     * Handle default path requests, redirect to users list
     */
    public static Result index() {
        return GO_HOME;
    }       
      
    /**
     * Display the paginated list of users.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filterable User property to which filter is applied    
     * @param filter Filter applied on user names
     */
    public static Result viewUsers(int page, String sortBy, String order, String filter, String filterable) {
        return ok(
            viewUsers.render(
                User.findByEmail(request().username()), 
                User.page(page, 10, sortBy, order, filter, filterable),
                sortBy, order, filter, filterable
            )
        );
    }    
	
    /**
     * Display the paginated list of users.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filterable User property to which filter is applied    
     * @param username Filter applied on user names
     */
    public static Result viewUsersByUsername(int page, String sortBy, String order, String username, String filterable) {
        return ok(
            viewUsers.render(
                User.findByEmail(request().username()), 
                User.page(page, 10, sortBy, order, username, filterable),
                sortBy, order, username, filterable
            )
        );
    }    	
	
    /**
     * Display the user's account summary
     */  
    public static Result viewUser(Integer id, String username) {
        return ok(
            viewUser.render(
                User.findByEmail(request().username()), 
                User.findById(id),
				Exam.graphList(username)  //create a graph list sorted ascending by date
            )
        );
    }   

    /**
     * Display a blank createUser form.
     */ 
    public static Result createUser(Integer id) { 
        return ok(
            form.render(
                User.findByEmail(request().username()), 
				User.emptyUser(),
                userForm.fill(User.emptyUser())
            )
        );  
    }   	
    
    /**
     * Display a form pre-filled with a user's existing account.
     */
    public static Result editUser(Integer id) { 
        return ok(
            form.render(
                User.findByEmail(request().username()), 
				User.findById(id),
                userForm.fill(User.findById(id))
            )
        );  
    }   
	
    /**
     * Handle the form submission 
     *
     * @param userID Id of the user who is logged in
	 * @param updatedID Id of the user whose account is saved.
     */
    public static Result saveUser(Integer userID, Integer savedID) {
       Form<User> filledForm = form(User.class, User.All.class).bindFromRequest();
              
        // Check repeated password
        if(!filledForm.field("password").valueOr("").isEmpty()) {
            if(!filledForm.field("password").valueOr("").equals(filledForm.field("repeatPassword").value())) {
                filledForm.reject("repeatPassword", "Password don't match");
            }
        }
        
        // Check if the username is valid
        if(!filledForm.hasErrors()) {
            if(filledForm.get().username.equals("admin") || filledForm.get().username.equals("guest")) {
                filledForm.reject("username", "This username is already taken");
            }
        }
        
        if(filledForm.hasErrors()) {
			User emptyUser = User.emptyUser();		
            return badRequest(form.render(User.findById(userID), User.findById(savedID), filledForm));
        } else {
			User saved = filledForm.get();
			if(savedID == User.incrementId()) {              //Checks if the id of the saved user account is new,
				filledForm.get().save();								 //If so, use save() to insert the user in the database
			} else {										 //Otherwise, the user account must already exist, 
				saved.update(savedID);						 //so use update() to change the existing user
			}
			flash("success", "User has been saved");
			return ok(viewUser.render(User.findById(userID), saved, Exam.graphList(saved.username)));
        }	
    }	
    
    /**
     * Handle user deletion
     */
    public static Result deleteUser(Integer id) {
        User.findById(id).delete();
        flash("success", "User has been deleted");
        return GO_HOME;
    }	
}

