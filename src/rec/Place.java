package rec;

import exept.myEror;

public record Place(String name, int hard, Boolean isDown, Boolean isHide, Boolean isUp) {
    public Place {
        if (isDown && isUp) {
            throw new myEror("место которое протеворечит всем законам обнаружено!");
        }
    }
}
