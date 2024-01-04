package thrones_factoryPattern;
public abstract class CharacterFactory {
    public static Character createCharacter(CharacterType type) {
        switch (type) {
            case KING:
                return new King();
            case QUEEN:
                return new Queen();
            case KNIGHT:
                return new Knight();
            case DRAGON:
                return new Dragon();
            default:
                return null;
        }
    }

    public static King createKing() {
        return new King();
    }

    public static Queen createQueen() {
        return new Queen();
    }

    public static Knight createKnight() {
        return new Knight();
    }

    public static Dragon createDragon() {
        return new Dragon();
    }
}
