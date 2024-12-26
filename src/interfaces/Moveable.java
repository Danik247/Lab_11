package interfaces;
import rec.Place;

public interface Moveable {

    void changeLocation(Place to);

    void escapeLocation(Place from);
}
