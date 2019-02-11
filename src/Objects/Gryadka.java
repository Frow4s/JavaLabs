package Objects;

import Enums.Place;
import Interfaces.ForAll;
import Interfaces.ForGryadka;


public abstract class Gryadka implements  ForGryadka {
    private String name;
    private String type;
    public static int count;

    public Gryadka(String name,int count,String type){
        this.name="грядка";
        this.count=count;
        this.type=type;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }


    public class Strawberry extends Gryadka{
        public Strawberry(String name,int count,String type){
            super(name,10,"с клубникой");
        }
     }
    public class Pomidor extends Gryadka{
        public Pomidor(String name,int count,String type){
            super(name,0,"с помидорами");
        }
    }
    public class Cucumber extends Gryadka{
        public Cucumber(String name,int count,String type){
            super(name,0,"с огурцами");
        }
    }
}
