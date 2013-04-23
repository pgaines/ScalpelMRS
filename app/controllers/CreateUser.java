package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import models.*;

import views.html.*;
import views.html.users.*;

public class CreateUser extends Controller {
    
    /**
     * Defines a form wrapping the User class.
     */ 
    final static Form<User> userForm = form(User.class, User.All.class);

    /**
     * Display a blank createUser form.
     */ 
    public static Result createUser(Integer id) {
        User emptyUser = User.emptyUser();  
        return ok(
            createUser.render(
                emptyUser,
                userForm.fill(emptyUser)
            )
        );  
    }   

    /**
     * Handle the form submission.
     */
    public static Result submitCreateUser() {
        Form<User> filledForm = form(User.class, User.All.class).bindFromRequest();
        
        // Check accept conditions
        if(!"true".equals(filledForm.field("accept").value()) && (User.findByEmail(request().username()) != null)) {
            filledForm.reject("accept", "You must accept the terms and conditions");
        }
        
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
            return badRequest(createUser.render(emptyUser, filledForm));
        } else {
            filledForm.get().save();
            User created = filledForm.get();
            return ok(viewUser.render(created, created));
        }
    }   
}