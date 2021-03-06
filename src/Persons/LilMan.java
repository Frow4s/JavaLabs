package Persons;

import Enums.Condition;
import Enums.Place  ;
import Exception.Pusto  ;
import Objects.Gryadka ;

import java.util.ArrayDeque;
import java.util.concurrent.ConcurrentLinkedDeque;

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

    public String Making(ConcurrentLinkedDeque<Gryadka> gryadka) throws Pusto{
        String out="";
        while (gryadka.peekFirst() != null) { //смотрим элемент с начала очереди
            Gryadka current = gryadka.pollFirst(); //извлекаем элемент с начала очереди и удаляем
            while (current.getCount() > 0) {
                out=out+"Собирает грядку " + current.getName()+"\n";
                current.setCount(current.getCount() - 1); // уменьшаем количество плодов на 1
            }

            out=out+"Грядки " + current.getName() + " закончились"+"\n";
        }
        return out;
        //throw new Pusto("Грядки закончились");*/
    }
}

