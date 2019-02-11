package Persons;

import Enums.Condition;
import Enums.Place;

public class Neznaika extends Man {
    private int days;

    public Neznaika(String name, Condition condition, Place place, String action, int days) {
        super(name, condition,place, action);

        this.days = days;
    }

    public void setAction(String action) {
        setPlace(Place.Mountain);
        if (place == Place.Mountain && days < 3) {
            setCondition(Condition.IMPRESSED);
        } else {
            setCondition(Condition.NotIMPRESSED);
        }
        super.setAction(action);
    }

}