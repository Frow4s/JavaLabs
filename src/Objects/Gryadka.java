package Objects;

import Enums.Place;
import Interfaces.ForAll;
import Interfaces.ForGryadka;


public class Gryadka implements ForGryadka {
    private String type;
    private int count;

    public Gryadka(int count, String type){
        this.type=type;
        this.count=count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    /*public void setName(String name) {
        this.name = name;
    }*/

    public String getName() {
        return type;
    }

    public int getCount() {
        return count;
    }

    public String getType() {
        return type;
    }



}
