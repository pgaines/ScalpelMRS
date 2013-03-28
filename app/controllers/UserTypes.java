package models;

import java.util.*;

public class UserTypes {
    
    public static List<String> list() {
        List<String> all = new ArrayList<String>();
        all.add("Admin");
        all.add("Doctor");	
        all.add("Nurse");	
        all.add("Patient");	
        all.add("NewUser");			
        return all;
    }
    
}