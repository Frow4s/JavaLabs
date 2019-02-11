package Persons;

import Enums.Condition;
import Enums.Place;
import Interfaces.ForPersons;
import Exception.Pusto;

public abstract class Man implements ForPersons {
    private String name;
    private Condition condition;
    public static Place place;
    public String action;

    public Man(String name,Condition condition,Place place,String action) {
        this.name = name;
        this.condition = condition;
        this.place = Place.UNKNOWN;
        this.action = action;
    }
    public void setAction(String action) {
        this.action=action;
    }

    public void setPlace(Place place) {
        this.place = place;
    }


    public String getPlace() {
        return place.name();
    }
    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Condition getCondition() {
        return condition;
    }

}
