package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import java.util.*;

import models.*;

import views.html.*;
import views.html.dashboards.*;

/**
 * Manage users related operations.
 */
@Security.Authenticated(Secured.class)
public class Dashboard extends Controller {  

    /**
     * Handle default path requests, redirect to the index
     */
    public static Result landingPage() {
        return ok(
            landingPage.render(
                User.findByEmail(request().username())
            )
        );
    }   	

    /**
     * Handle default path requests, redirect to doctorDashboard
     */
    public static Result doctorDashboard() {
        return ok(
            doctorDashboard.render(
                User.findByEmail(request().username())
            )
        );
    }       
    
    /**
     * Handle default path requests, redirect to nurseDashboard
     */
    public static Result nurseDashboard() {
        return ok(
            nurseDashboard.render(
                User.findByEmail(request().username())
            )
        );
    }    

    /**
     * Handle default path requests, redirect to patientDashboard
     */
    public static Result patientDashboard() {
        return ok(
            patientDashboard.render(
                User.findByEmail(request().username())
            )
        );
    }       
}

