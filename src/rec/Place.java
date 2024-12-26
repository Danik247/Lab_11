package rec;

import exept.InvalidPlaceException;

public record Place(String name, int hard, Boolean isDown, Boolean isHide, Boolean isUp) {

    public Place {
        if (isDown && isUp) {
            throw new InvalidPlaceException("место которое протеворечит всем законам обнаружено!");
        }
    }
}
