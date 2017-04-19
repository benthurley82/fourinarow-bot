package bot;

import java.util.ArrayList;
import java.util.List;

/**
 * A move engine for determining the next move in a game of connect 4
 * 
 * @author Ben Thurley
 *
 */
public class MoveEngine
{

	private FieldScorer			rating				= new FieldScorer();
	private static final int	DEPTH				= 5;
	private static final int	MAXIMISING_PLAYER	= 1;
	private static final int	MINIMISING_PLAYER	= 2;

	/**
	 * Given a board state and the current player token determine the next move
	 * 
	 * @param round
	 * @param field
	 * @param myId
	 * @return
	 */
	public int makeTurn(int round, Field field, int myId)
	{
		// Default to middle column if empty
		int move = Math.floorDiv(field.getNrColumns(), 2);
		if (!field.isEmpty())
		{
			move = minimax(DEPTH, field, myId);
		}

		return move;
	}

	/**
	 * An implementation of the minimax algorithm to determine the next move.
	 * This calls itself recursively examining the board state for each possible
	 * move down to a set depth.
	 * 
	 * @param depth
	 * @param field
	 * @param disc
	 * @return
	 */
	private int minimax(int depth, Field field, int disc)
	{
		int bestScore = 0;
		int bestMove = -1;

		// If depth=0 or end of game then return the score
		int currentScore = rating.scoreField(field, MAXIMISING_PLAYER);
		if (depth == 0 || field.isFull() || currentScore >= 10000
				|| currentScore <= -10000)
		{
			bestScore = currentScore;
		}
		else
		{
			Field clonedField = new Field(field);
			List<Integer> nextMoves = getValidMoves(field);
			if (disc == MAXIMISING_PLAYER)
			{
				bestScore = Integer.MIN_VALUE;
				for (Integer move : nextMoves)
				{
					clonedField.addDisc(move, disc);
					int moveScore = minimax(depth - 1, clonedField,
							MINIMISING_PLAYER);
					if (moveScore > bestScore)
					{
						bestScore = moveScore;
						bestMove = move;
					}
					clonedField.undoAddDisc(move);
				}
			}
			else
			{
				bestScore = Integer.MAX_VALUE;
				for (Integer move : nextMoves)
				{
					clonedField.addDisc(move, disc);
					int moveScore = minimax(depth - 1, clonedField,
							MAXIMISING_PLAYER);
					if (moveScore < bestScore)
					{
						bestScore = moveScore;
						bestMove = move;
					}
					clonedField.undoAddDisc(move);
				}
			}
		}

		return depth == DEPTH ? bestMove : bestScore;
	}

	/**
	 * Get a list of possible moves for this board state
	 * 
	 * @param field
	 * @return
	 */
	private List<Integer> getValidMoves(Field field)
	{
		List<Integer> moves = new ArrayList<Integer>();
		for (int move = 0; move < field.getNrColumns(); move++)
		{
			if (field.isValidMove(move))
			{
				moves.add(new Integer(move));
			}
		}
		return moves;
	}

}
