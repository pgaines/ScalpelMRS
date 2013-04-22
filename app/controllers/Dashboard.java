package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import java.util.*;

import models.*;

import views.html.*;

/**
 * Manage users related operations.
 */
@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {

   
    /**
     * Handle default path requests, redirect to dashboard
     */
    public static Result dashboard() {
        return ok(
            dashboard.render(
                User.findByEmail(request().username())
            )
        );
    }       
}

