package pieces;

import java.util.List;

import board.Chessboard;
import piece.utils.Position;
import piece.utils.Color;
import piece.utils.Name;

/**
 * 
 *
 */
public final class Knight extends AbstractPiece {

    private static final int KNIGHT_VALUE = 3;

    /**
     * A Knight piece constructor.
     * 
     * @param position the piece position.
     * @param color the piece color.
     */
    protected Knight(final Position position, final Color color) {
        super(Name.KNIGHT, position, color);
        // TODO Auto-generated constructor stub
    }

    @Override
    public List<Position> getAllPossiblePositions(final Piece piece, final Chessboard board) {
        // TODO Auto-generated method stub
        return List.of();
    }

    @Override
    public int getValue() {
        // TODO Auto-generated method stub
        return KNIGHT_VALUE;
    }
}
