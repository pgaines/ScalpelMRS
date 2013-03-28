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
    final static Form<User> createAccountForm = form(User.class, User.All.class);
 
    /**
     * This result directly redirect to application home. Pulls a list of users filtered by username.
     */
    public static Result GO_HOME = redirect(
        routes.Users.list(0, "username", "asc", "", "username")
    );
    
    /**
     * Handle default path requests, redirect to computers list
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
                createAccountForm.fill(User.findByEmail(request().username()))
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
                createAccountForm.fill(User.findById(id))
            )
        );  
    }   
}

