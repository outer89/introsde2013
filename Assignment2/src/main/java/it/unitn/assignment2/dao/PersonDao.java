package it.unitn.assignment2.dao;

import it.unitn.assignment2.model.HealthProfile;
import it.unitn.assignment2.model.Measure;
import it.unitn.assignment2.model.MeasureType;
import it.unitn.assignment2.model.Person;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public enum PersonDao {
    instance;
    private Map<String, Person> contentProvider = new HashMap<String, Person>();
    private final String[] nomi = {"Brittni", "Cassie", "Dwight", "Shana", "Emma",
        "Carol", "Georgianne", "Rebekah", "Deangelo", "Clinton",
        //"Micaela", "Geraldo", "Leonor", "Yanira", "Latoyia",
        //"Ignacia", "Ariana", "Raymond", "Stephen", "Reta", "Mattia", "Carlo", "Franco", "Lorenzo", "Massimo",
        "Massimiliano", "Giorgio", "Iolanda", "Michela", "Giorgia", "Franca", "Ilona", "Valentina"};
    private final String[] cognomi = {"North", "Ogden", "Oliver", "Paige", "Parr",
        //"Parsons", "Paterson", "Payne", "Peake", "Peters", "Piper", "Poole", "Powell",
        //"Pullman", "Quinn", "Rampling", "Randall", "Rees", "Reid", "Roberts", "Robertson",
        "Ross", "Russell", "Rutherford", "Sanderson", "Scott", "Rossi", "Pittarello", "DeAgostini", "Agostini", "Ferrari", "Bianchi"};

    private PersonDao() {

        //init dao content
        Integer index = 1;
        Calendar c = new GregorianCalendar();
        for (String nome : nomi) {
            for (String cognome : cognomi) {
                //create person
                Person person = new Person();
                person.setId(index.toString());
                person.setFirstname(nome);
                person.setLastname(cognome);
                person.sethProfile(new HealthProfile());
                person.setBday(generaDate());
                //manage to fill measure values
                //create person measure 10 for each measure type
                List<Measure> l = new LinkedList<Measure>();
                for (MeasureType m : MeasureType.values()) {
                    for (int i = 0; i < 10; i++) {
                        c.set(2011, 0, i);
                        Measure measure = new Measure();
                        measure.setType(m);
                        measure.setMid(Integer.toString(i));
                        switch (m) {
                            case HEIGHT:
                                measure.setValue(valoreAltezza());
                                break;
                            case WEIGHT:
                                measure.setValue(valorePeso());
                                break;
                            default:
                                measure.setValue("");
                                break;
                        }
                        measure.setDate(c.getTime());
                        l.add(measure);
                    }
                }
                person.setListaMisure(l);
                contentProvider.put(index.toString(), person);
                index++;
            }
        }
    }

    private String valorePeso() {
        Double maxheight = 2.54;
        Double minheight = 1.00;
        Double rangeheight = (maxheight - minheight);
        Double res = Math.floor((Math.random() * rangeheight + 1) * 100) / 100;
        return res.toString();
    }

    private String valoreAltezza() {
        Double maxweight = 200.00;
        Double minweight = 50.00;
        Double rangeweight = (maxweight - minweight);
        Double res = Math.floor((Math.random() * rangeweight + 1) * 100) / 100;
        return res.toString();
    }

    private Date generaDate() {
        GregorianCalendar gc = new GregorianCalendar();
        int year = randBetween(1900, 2010);
        gc.set(gc.YEAR, year);
        int dayOfYear = randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);
        return gc.getTime();
    }

    private int randBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public Map<String, Person> getModel() {
        return contentProvider;
    }

    public String getNewID() {
        Integer res = 0;
        for (String string : contentProvider.keySet()) {
            Integer i = new Integer(string);
            if (i > res) {
                res = i;
            }
        }
        res += 1;
        return res.toString();
    }
}
