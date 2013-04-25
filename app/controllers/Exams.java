package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;

import java.util.*;

import models.*;

import views.html.*;
import views.html.exams.*;

/**
 * Manage exams related operations.
 */
@Security.Authenticated(Secured.class)
public class Exams extends Controller {
   /**
     * Defines a form wrapping the Exam class.
     */ 
    final static Form<Exam> createExamForm = form(Exam.class);
 
    /**
     * This result directly redirect to application home. Pulls a list of exams filtered by username.
     */
    public static Result GO_HOME = redirect(
        routes.Exams.viewExams(0, "username", "asc", "", "username")
    );
    
    /**
     * Handle default path requests, redirect to exam list
     */
    public static Result index() {
        return GO_HOME;
    }

    /**
     * Display the exam summary
     */  
    public static Result viewExam(Integer id) {
        return ok(
            viewExam.render(
                User.findByEmail(request().username()),
                Exam.findById(id)
            )
        );
    }       
    
    /**
     * Display the paginated list of exams.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filterable Exam property to which filter is applied    
     * @param filter Filter applied on Filterable
     */
    public static Result viewExams(int page, String sortBy, String order, String filter, String filterable) {
        return ok(
            viewExams.render(
                User.findByEmail(request().username()), 
                Exam.page(page, 10, sortBy, order, filter, filterable),
                sortBy, order, filter, filterable
            )
        );
    }  
	
    /**
     * Display the paginated list of exams.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filterable Exam property to which filter is applied    
     * @param username Filter applied on Filterable
     */
    public static Result viewExamsByUsername(int page, String sortBy, String order, String username, String filterable) {
        return ok(
            viewExams.render(
                User.findByEmail(request().username()), 
                Exam.page(page, 10, sortBy, order, username, filterable),
                sortBy, order, username, filterable
            )
        );
    }  	
    
   /**
     * Display a blank createExam form.
     */ 
    public static Result createExam(Integer id) {
        Exam emptyExam = Exam.emptyExam();  
        return ok(
            form.render(
                User.findByEmail(request().username()),             
                emptyExam,
                createExamForm.fill(emptyExam)
            )
        );  
    }   

    /**
     * Display a form pre-filled with a exam.
     */
    public static Result editExam(Integer id) { 
        return ok(
            form.render(
                User.findByEmail(request().username()),             
                Exam.findById(id),
                createExamForm.fill(Exam.findById(id))
            )
        );  
    }    

    /**
     * Handle the create exam form submission.
     */
    public static Result saveExam(Integer id) {
        Form<Exam> filledForm = form(Exam.class, Exam.All.class).bindFromRequest();
        
        if(filledForm.hasErrors()) {
            Exam emptyExam = Exam.emptyExam();      
            return badRequest(form.render(User.findByEmail(request().username()), emptyExam, filledForm));
        } else {    
            if(id == Exam.incrementId()) {          
                filledForm.get().save();        
            } else {
                filledForm.get().update(id);
                flash("success", "Exam has been updated");  
            }               
            return ok(viewExam.render(User.findByEmail(request().username()), filledForm.get()));
        }
    }      
    
    /**
     * Handle exam deletion
     */
    public static Result deleteExam(Integer id) {
        Exam.findById(id).delete();
        flash("success", "Exam has been deleted");
        return GO_HOME;
    }       
}

