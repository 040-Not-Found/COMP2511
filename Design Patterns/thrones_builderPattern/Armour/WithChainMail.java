package thrones_builderPattern.Armour;

import thrones_builderPattern.Character;
import thrones_builderPattern.CharactorDecorator;


public class WithChainMail extends CharactorDecorator {

    public WithChainMail(Character character) {
        super(character);
    }

    @Override
    public void damage(int points) {
        super.getCharacterBase().damage(Math.floorDiv(points, 2));
    }
}
