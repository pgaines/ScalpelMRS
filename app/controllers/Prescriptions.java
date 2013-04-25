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
        routes.Prescriptions.viewPrescriptions(0, "id", "asc", "", "patientName")
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
    public static Result viewPrescription(Integer id) {
		return ok(
			viewPrescription.render(
				User.findByEmail(request().username()),
				Prescription.findById(id)
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
    public static Result viewPrescriptions(int page, String sortBy, String order, String filter, String filterable) {
        return ok(
            viewPrescriptions.render(
                User.findByEmail(request().username()), 
                Prescription.page(page, 10, sortBy, order, filter, filterable),
                sortBy, order, filter, filterable
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
     * @param username Filter applied on Filterable
     */
    public static Result viewPrescriptionsByUsername(int page, String sortBy, String order, String username, String filterable) {
        return ok(
            viewPrescriptions.render(
                User.findByEmail(request().username()), 
                Prescription.page(page, 10, sortBy, order, username, filterable),
                sortBy, order, username, filterable
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
    public static Result createPrescription(Integer id) {
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
    public static Result savePrescription(Integer id) {
        Form<Prescription> filledForm = form(Prescription.class, Prescription.All.class).bindFromRequest();
        
        if(filledForm.hasErrors()) {
            Prescription emptyPrescription = Prescription.emptyPrescription();      
            return badRequest(form.render(User.findByEmail(request().username()), emptyPrescription, filledForm));
        } else {
		    if(id == Prescription.incrementId()) {
			    filledForm.get().save();		
			} else {
				filledForm.get().update(id);
				flash("success", "Prescription has been updated");			
			}
			return ok(viewPrescription.render(User.findByEmail(request().username()), filledForm.get()));
        }
    }        	
	
    /**
     * Handle prescription deletion
     */
    public static Result deletePrescription(Integer id) {
        Prescription.findById(id).delete();
        flash("success", "Prescription has been deleted");
        return GO_HOME;
    }			
}

