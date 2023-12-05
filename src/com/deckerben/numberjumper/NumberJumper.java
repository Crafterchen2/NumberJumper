package com.deckerben.numberjumper;

import java.awt.*;
import java.util.Random;

/**
 * This is the complete game logic for the game "NumberJumper".
 * <h1>
 *     Game
 * </h1>
 * <h2>
 *     What is "NumberJumper"?
 * </h2>
 * NumberJumper is game about jumping on a grid with numbers a such a way, that the accumulated value
 * of every jumped number is equal to a target score when entering the goal area.
 * <h2>
 *     Rules
 * </h2>
 * The player can always move one cell at a time and can only enter directly adjacent cells. Every cell can be
 * entered an infinite amount of times. The player starts in a start area and can move sideways in this area as often
 * as the player wants without accumulating any points, but this area can't be re-entered once it's left.
 * The player can not leave the grid through its sides. There can be special cells on the grid that can restrict
 * the players movement by not letting the player enter or leave the cell through a specific direction. These special
 * cells can also affect the players score in various ways. Once the goal area on the other side of the grid,
 * opposite to the start area, has been entered, it can't be left anymore and the game is over. The player has won,
 * if their accumulated points equal the target score.
 * <h1>
 *     Technicalities
 * </h1>
 * <h2>
 *     Why interface?
 * </h2>
 * The game logic is written in such a way, that it is fully deterministic and only depends on the abstract method's
 * return values. This enables many different uses and implementations, for example as a console game or for a UI.
 * <h2>
 *     Methods to implement
 * </h2>
 * The complete game logic is already provided and doesn't need to be implemented. What needs to be implemented is
 * every abstract method. Once this is done, the game is technically functional. Gathering the user input and displaying
 * the information of the game is also up to the implementor.
 * <h2>
 *     Controlling the game logic
 * </h2>
 * Once everything is implemented, only the {@code jump(Direction dir)} method needs to be called if a jump should occur.
 * The location of the player is automatically adjusted and does not to be changed by the implementor if the default
 * behaviour should be preserved. The method {@code jump(Direction dir)} returns the new score and is the only thing that
 * may need to be saved between jumps. For the intended default behaviour should this new score be returned by
 * {@code getCurrentPoints()} to ensure the correct accumulation of points.
 *
 * @version     05 Dec 2023
 * @author      Benjamin Decker (aka. Crafterchen2)
 * @see NumberField
 * @see SpecialField
 * @see Direction
 */
public interface NumberJumper {

    /**
     * Read-Only reference to the current points of the player.
     * @return The points of the player.
     */
    int getCurrentPoints();

    /**
     * Read-Only reference to the required points needed to win.
     * @return The points needed to win.
     */
    int getTargetPoints();

    /**
     * Reference to the current player location. Modification of the location is already handled
     * by {@code jump(Direction dir)} and does not need to be modified by the implementor to
     * achieve the intended default behaviour.
     * @return The current player location.
     */
    Point getCurrentPlayerLoc();

    /**
     * Read-Only reference to the play field. It is recommended not to change to contents of the play field
     * while a game is running. The default implementation does not modify the contents of the play field.
     * The outer array represents the rows and the inner array represents the columns of the play field.
     * @return The play field to be played on.
     */
    NumberField[][] getPlayField();

    /**
     * Returns a {@code Dimension} object that represents the size of {@code getPlayField()}. {@code width} represents
     * the amount of columns and {@code height} the amount of rows.
     * @return The size of {@code getPlayField()}.
     * @see #getPlayField()
     */
    default Dimension getFieldSize(){
        return new Dimension(getPlayField().length,getPlayField()[0].length);
    }

    /**
     * Convenience method.
     * @return The field at {@code getCurrentPlayerLoc()}.
     * @throws PlayerOutOfBoundsException If {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @see #getFieldAtPoint(Point)
     * @see #getCurrentPlayerLoc()
     */
    default NumberField getFieldUnderPlayer() throws PlayerOutOfBoundsException{
        return getFieldAtPoint(getCurrentPlayerLoc());
    }

    /**
     * Accesses {@code getPlayField()} and the field referenced by {@code point}. {@code point.y} represents the
     * row and {@code point.x} represents the column.
     * @param point Which field should be returned.
     * @return The field targeted by {@code point}.
     * @throws PlayerOutOfBoundsException If {@code point} is outside of {@code getFieldSize()}.
     * @see #getPlayField()
     */
    default NumberField getFieldAtPoint(Point point) throws PlayerOutOfBoundsException{
        PlayerOutOfBoundsException.checkThrow(point,getFieldSize());
        return getPlayField()[point.y][point.x];
    }

    /**
     * Convenience method.
     * @param dir Which direction should be returned.
     * @return The field in {@code dir} relative to {@code getCurrentPlayerLoc()}.
     * @throws PlayerOutOfBoundsException If {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @throws IllegalDirectionException If the field in {@code dir} relative to {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @see #getFieldAtPoint(Point)
     */
    default NumberField getFieldInDirection(Direction dir) throws IllegalDirectionException, PlayerOutOfBoundsException {
        PlayerOutOfBoundsException.checkThrow(getCurrentPlayerLoc(),getFieldSize());
        IllegalDirectionException.checkThrow(dir,getCurrentPlayerLoc(),getFieldSize());
        return getPlayField()[getCurrentPlayerLoc().y + dir.rowOffset][getCurrentPlayerLoc().x + dir.columnOffset];
    }

    /**
     * Performs the jump. Checks whether {@code getCurrentPlayerLoc()} can leave the current field in {@code dir},
     * enter the targeted field by {@code dir} and if the goal has not been entered. If all criteria are met,
     * then the player is moved to the targeted field by {@code dir}. If the player is not in the start- or goal area,
     * the entered field's {@code manipulate(int points)} method is called.
     * @param dir The direction in which to jump.
     * @return The result of the entered field's {@code manipulate(int points)} method.
     * @throws PlayerOutOfBoundsException If {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @throws IllegalDirectionException If the field in {@code dir} relative to {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @throws CanNotLeaveException If the field the player is standing on does not allow exiting the field in {@code dir}.
     * @throws CanNotEnterException if the field targeted by {@code dir} does not allow entering the field from {@code dir}.
     * @throws AlreadyLeftStartException If the player attempts to re-enter the start region after it has already been left.
     * @throws AlreadyInGoalException If {@code jump(Direction dir)} gets called while {@code isInGoal()} returns true.
     */
    default int jump(Direction dir) throws PlayerOutOfBoundsException, IllegalDirectionException, CanNotLeaveException, CanNotEnterException, AlreadyLeftStartException, AlreadyInGoalException {
        PlayerOutOfBoundsException.checkThrow(getCurrentPlayerLoc(),getFieldSize());
        if (!isInStart() && dir == Direction.SOUTH && getCurrentPlayerLoc().y == 0) throw new AlreadyLeftStartException();
        NumberField srcField = (getCurrentPlayerLoc().y < 0) ? null : getFieldUnderPlayer();
        if (!isInGoal()) {
            IllegalDirectionException.checkThrow(dir,getCurrentPlayerLoc(),getFieldSize());
            getCurrentPlayerLoc().translate(dir.columnOffset, dir.rowOffset);
        }   else throw new AlreadyInGoalException();
        if (!isInGoal() && !isInStart()) {
            if (srcField != null) {
                if (isSpecial(srcField)) {
                    SpecialField specialField = (SpecialField) srcField;
                    if (!specialField.canLeave(dir)) throw new CanNotLeaveException(dir);
                }
            }
            NumberField targetField = getFieldUnderPlayer();
            if (isSpecial(targetField)) {
                SpecialField specialField = (SpecialField) targetField;
                if (!specialField.canEnter(dir.getOpposing())) throw new CanNotEnterException(dir);
            }
            return targetField.manipulate(getCurrentPoints());
        } else {
            return getCurrentPoints();
        }
    }

    /**
     * Checks whether the field at {@code point} is an instance of {@code SpecialField}.
     * @param point Which field should be checked.
     * @return {@code true} if field targeted by {@code point} is {@code instanceof SpecialField}.
     * @throws PlayerOutOfBoundsException If {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @see #isSpecial(NumberField)
     */
    default boolean isSpecial(Point point) throws PlayerOutOfBoundsException {
        return isSpecial(getFieldAtPoint(point));
    }

    /**
     * Checks whether {@code field} is an instance of {@code SpecialField}.
     * @param field The field to be checked.
     * @return {@code true} if {@code field} is {@code instanceof SpecialField}.
     * @see #isSpecial(Point)
     */
    default boolean isSpecial(NumberField field){
        return field instanceof SpecialField;
    }

    /**
     * Checks whether the game is won or not. The default implementation returns true if {@code isInGoal()} is true
     * and {@code getCurrentPoints() == getTargetPoints()}.
     * @return {@code true} if the player has won.
     * @throws PlayerOutOfBoundsException If {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @see #isInGoal()
     */
    default boolean isWin() throws PlayerOutOfBoundsException {
        return isInGoal() && getCurrentPoints() == getTargetPoints();
    }

    /**
     * Checks whether the player is in the goal area or not.
     * @return {@code true} if {@code getCurrentPlayerLoc().y == getFieldSize().height}.
     * @throws PlayerOutOfBoundsException If {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @see #isWin()
     * @see #isInStart()
     */
    default boolean isInGoal() throws PlayerOutOfBoundsException {
        PlayerOutOfBoundsException.checkThrow(getCurrentPlayerLoc(),getFieldSize());
        return getCurrentPlayerLoc().y == getFieldSize().height;
    }

    /**
     * Checks whether the player is in the start area or not.
     * @return {@code true} if {@code getCurrentPlayerLoc().y == -1}.
     * @throws PlayerOutOfBoundsException If {@code getCurrentPlayerLoc()} is outside of {@code getFieldSize()}.
     * @see #isInGoal()
     */
    default boolean isInStart() throws PlayerOutOfBoundsException {
        PlayerOutOfBoundsException.checkThrow(getCurrentPlayerLoc(),getFieldSize());
        return getCurrentPlayerLoc().y == -1;
    }

    /**
     * Modifies {@code field} to contain every entry in {@code specials} with an index < {@code field.length * field[0].length}.
     * The entries from {@code specials} are placed randomly in {@code field}.
     * @param field The field to be modified.
     * @param specials The {@code SpecialField}s to be placed in {@code field}.
     * @see #generateField(NumberField[][], SpecialField[], int, boolean)
     * @see #generateField(NumberField[][], SpecialField)
     */
    static void generateField(NumberField[][] field, SpecialField[] specials){
        if (specials.length < (field.length * field[0].length)) {
            for (SpecialField special : specials) {
                generateField(field,special);
            }
        } else {
            int n = 0;
            for (int i = 0; i < field.length; i++) {
                for (int j = 0; j < field[i].length; j++) {
                    field[i][j] = specials[n];
                    n++;
                }
            }
        }
    }

    /**
     * Modifies {@code field} to contain {@code special}. Places {@code special} at a random location in {@code field}.
     * @param field The field to be modified.
     * @param special The {@code SpecialField} to be placed in {@code field}.
     */
    static void generateField(NumberField[][] field, SpecialField special){
        Random rng = new Random(System.currentTimeMillis()); //rng.nextInt(0, field.length);
        int x, y;
        x = rng.nextInt(0, field.length);
        y = rng.nextInt(0, field[0].length);
        if (!(field[x][y] instanceof SpecialField)) {
            field[x][y] = special;
        } else {
            A:
            for (int j = 0; j < field.length; j++) {
                for (int k = 0; k < field[j].length; k++) {
                    if (!(field[(x + j) % field.length][(x + k) % field[j].length] instanceof SpecialField)) {
                        field[(x + j) % field.length][(x + k) % field[j].length] = special;
                        break A;
                    }
                }
            }
        }
    }

    /**
     * Modifies {@code field} to contain {@code placeAmount} entries of {@code specials}.
     * If {@code placeAmount > specials.length}, duplicates will be placed. The order in which entries from {@code specials}
     * are selected, can be controlled be {@code randomOrder}. If {@code randomOrder} is true, an entry is chosen randomly,
     * otherwise sequentially.
     * @param field The field to be modified.
     * @param specials The {@code SpecialField}s to be placed in {@code field} {@code placeAmount} times.
     * @param placeAmount How many entries from {@code specials} should be placed.
     * @param randomOrder Whether a random entry from {@code specials} is chosen or the next element.
     * @see #generateField(NumberField[][], SpecialField[])
     * @see #generateField(NumberField[][], SpecialField)
     */
    static void generateField(NumberField[][] field, SpecialField[] specials, int placeAmount, boolean randomOrder){
        placeAmount = Math.min(placeAmount,field.length * field[0].length);
        Random rng = (randomOrder) ? new Random(System.currentTimeMillis()) : null;
        for (int i = 0; i < placeAmount; i++) {
            generateField(field,specials[(randomOrder) ? rng.nextInt(0, specials.length) : i % specials.length]);
        }
    }

    /**
     * Generates a field with {@code rows} rows and {@code columns.length} columns.
     * One column contains {@code rows} times {@code columns[i]}, with {@code i} being the current column.
     * Additionally, {@code placeAmount} entries of {@code specials} will be placed at random locations.
     * If {@code placeAmount > specials.length}, duplicates will be placed. The order in which entries from {@code specials}
     * are selected, can be controlled be {@code randomOrder}. If {@code randomOrder} is true, an entry is chosen randomly,
     * otherwise sequentially.
     * @param rows The amount of rows.
     * @param columns The contents of one row.
     * @param specials The {@code SpecialField}s to be placed in {@code field} {@code placeAmount} times.
     * @param placeAmount How many entries from {@code specials} should be placed.
     * @param randomOrder Whether a random entry from {@code specials} is chosen or the next element.
     * @return The generated field.
     * @see #generateField(int, NumberField[], SpecialField[], int, boolean)
     */
    static NumberField[][] generateField(int rows, NumberField[] columns, SpecialField[] specials, int placeAmount, boolean randomOrder){
        NumberField[][] rv = generateField(rows, columns);
        generateField(rv,specials,placeAmount,randomOrder);
        return rv;
    }

    /**
     * Generates a field with {@code rows.length} rows and {@code columns} columns.
     * One row contains {@code columns} times {@code rows[i]}, with {@code i} being the current row.
     * Additionally, {@code placeAmount} entries of {@code specials} will be placed at random locations.
     * If {@code placeAmount > specials.length}, duplicates will be placed. The order in which entries from {@code specials}
     * are selected, can be controlled be {@code randomOrder}. If {@code randomOrder} is true, an entry is chosen randomly,
     * otherwise sequentially.
     * @param rows The contents of one column.
     * @param columns The amount of columns.
     * @param specials The {@code SpecialField}s to be placed in {@code field} {@code placeAmount} times.
     * @param placeAmount How many entries from {@code specials} should be placed.
     * @param randomOrder Whether a random entry from {@code specials} is chosen or the next element.
     * @return The generated field.
     * @see #generateField(NumberField[], int, SpecialField[], int, boolean)
     */
    static NumberField[][] generateField(NumberField[] rows, int columns, SpecialField[] specials, int placeAmount, boolean randomOrder){
        NumberField[][] rv = generateField(rows, columns);
        generateField(rv,specials,placeAmount,randomOrder);
        return rv;
    }

    /**
     * Generates a field with {@code rows} rows and {@code columns.length} columns.
     * One column contains {@code rows} times {@code columns[i]}, with {@code i} being the current column.
     * Additionally, the entries from {@code specials} will be placed randomly.
     * @param rows The amount of rows.
     * @param columns The contents of one row.
     * @param specials The {@code SpecialField}s to be placed in {@code field}.
     * @return The generated field.
     * @see #generateField(NumberField[], int, SpecialField[])
     */
    static NumberField[][] generateField(int rows, NumberField[] columns, SpecialField[] specials){
        NumberField[][] rv = generateField(rows, columns);
        generateField(rv,specials);
        return rv;
    }

    /**
     * Generates a field with {@code rows.length} rows and {@code columns} columns.
     * One row contains {@code columns} times {@code rows[i]}, with {@code i} being the current row.
     * Additionally, the entries from {@code specials} will be placed randomly.
     * @param rows The contents of one column.
     * @param columns The amount of columns.
     * @param specials The {@code SpecialField}s to be placed in {@code field}.
     * @return The generated field.
     * @see #generateField(int, NumberField[], SpecialField[])
     */
    static NumberField[][] generateField(NumberField[] rows, int columns, SpecialField[] specials){
        NumberField[][] rv = generateField(rows, columns);
        generateField(rv,specials);
        return rv;
    }

    /**
     * Generates a field with {@code rows} rows and {@code columns.length} columns.
     * One column contains {@code rows} times {@code columns[i]}, with {@code i} being the current column.
     * @param rows The amount of rows.
     * @param columns The contents of one row.
     * @return The generated field.
     * @see #generateField(NumberField[], int)
     */
    static NumberField[][] generateField(int rows, NumberField[] columns){
        NumberField[][] rv = new NumberField[rows][columns.length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns.length; j++) {
                rv[i][j] = columns[j];
            }
        }
        return rv;
    }

    /**
     * Generates a field with {@code rows.length} rows and {@code columns} columns.
     * One row contains {@code columns} times {@code rows[i]}, with {@code i} being the current row.
     * @param rows The contents of one column.
     * @param columns The amount of columns.
     * @return The generated field.
     * @see #generateField(int, NumberField[])
     */
    static NumberField[][] generateField(NumberField[] rows, int columns){
        NumberField[][] rv = new NumberField[rows.length][columns];
        for (int i = 0; i < rows.length; i++) {
            for (int j = 0; j < columns; j++) {
                rv[i][j] = rows[i];
            }
        }
        return rv;
    }

}