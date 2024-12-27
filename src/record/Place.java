package record;

import exept.InvalidPlaceException;

public record Place(String name, int difficulty, Boolean isDown, Boolean isHide, Boolean isUp) {

    public Place {
        if (isDown && isUp) {
            throw new InvalidPlaceException("место которое протеворечит всем законам обнаружено!");
        }
    }
}
