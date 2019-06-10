import Enums.Condition;
import Enums.Place;
import Objects.Gryadka;
import Objects.House;
import Persons.LilMan;
import Exception.Pusto;
import Persons.Man ;
import Persons.Neznaika;
import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Output {
    public Output(){

    }

    public String tellTheStory(ConcurrentLinkedDeque <Gryadka> gryadkas)  {
        String story="";
        LilMan lilMan = new LilMan("Коротышка", Condition.NONE,Place.CucumberGryadka,"собирает");
        story=story+lilMan.getAction()+"\n";
        try {
            story=story+lilMan.Making(gryadkas)+"\n";
        } catch (Pusto e) {
            story=story+(e.getMessage())+"\n";
        }

        Neznaika Neznaika=new Neznaika("Neznaika",Condition.NONE,Place.CucumberGryadka,"смотрит",2);
        story=story+"Незнайка и Фикс появились рядом с грядками"+"\n";

        Man Fix=new Man("Fix",Condition.NONE,Place.UNKNOWN,"Фикс ткнул незнайку палкой"){
            String predmet="палка";
        };
        Neznaika.setPlace(Place.CucumberGryadka);
        LilMan.TalkinLilMan shutup=new LilMan.TalkinLilMan("Коротышка", Condition.NONE,Place.CucumberGryadka,"Коротышка собирает");
        story=story+shutup.getAction()+"\n";
        story=story+Fix.getAction()+"\n";
        Neznaika.setAction("Незнайка поднялся на гору");
        story=story+Neznaika.getAction()+"\n";
        Neznaika.setAction("Увидел дом");
        story=story+Neznaika.getAction()+"\n";
        House house=new House(2,"с большой открытой верандой");
        story=story+(house.getLooking());
        return story;
}


}
