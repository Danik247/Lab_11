import persons.*;
import record.Place;
import helpfulClasses.Crowd;

public class Main {
    public static void main() {
        Man man = new Man("Незнайка", 3);
        Man man2 = new Man("", 4);
        Place bridge = new Place("мост", 2, false, false, true);
        Place certain = new Place("занавес", 3, false, true, false);
        Crowd crow = new Crowd( 12);

        man.changeLocation(bridge);
        man.escapeLocation(bridge);
        man.rushCrowd(crow);
        man.changeLocation(certain);
        crow.play(man);
        man.escapeLocation(certain);
        var a = man.equals(man2);
        System.out.print(a);
    }
}
