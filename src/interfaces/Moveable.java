package interfaces;
import record.Place;

public interface Moveable {

    void changeLocation(Place to);

    void escapeLocation(Place from);

}
