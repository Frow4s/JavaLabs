import Enums.Condition;
import Enums.Place;
import Objects.Gryadka;
import Objects.House;
import Persons.LilMan;
import Exception.Pusto;
import Persons.Man ;
import Persons.Neznaika;
import java.util.ArrayDeque;

public class Output {
    public Output(){

    }

    public void tellTheStory(ArrayDeque<Gryadka> gryadkas)  {

        LilMan lilMan = new LilMan("Коротышка", Condition.NONE,Place.CucumberGryadka,"собирает");
        System.out.println(lilMan.getAction());
        try {
            lilMan.Making(gryadkas);
        } catch (Pusto e) {
            System.out.println(e.getMessage());
        }

        Neznaika Neznaika=new Neznaika("Neznaika",Condition.NONE,Place.CucumberGryadka,"смотрит",2);
        System.out.println("Незнайка и Фикс появились рядом с грядками");

        Man Fix=new Man("Fix",Condition.NONE,Place.UNKNOWN,"Фикс ткнул незнайку палкой"){
            String predmet="палка";
        };
        Neznaika.setPlace(Place.CucumberGryadka);
        LilMan.TalkinLilMan shutup=new LilMan.TalkinLilMan("Коротышка", Condition.NONE,Place.CucumberGryadka,"Коротышка собирает");
        System.out.println(shutup.getAction());
        System.out.println(Fix.getAction());
        Neznaika.setAction("Незнайка поднялся на гору");
        System.out.println(Neznaika.getAction());
        Neznaika.setAction("Увидел дом");
        System.out.print(Neznaika.getAction());
        House house=new House(2,"с большой открытой верандой");
        System.out.println(house.getLooking());
    }

}
