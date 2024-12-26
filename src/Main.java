import persons.*;
import rec.Place;
import helpfulClasses.Crowd;

public class Main {
    public static void main(String[] args) {
        Goat got = new Goat(3);
        Man man = new Man("Незнайка", 3);
        Place bridge = new Place("мост", 2, false, false, true);
        Place certain = new Place("занавес", 3, false, true, false);
        Crowd crow = new Crowd(got, 12);
        man.changeLocation(bridge);
        man.escapeLocation(bridge);
        man.rushCrowd(crow);
        man.changeLocation(certain);
        crow.play(man);
        man.escapeLocation(certain);
    }
}


