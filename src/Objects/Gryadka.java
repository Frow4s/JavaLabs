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


    public static class Strawberry extends Gryadka{
        public Strawberry(int count,String type){
            super(count,"с клубникой");
        }
    }
    public static class Pomidor extends Gryadka{
        public Pomidor(int count,String type){
            super(count,"с помидорами");
        }
    }
    public static class Cucumber extends Gryadka{
        public Cucumber(int count,String type){
            super(count,"с огурцами");
        }
    }
}
