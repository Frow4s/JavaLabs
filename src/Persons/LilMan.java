package Persons;

import Enums.Condition;
import Enums.Place;
import Exception.Pusto;
import Objects.Gryadka;

public class LilMan extends Man {
    public LilMan(String name, Condition condition, Place place, String action) {
        super("коротышка",Condition.NONE, Place.UNKNOWN, "Коротышка собирает плоды");
    }
    public static class TalkinLilMan extends LilMan{

        public TalkinLilMan(String s, Condition condition, Place cucumberGryadka, String s1) {
            super(s, condition, cucumberGryadka, s1);
        }

        public void setAction(String action) {
            this.action = action;
        }

        public String getAction(){
            if (Neznaika.place==Place.UNKNOWN || Neznaika.place==Place.StrawberryGryadka || Neznaika.place==Place.PomidorGryadka){
                return ("Один,увидев Незнайку, сказал:Коротышки молча принялись за работу");
            }
            return("");
        }
    }

    public void setAction(String action) {
        super.setAction(action);
    }

    @Override
    public String getAction() {
        return super.getAction();
    }

    public void Making() throws Pusto{
        if (Gryadka.Cucumber.count > 0) {
            System.out.print("Cобирает огурцы");
            Gryadka.Cucumber.count -= 1;
        } else {
            throw new Pusto("Грядки с огурцами закончились");
        }
        if (Gryadka.Pomidor.count > 0 && Gryadka.Cucumber.count == 0) {
            System.out.print("Собирает помидоры");
        } else {
            throw new Pusto("Грядки с помидорами закончились");
        }
        if(Gryadka.Pomidor.count==0 && Gryadka.Cucumber.count == 0 && Gryadka.Strawberry.count>0){
            System.out.print("Собрает клубнику");
        }
        else{
            throw new Pusto("Грядки закончились");
        }
    }
}

