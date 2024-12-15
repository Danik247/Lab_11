package rec;

import exept.InvalidPlaseDescription;

public record Place(String name, int hard, Boolean isDown, Boolean isHide, Boolean isUp) {
    public Place {
        if (isDown && isUp) {
            throw new InvalidPlaseDescription("место которое протеворечит всем законам обнаружено!");
        }
    }
}
