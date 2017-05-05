package bot;

/**
 * Score the current field.
 * 
 * A +ve score means the current player is winning, conversely a -ve score means
 * the other player is winning.
 * 
 * @author Ben Thurley
 * 
 */
public class FieldScorer
{

	private static final int	MAXIMISING_PLAYER	= 1;
	private static final int	FOUR_SCORE			= 100000;

	/**
	 * Simple heuristic function to evaluate board configurations.
	 * 
	 * @param field
	 * @param player
	 * @return
	 */
	public FieldScore scoreField(Field field, int player)
	{
		int score = 0;
		int winner = 0;

		// Loop through each position on the board
		for (int x = 0; x < field.getNrColumns(); x++)
		{
			// From bottom row up (row 0 is the top)
			for (int y = field.getNrRows() - 1; y >= 0; y--)
			{
				/*
				 * Once we hit an empty slot we can skip the rows above for this
				 * column as they'll all be empty int disc = field.getDisc(x,
				 * y); if (disc == 0) { break; }
				 */

				int positionScore = scoreRunsStartingAtPosition(x, y, field);

				// If we find a 4 then make sure we terminate with this end of
				// game score
				if (positionScore >= FOUR_SCORE || positionScore <= -FOUR_SCORE)
				{
					winner = field.getDisc(x, y);
				}

				score += positionScore;
			}
		}

		return new FieldScore(score, winner);
	}

	/**
	 * Score runs starting at this position and in all directions
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @param player
	 * @return
	 */
	private int scoreRunsStartingAtPosition(int x, int y, Field field)
	{
		int score = 0;
		int disc = field.getDisc(x, y);

		// Only score if this position is not empty
		if (disc != 0)
		{
			score += horizontalScoreAt(x, y, field);

			score += verticalScoreAt(x, y, field);

			score += diagonalUpRightScoreAt(x, y, field);

			score += diagonalDownRightScoreAt(x, y, field);

		}

		return score;
	}

	/**
	 * Given a run length apply a weighting multiplier and either make positive
	 * or negative depending on whose run this is.
	 * 
	 * @param length
	 * @param isMaximisingPlayer
	 * @return
	 */
	private int scoreRun(int length, boolean isMaximisingPlayer)
	{
		int score = 0;

		if (length == 2)
		{
			score = 10;
		}
		else if (length == 3)
		{
			score = 1000;
		}
		else if (length >= 4)
		{
			score = FOUR_SCORE;
		}

		// If these runs are for the other player then make negative
		if (!isMaximisingPlayer)
		{
			score = (0 - score);
		}

		return score;
	}

	/**
	 * Score horizontal runs starting at a given position
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int horizontalScoreAt(int x, int y, Field field)
	{
		int score = 0;
		int player = field.getDisc(x, y);

		// Is this the start of a new run?
		if (x == 0 || field.getDisc(x - 1, y) != player)
		{
			// How long is this run?
			int length = 1;
			int column = x;
			while (length < 4 && field.getDisc(column, y) == player
					&& column < (field.getNrColumns() - 1))
			{
				column += 1;
				if (field.getDisc(column, y) == player)
				{
					length += 1;
				}
			}
			int end = (x + length - 1);

			if (length >= 4)
			{
				// We have a winner!
				score = scoreRun(length, player == MAXIMISING_PLAYER);
			}
			else if (length == 3)
			{
				// Check either side for a threat
				if (x > 0 && field.getDisc(x - 1, y) == 0)
				{
					score += scoreRun(length, player == MAXIMISING_PLAYER);
				}
				if (end < (field.getNrColumns() - 2)
						&& field.getDisc(end + 1, y) == 0)
				{
					score += scoreRun(length, player == MAXIMISING_PLAYER);
				}
			}
			else if (length == 2)
			{
				// Look left
				if (x > 1 && field.getDisc(x - 1, y) == 0)
				{
					if (field.getDisc(x - 2, y) == 0)
					{
						score += scoreRun(length, player == MAXIMISING_PLAYER);
					}
					else if (field.getDisc(x - 2, y) == player)
					{
						// What we have is a 4 with a gap so score as if it's a
						// 3 since both are 1 away from a 4
						score += scoreRun(length + 1,
								player == MAXIMISING_PLAYER);
					}
				}
				// Look right
				if (end < (field.getNrColumns() - 3)
						&& field.getDisc(end + 1, y) == 0)
				{
					if (field.getDisc(end + 2, y) == 0)
					{
						score += scoreRun(length, player == MAXIMISING_PLAYER);
					}
					else if (field.getDisc(end + 2, y) == player)
					{
						// What we have is a 4 with a gap so score as if it's a
						// 3 since both are 1 away from a 4
						score += scoreRun(length + 1,
								player == MAXIMISING_PLAYER);
					}
				}
			}

		}

		return score;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int verticalScoreAt(int x, int y, Field field)
	{
		int score = 0;
		int player = field.getDisc(x, y);

		// Is this a disc at the top of the column?
		if (player != 0 && (y == 0 || field.getDisc(x, y - 1) == 0))
		{
			// How long is this run?
			int length = 1;
			int row = y;
			while (length < 4 && field.getDisc(x, row) == player
					&& row < (field.getNrRows() - 1))
			{
				row += 1;
				if (field.getDisc(x, row) == player)
				{
					length += 1;
				}
			}

			score = scoreRun(length, player == MAXIMISING_PLAYER);
		}

		return score;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int diagonalUpRightScoreAt(int x, int y, Field field)
	{
		int score = 0;

		int player = field.getDisc(x, y);

		// Is this the start of a new run?
		if (x == 0 || y == field.getNrRows() - 1
				|| field.getDisc(x - 1, y + 1) != player)
		{
			// How long is this run?
			int length = 1;
			int column = x;
			int row = y;
			while (length < 4 && field.getDisc(column, row) == player
					&& column < (field.getNrColumns() - 1) && row > 0)
			{
				column += 1;
				row -= 1;
				if (field.getDisc(column, row) == player)
				{
					length += 1;
				}
			}
			int endColumn = (x + length - 1);
			int endRow = (y - length + 1);

			if (length >= 4)
			{
				// We have a winner!
				score = scoreRun(length, player == MAXIMISING_PLAYER);
			}
			else if (length == 3)
			{
				// Check either side for a threat
				if (x > 0 && y < (field.getNrRows() - 2)
						&& field.getDisc(x - 1, y + 1) == 0)
				{
					score += scoreRun(length, player == MAXIMISING_PLAYER);
				}
				if (endColumn < (field.getNrColumns() - 2) && endRow > 0
						&& field.getDisc(endColumn + 1, endRow - 1) == 0)
				{
					score += scoreRun(length, player == MAXIMISING_PLAYER);
				}
			}
			else if (length == 2)
			{
				// Look left
				if (x > 1 && y < (field.getNrRows() - 2)
						&& field.getDisc(x - 1, y + 1) == 0)
				{
					int endDisc = field.getDisc(x - 2, y + 2);
					if (endDisc == 0)
					{
						score += scoreRun(length, player == MAXIMISING_PLAYER);
					}
					else if (endDisc == player)
					{
						// What we have is a 4 with a gap so score as if it's a
						// 3 since both are 1 away from a 4
						score += scoreRun(length + 1,
								player == MAXIMISING_PLAYER);
					}
				}
				// Look right
				if (endColumn < (field.getNrColumns() - 4) && endRow > 3
						&& field.getDisc(endColumn + 1, endRow - 1) == 0)
				{
					int endDisc = field.getDisc(x + 2, y - 2);
					if (endDisc == 0)
					{
						score += scoreRun(length, player == MAXIMISING_PLAYER);
					}
					else if (endDisc == player)
					{
						// What we have is a 4 with a gap so score as if it's a
						// 3 since both are 1 away from a 4
						score += scoreRun(length + 1,
								player == MAXIMISING_PLAYER);
					}
				}
			}

		}

		return score;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int diagonalDownRightScoreAt(int x, int y, Field field)
	{
		int score = 0;

		int player = field.getDisc(x, y);

		// Is this the start of a new run?
		if (x == 0 || y == 0 || field.getDisc(x - 1, y - 1) != player)
		{
			// How long is this run?
			int length = 1;
			int column = x;
			int row = y;
			while (length < 4 && field.getDisc(column, row) == player
					&& column < (field.getNrColumns() - 1)
					&& row < (field.getNrRows() - 1))
			{
				column += 1;
				row += 1;
				if (field.getDisc(column, row) == player)
				{
					length += 1;
				}
			}
			int endColumn = (x + length - 1);
			int endRow = (y + length - 1);

			if (length >= 4)
			{
				// We have a winner!
				score = scoreRun(length, player == MAXIMISING_PLAYER);
			}
			else if (length == 3)
			{
				// Check either side for a threat
				if (x > 0 && y > 0 && field.getDisc(x - 1, y - 1) == 0)
				{
					score += scoreRun(length, player == MAXIMISING_PLAYER);
				}
				if (endColumn < (field.getNrColumns() - 2)
						&& endRow < (field.getNrRows() - 2)
						&& field.getDisc(endColumn + 1, endRow + 1) == 0)
				{
					score += scoreRun(length, player == MAXIMISING_PLAYER);
				}
			}
			else if (length == 2)
			{
				// Look left
				if (x > 1 && y > 1 && field.getDisc(x - 1, y - 1) == 0)
				{
					int endDisc = field.getDisc(x - 2, y - 2);
					if (endDisc == 0)
					{
						score += scoreRun(length, player == MAXIMISING_PLAYER);
					}
					else if (endDisc == player)
					{
						// What we have is a 4 with a gap so score as if it's a
						// 3 since both are 1 away from a 4
						score += scoreRun(length + 1,
								player == MAXIMISING_PLAYER);
					}
				}
				// Look right
				if (endColumn < (field.getNrColumns() - 4)
						&& endRow < (field.getNrRows() - 4)
						&& field.getDisc(endColumn + 1, endRow + 1) == 0)
				{
					int endDisc = field.getDisc(x + 2, y + 2);
					if (endDisc == 0)
					{
						score += scoreRun(length, player == MAXIMISING_PLAYER);
					}
					else if (endDisc == player)
					{
						// What we have is a 4 with a gap so score as if it's a
						// 3 since both are 1 away from a 4
						score += scoreRun(length + 1,
								player == MAXIMISING_PLAYER);
					}
				}
			}

		}

		return score;
	}

}
