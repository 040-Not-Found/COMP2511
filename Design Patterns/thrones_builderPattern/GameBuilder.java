package thrones_builderPattern;

public class GameBuilder {
    private int width;
    private int height;
    private Game game;

    public GameBuilder(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean canAdd(int x, int y) {
        return (x < width && y < height);
    }

    public GameBuilder createGame() {
        if (game == null) {
            this.game = new Game();
        }
        return this;
    }

    public GameBuilder addQueen(int x, int y) {
        if (canAdd(x, y))
            this.game.addCharacter(new Queen(x, y));
        return this;
    }

    public GameBuilder addKing(int x, int y) {
        if (canAdd(x, y))
            this.game.addCharacter(new King(x, y));
        return this;
    }

    public GameBuilder addDragon(int x, int y) {
        if (canAdd(x, y))
            this.game.addCharacter(new Dragon(x, y));
        return this;
    }

    public String toString() {
        return game.toString();
    }

    public static void main(String[] args) {
        GameBuilder gb = new GameBuilder(5, 5)
                            .createGame()
                            .addQueen(1,1)
                            .addKing(2,2)
                            .addDragon(3,3);
        System.out.println(gb.toString());
    }
}
