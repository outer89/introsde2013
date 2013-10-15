import java.util.HashMap;
import java.util.Map;

import pojos.HealthProfile;
import pojos.Person;


public class HealthProfileReader {
	
	public static Map<String,Person> database = new HashMap<String,Person>();
	
	static
    {
    	Person pallino = new Person();
		Person pallo = new Person("Pinco","Pallo");
		HealthProfile hp = new HealthProfile(80.0,1.80);
		Person john = new Person("L","G",hp);
		
		database.put(pallino.getFirstname()+" "+pallino.getLastname(), pallino);
		database.put(pallo.getFirstname()+" "+pallo.getLastname(), pallo);
		database.put(john.getFirstname()+" "+john.getLastname(), john);
    }
	/**
	 * The health profile reader gets information from the command line about
	 * weight and height and calculates the BMI of the person based on this 
	 * parameters
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//initializeDatabase();
		int argCount = args.length;
		if (argCount == 0) {
			System.out.println("I cannot create people out of thing air. Give me at least a name and lastname.");
		} else if (argCount < 3) {
			System.out.println("Are you sure you gave me a first, lastname and paramter to know?");
		} else if (argCount == 3) {
			String fname = args[0];
			String lname = args[1];
            String param = args[2];
			// read the person from the DB
			Person p= (Person)database.get(fname+" "+lname);
			if (p!=null) {
                
                String res = "";
                if(param.equals("weight")){
                    res = Double.toString(p.gethProfile().getWeight());
                }
                else if(param.equals("height")){
                    res = Double.toString(p.gethProfile().getHeight());
                }
                else if(param.equals("bmi")){
                    res = Double.toString(p.gethProfile().getBMI());
                }
                else{
                    res="is not a parameter of the health profile";
                }
				System.out.println(fname+" "+lname+"'s health profile " + param + " you asked is: " + res);
			} else {
				System.out.println(fname+" "+lname+" is not in the database");
			}
		}
		// add the case where there are 3 parameters, the third being a string that matches "weight", "height" or "bmi"
	}
	
	//public static void initializeDatabase() {
	//	Person pallino = new Person();
	//	Person pallo = new Person("Pinco","Pallo");
	//	HealthProfile hp = new HealthProfile(68.0,1.72);
	//	Person john = new Person("John","Doe",hp);
	//	
	//	database.put(pallino.getFirstname()+" "+pallino.getLastname(), pallino);
	//	database.put(pallo.getFirstname()+" "+pallo.getLastname(), pallo);
	//	database.put(john.getFirstname()+" "+john.getLastname(), john);
	//}
}