import persons.*;
import rec.Place;
import rec.crowd;

public class Main {
    public static void main(String[] args) {
        goat got = new goat(3);
        man man = new man("Незнайка",3);
        Place bridge = new Place("мост",2,false,false,true);
        Place certain = new Place("занавес",3,false,true,false);
        crowd crow = new crowd(got ,12);
        man.changeLoc(bridge);
        man.escape(bridge);
        man.rushCrowd(crow);
        man.changeLoc(certain);
        crow.play(man);
        man.escape(certain);
    }
}


