package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import java.util.*;

import models.*;

import views.html.*;
import views.html.users.*;

/**
 * Manage users related operations.
 */
@Security.Authenticated(Secured.class)
public class Users extends Controller {
   /**
     * Defines a form wrapping the User class.
     */ 
    final static Form<User> editAccountForm = form(User.class, User.All.class);
 
    /**
     * This result directly redirect to application home. Pulls a list of users filtered by username.
     */
    public static Result GO_HOME = redirect(
        routes.Users.list(0, "username", "asc", "", "username")
    );
    
    /**
     * Handle default path requests, redirect to users list
     */
    public static Result index() {
        return GO_HOME;
    }       
    
    /**
     * Display the user's account summary
     */  
    public static Result summary() {
        return ok(
            summary.render(
                User.findByEmail(request().username())
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
     * @param filter Filter applied on user names
     */
    public static Result list(int page, String sortBy, String order, String filter, String filterable) {
        return ok(
            list.render(
                User.findByEmail(request().username()), 
                User.page(page, 10, sortBy, order, filter, filterable),
                sortBy, order, filter, filterable
            )
        );
    }   

    /**
     * Display a form pre-filled with the current user's existing account.
     */
    public static Result editAccount() {    
        return ok(
            form.render(
                User.findByEmail(request().username()),
                editAccountForm.fill(User.findByEmail(request().username()))
            )
        );  
    }   
    
    /**
     * Display a form pre-filled with another user's existing account.
     */
    public static Result editUser(Integer id) { 
        return ok(
            form.render(
                User.findById(id),
                editAccountForm.fill(User.findById(id))
            )
        );  
    }   
	
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the user to edit
     */
    public static Result updateUser(Integer id) {
       Form<User> filledForm = editAccountForm.bindFromRequest();
              
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
            return badRequest(form.render(emptyUser, filledForm));
        } else {
			filledForm.get().update(id);
        flash("success", "User has been updated");
        return GO_HOME;
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

