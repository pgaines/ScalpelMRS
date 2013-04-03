package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import java.util.*;

import models.*;

import views.html.*;
import views.html.prescriptions.*;

/**
 * Manage prescriptions related operations.
 */
@Security.Authenticated(Secured.class)
public class Prescriptions extends Controller {
   /**
     * Defines a form wrapping the Prescription class.
     */ 
    final static Form<Prescription> createPrescriptionForm = form(Prescription.class);
 
    /**
     * This result directly redirect to application home. Pulls a list of prescriptions filtered by prescriptionname.
     */
    public static Result GO_HOME = redirect(
        routes.Prescriptions.list(0, "id", "asc", "", "patientName")
    );
    
    /**
     * Handle default path requests, redirect to prescriptions list
     */
    public static Result index() {
        return GO_HOME;
    }       
	
    /**
     * Display the prescription summary
     */  
    public static Result summary(Prescription prescription) {
		return ok(
			summary.render(
				User.findByEmail(request().username()),
				prescription
			)
		);
	}		
    
    /**
     * Display the paginated list of prescriptions.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
	 * @param filterable Prescription property to which filter is applied	 
     * @param filter Filter applied on Filterable
     */
    public static Result list(int page, String sortBy, String order, String filter, String filterable) {
        return ok(
            list.render(
                User.findByEmail(request().username()), 
                Prescription.page(page, 10, sortBy, order, filter, filterable),
                sortBy, order, filter, filterable
            )
        );
    }  

    /**
     * Display a form pre-filled with a prescription.
     */
    public static Result editPrescription(Integer id) { 
        return ok(
            form.render(
                User.findByEmail(request().username()),             
                Prescription.findById(id),
                createPrescriptionForm.fill(Prescription.findById(id))
            )
        );  
    }   
    
   /**
     * Display a blank createPrescription form.
     */ 
    public static Result createPrescription() {
        Prescription emptyPrescription = Prescription.emptyPrescription();  
        return ok(
            form.render(
                User.findByEmail(request().username()),             
                emptyPrescription,
                createPrescriptionForm.fill(emptyPrescription)
            )
        );  
    }   

    /**
     * Handle the form submission.
     */
    public static Result submitCreatePrescription() {
        Form<Prescription> filledForm = createPrescriptionForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
            Prescription emptyPrescription = Prescription.emptyPrescription();      
            return badRequest(form.render(User.findByEmail(request().username()), emptyPrescription, filledForm));
        } else {
            Prescription created = filledForm.get();
            return ok(summary.render(User.findByEmail(request().username()), created));
        }
    }       
}

