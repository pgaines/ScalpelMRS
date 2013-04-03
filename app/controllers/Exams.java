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
     * This result directly redirect to application home. Pulls a list of exams filtered by patientName.
     */
    public static Result GO_HOME = redirect(
        routes.Exams.list(0, "patientName", "asc", "", "patientName")
    );
    
    /**
     * Handle default path requests, redirect to computers list
     */
    public static Result index() {
        return GO_HOME;
    }       
	
    /**
     * Display the exam summary
     */  
    public static Result summary(Exam exam) {
		return ok(
			summary.render(
				User.findByEmail(request().username()),
				exam
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
    public static Result list(int page, String sortBy, String order, String filter, String filterable) {
        return ok(
            list.render(
                User.findByEmail(request().username()), 
                Exam.page(page, 10, sortBy, order, filter, filterable),
                sortBy, order, filter, filterable
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
     * Display a blank createExam form.
     */ 
    public static Result createExam() {
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
     * Handle the form submission.
     */
    public static Result submitCreateExam() {
        Form<Exam> filledForm = createExamForm.bindFromRequest();
        
        if(filledForm.hasErrors()) {
            Exam emptyExam = Exam.emptyExam();      
            return badRequest(form.render(User.findByEmail(request().username()), emptyExam, filledForm));
        } else {
            Exam created = filledForm.get();
            return ok(summary.render(User.findByEmail(request().username()), created));
        }
    }       
}

