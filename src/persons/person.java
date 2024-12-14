package persons;

import enums.Effect;
import interfaces.check;
import interfaces.consl;
import interfaces.move;
import rec.Place;
import rec.crowd;

import java.util.ArrayList;

public abstract class person implements move, check {

    protected float stamina;//выносливость
    protected float speed;//скорость
    protected float dexterity;//ловкость
    protected float eloquence;//красноречие
    protected String name;//имя
    protected float weight;//вес
    protected float size;//размер
    protected Effect effect;//статус
    ArrayList<Place> lastplaces = new ArrayList<>();
    Place start = new Place("Старт", 0, false, false, false);

    public person(String name, float dexterity, float speed, float stamina, float eloquence, float size, float weight) {
        this.dexterity = dexterity;
        this.speed = speed;
        this.stamina = stamina;
        this.eloquence = eloquence;
        this.name = name;
        this.weight = weight;
        this.size = size;
        consl.hello(name, dexterity, speed, stamina, eloquence, size, weight);
        lastplaces.add(start);
    }

    abstract void boringSt();

    public float[] getStats() {
        if (effect == Effect.isAbove | effect == Effect.isHided) {
            return new float[]{speed, stamina, (float) (dexterity * 0.7), (float) (eloquence * 1.5), weight, size};
        }
        if (effect == Effect.isDropped) {
            return new float[]{(float) (speed * 0.5), stamina, (float) (dexterity * 0.5), eloquence, weight, size};
        }
        if (effect == Effect.isCoughted | effect == Effect.isConfused) {
            return new float[]{(float) (speed * 0.1), stamina, dexterity, eloquence, weight, size};
        }
        return new float[]{speed, stamina, dexterity, eloquence, weight, size};
    }
    public void rushCrowd(crowd crowd){
        boolean success = checkit(60 - (crowd.count() - (this.stamina + this.dexterity) * 3));
        if (getStats()[1] >= (int) crowd.count()/3){
            if (success){
                consl.describe("ты прополз через толпу из "+ crowd.count()+ " " + crowd.cont().name + "-ов");
                changeSt((int) crowd.count()/3);
            }
            else {
                consl.describe("тебя вытолкнули обратно, попробуй снова");
                rushCrowd(crowd);
            }
        }
        else {
            consl.describe("ты все-таки решил оценить свои силы и");
            rest((int) crowd.count()/3);
            rushCrowd(crowd);
        }
    }
    public void escape(Place place) {
        boolean success = checkit(60 - (place.hard() * 12 - (this.stamina + this.dexterity) * 3));
        if (getStats()[1] >= place.hard()) {
            if (place.isDown()) {
                if (success) {
                    consl.describe("Ты все-таки вылез из " + place.name() + ", но потратил " + place.hard() + " выносливости");
                    changeSt(place.hard() * -1);
                    effect = null;
                } else {
                    consl.describe("Эээ... Ты уныло соскользнул в самый низ и потратил " + place.hard() / 2 + " выносливости");
                    changeSt((float) place.hard() / -2);
                    escape(place);
                }
            } else if (place.isUp()) {
                if (success) {
                    consl.describe("Ты все-таки спустился, но потратил " + place.hard() / 2 + " выноосливости");
                    changeSt((float) place.hard() / -2);
                    effect = null;
                } else {
                    consl.describe("Хаха... Ты грохнулся вниз и потратил " + place.hard() + " выносливости");
                    changeSt((float) place.hard() * -1);
                }
            } else if (place.isHide()){
                consl.describe("ты выбрался оттуда...");
                changeSt((float) place.hard() * -1);
                effect = null;
            }
        } else {
            consl.describe("Что-то ты устал, попробуй отдохнуть");
            rest(place.hard() * 3);
            escape(place);
        }
    }
    @Override
    public void changeLoc(Place place) {
        boolean success = checkit(60 - (place.hard() * 12 - (this.stamina + this.dexterity) * 4));
        if (place.hard() < stamina) {
            if (place.isUp()) {
                if (success) {
                    consl.describe("Ты залез на " + place.name() + ", но потратил " + place.hard() + " выноосливости");
                    changeSt((float) place.hard() / -2);
                    lastplaces.add(place);
                    effect = Effect.isAbove;
                } else {
                    consl.describe("Эээ... Ты уныло соскользнул в самый низ, еще и потратил " + place.hard() + " выносливости");
                    changeSt((float) place.hard() / -1);
                    changeLoc(place);
                    return;
                }
            } else if (place.isDown()) {
                if (success) {
                    consl.describe("ты аккуратно спустился в " + place.name() + ", хоть и потратил " + place.hard() + " выносливости");
                    changeSt((float) place.hard() / -1);
                } else {
                    consl.describe("Ты полетел в самый низ, зато потратил всего " + place.hard() + " выносливости");
                    changeSt((float) place.hard() / -2);
                }
                lastplaces.add(place);
                effect = Effect.isDropped;
            } else if (place.isHide()) {
                if (success) {
                    consl.describe("Ты решил спрятаться \nПоздравляю тебя не видно, теперь ты " + place.name());
                    effect = Effect.isHided;
                } else {
                    consl.describe("Ты запутался, молодец");
                    effect = Effect.isCoughted;
                }

            } else {
                consl.describe(name + " пришел к такому месту как " + place.name() + " почти не потратив выносливости");
                changeSt((float) place.hard() / -10);
            }
            lastplaces.add(place);
        } else {
            consl.describe("Что-то ты устал, попробуй отдохнуть");
            rest(place.hard() * 3);
            changeLoc(place);
        }
    }
    public boolean dodge(person att){
        boolean success = checkit(50 + (this.stamina + this.dexterity) * 2);
        consl.describe(success ? "увернулся" : att.name + " попал в тебя");
        return success;
    }



    public void rest(float timeHour) {
        changeSt(timeHour * 2);
        consl.describe("Ты отдыхал " + timeHour + " часов, и восстановил " + timeHour * 2 + " выносливости");
    }

    public void changeSt(float incur) {
        this.stamina += incur;
        if (this.stamina < 0) {
            this.stamina = 0;
        }
        consl.describe("Теперь у тебя " + stamina + " выносливости");
    }

    @Override
    public String toString() {
        return "person{" +
                "name='" + name + '\'' +
                ", dexterity=" + dexterity +
                ", eloquence=" + eloquence +
                ", ...}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        person guest = (person) obj;
        return (name != null && name.equals(guest.name))
                && (dexterity == guest.dexterity) && (stamina == guest.stamina)
                && (speed == guest.speed) && (size == guest.size)
                && (eloquence == guest.eloquence) && (weight == guest.weight);
    }

    @Override
    public int hashCode() {
        int total = 52;
        total = total * 52 + (name == null ? 0 : name.hashCode());
        total = total * 52 + (int) speed;
        total = total * 52 + (int) stamina;
        total = total * 52 + (int) size;
        total = total * 52 + (int) dexterity;
        total = total * 52 + (int) eloquence;
        total = total * 52 + (int) weight;
        return total;
    }



    @Override
    public boolean checkit(float chance) {
        return (chance / 100 > Math.random());
    }
}
