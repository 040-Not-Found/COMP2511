package thrones_factoryPattern;

public class Game {
    
    public static void main(String[] args) {
        King king = CharacterFactory.createKing();
        Queen queen = CharacterFactory.createQueen();
        Knight knight = CharacterFactory.createKnight();
        Dragon dragon = CharacterFactory.createDragon();
        Dragon dragon2 = (Dragon) CharacterFactory.createCharacter(CharacterType.DRAGON);


        knight.attack(dragon);
    }

}