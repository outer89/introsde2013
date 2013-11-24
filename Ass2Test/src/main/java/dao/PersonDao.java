package dao;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import model.HealthProfile;
import model.Measure;
import model.Person;

public enum PersonDao {
	instance;

	private Map<String, Person> contentProvider = new HashMap<String, Person>();
        
        private PersonDao() {

		Person pallino = new Person();
		Person pallo = new Person("Pinco","Pallo");
		HealthProfile hp = new HealthProfile(68.0,1.72);
		Person john = new Person("John","Doe",hp);

		pallino.setId("1");
		pallo.setId("2");
		john.setId("3");
		List<Measure> l = new LinkedList<Measure>();
		contentProvider.put("1", pallino);
		contentProvider.put("2", pallo);
		contentProvider.put("3", john);

	}

	public Map<String, Person> getModel() {
		return contentProvider;
	}
}