package it.unitn.assignment2.dao;

import it.unitn.assignment2.model.HealthProfile;
import it.unitn.assignment2.model.Measure;
import it.unitn.assignment2.model.Person;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public enum PersonDao {

    instance;

    private Map<String, Person> contentProvider = new HashMap<String, Person>();

    private PersonDao() {

        Person pallino = new Person();
        Person pallo = new Person("Pinco", "Pallo");
        HealthProfile hp = new HealthProfile(68.0, 1.72);
        Person john = new Person("John", "Doe", hp);
        Calendar c = Calendar.getInstance();
        c.set(1989, 07, 11);
        pallino.setId("1");
        pallo.setId("2"); c.set(1992, 07, 11); pallo.setBday(c.getTime());
        john.setId("3"); c.set(1987, 07, 11); john.setBday(c.getTime());

        List<Measure> l = new LinkedList<Measure>();

        contentProvider.put("1", pallino);
        contentProvider.put("2", pallo);
        contentProvider.put("3", john);

    }

    public Map<String, Person> getModel() {
        return contentProvider;
    }
    public String getNewID(){
        Integer res = 0;
        for (String string : contentProvider.keySet()) {
            Integer i = new Integer(string); 
            if(i>res){
                res = i;
            }
        }
        res+=1;
        return res.toString();
    }
}
