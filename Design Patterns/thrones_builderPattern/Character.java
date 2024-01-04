package thrones_builderPattern;

import java.util.List;

public abstract class Character {

    private int healthPoints;

    private int x, y;
    public Character(int x, int y) {
        healthPoints = 100;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
    
    /**
     * Cause this character the given amount of damage.
     *
     * @param points
     */
    public void damage(int points) {
        healthPoints -= points;
    }
    
    /**
     * Can this character move by the given amount along the x and y axes.
     *
     * @param x
     * @param y
     * @return True if they can move by that amount, false otherwise
     */
    public abstract boolean canMove(int dx, int dy);

    /**
     * This character attacks the given victim, causing them damage according to
     * their rules.
     *
     * @param victim
     */
    public abstract void attack(Character character);

    /**
     * Attempts to make a move to a square in the game, given all of the characters
     * If it is an invalid move, returns INVALID.
     * If it is a valid move but the square is occupied, attacks the character and returns ATTACK
     * If it is a valid move and the square is free, returns SUCCESS
     */
    public MoveResult makeMove(int x, int y, List<Character> characters) {
        // This function uses two abstract methods (AKA 'hook methods') which the concrete classes must implement
            // template pattern!!!!!!! canMove()
            System.out.println("can move: " + x + y + canMove(this.x - x, this.y - y));
            if (!canMove(this.x - x, this.y - y)) {
                return MoveResult.INVALID;
            }
    
            for (Character character : characters) {
                if (character != this && character.getX() == x && character.getY() == y) {
    
                    // template pattern!!!!!!! attack()
                    attack(character);
                    return MoveResult.ATTACK;
                }
            }
            System.out.println(x);
            System.out.println(y);

            this.x = x;
            this.y = y;
    
            return MoveResult.SUCCESS;
    }

    public String toString() {
        return getClass().getSimpleName() + " at (" + getX() + ", " + getY() + "), health = " + healthPoints;
    }
}
