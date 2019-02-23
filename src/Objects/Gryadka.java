package Objects;

import Enums.Place;
import Interfaces.ForAll;
import Interfaces.ForGryadka;


public class Gryadka implements ForGryadka {
    private String name;
    private String type;
    private int count;

    public Gryadka(String name, int count, String type){
        this.name="грядка";
        this.count=count;
        this.type=type;
    }

    public Gryadka(int count, String type){
        this.type=type;
        this.count=count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return type;
    }

    public int getCount() {
        return count;
    }


    public static class Strawberry extends Gryadka{
        public Strawberry(String name,int count,String type){
            super(name,count,"с клубникой");
        }
    }
    public static class Pomidor extends Gryadka{
        public Pomidor(String name, int count,String type){
            super(name,count,"с помидорами");
        }
    }
    public static class Cucumber extends Gryadka{
        public Cucumber(String name,int count,String type){
            super(name,count,"с огурцами");
        }
    }
}
