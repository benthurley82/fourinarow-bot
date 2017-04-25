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

	// We're aiming for 4 in a row
	private static final int TARGET = 4;

	/**
	 * Simple heuristic function to evaluate board configurations. Heuristic is
	 * (num of 4-in-a-rows)*10000 + (num of 3-in-a-rows)*100 + (num of
	 * 2-in-a-rows)*10 - (num of opponent 4-in-a-rows)*99999 - (num of opponent
	 * 3-in-a-rows)*100 - (num of opponent 2-in-a-rows)*10
	 * 
	 * @param field
	 * @param player
	 * @return
	 */
	public FieldScore scoreField(Field field, int player)
	{
		int score = 0;
		boolean hasWinner = false;

		// Loop through each position on the board
		for (int x = 0; x < field.getNrColumns(); x++)
		{
			for (int y = 0; y < field.getNrRows(); y++)
			{
				int positionScore = scoreRunsStartingAtPosition(x, y, field,
						player);
				// If we find a 4 then make sure we terminate with this end of
				// game score
				if (positionScore >= 10000 || positionScore <= -10000)
				{
					hasWinner = true;
				}
				score += positionScore;
			}
		}

		return new FieldScore(score, hasWinner);
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
	private int scoreRunsStartingAtPosition(int x, int y, Field field, int player)
	{
		int score = 0;
		int disc = field.getDisc(x, y);

		// Only score if this position is not empty
		if (disc != 0)
		{
			boolean isMaximisingPlayer = (disc == player);

			int run = lengthOfHorizontalRun(x, y, field);
			score += scoreRun(run, isMaximisingPlayer);

			run = lengthOfVerticalRun(x, y, field);
			score += scoreRun(run, isMaximisingPlayer);

			run = lengthOfDiagonalUpRightRun(x, y, field);
			score += scoreRun(run, isMaximisingPlayer);

			run = lengthOfDiagonalDownRightRun(x, y, field);
			score += scoreRun(run, isMaximisingPlayer);

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
			score = 100;
		}
		else if (length >= 4)
		{
			score = 10000;
		}

		// If these runs are for the other player then make negative
		if (!isMaximisingPlayer)
		{
			score = (0 - score);
		}

		return score;
	}

	/**
	 * Is this the start of a left to right horizontal run and how long? Must
	 * have space either side to complete the run.
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int lengthOfHorizontalRun(int x, int y, Field field)
	{
		int length = 0;
		int player = field.getDisc(x, y);

		// Is this the start of a run?
		if (x == 0 || field.getDisc(x - 1, y) != player)
		{
			// See how many tokens the same we have in a row
			int column = x + 1;
			while (column < field.getNrColumns()
					&& field.getDisc(column, y) == player)
			{
				column += 1;
			}
			length = (column - x);

			// If this is less than the target then check there are enough
			// free slots for this to be of use...
			if (length < TARGET)
			{
				int spaces = 0;
				int needed = (TARGET - length);
				// Look right
				while (spaces < needed && column < field.getNrColumns()
						&& (field.getDisc(column, y) == 0
								|| field.getDisc(column, y) == player))
				{
					spaces += 1;
					column += 1;
				}
				// Look left if needed
				column = x - 1;
				while (spaces < needed && column >= 0
						&& (field.getDisc(column, y) == 0
								|| field.getDisc(column, y) == player))
				{
					spaces += 1;
					column -= 1;
				}
				if (spaces < needed)
				{
					// Doesn't count
					length = 0;
				}
			}
		}

		return length;
	}

	/**
	 * Count vertical run from bottom to top
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int lengthOfVerticalRun(int x, int y, Field field)
	{
		int length = 0;
		int player = field.getDisc(x, y);

		// Is this the start of a run?
		if (y == 0 || field.getDisc(x, y - 1) != player)
		{
			// See how many tokens the same we have in a row
			int row = y + 1;
			while (row < field.getNrRows() && field.getDisc(x, row) == player)
			{
				row += 1;
			}
			length = (row - y);

			// If this is less than the target then check there are enough
			// free slots for this to be of use...
			if (length < TARGET)
			{
				int spaces = 0;
				int needed = (TARGET - length);
				// Look right
				while (spaces < needed && row < field.getNrRows()
						&& (field.getDisc(x, row) == 0
								|| field.getDisc(x, row) == player))
				{
					spaces += 1;
					row += 1;
				}
				// Look left if needed
				row = y - 1;
				while (spaces < needed && row >= 0
						&& (field.getDisc(x, row) == 0
								|| field.getDisc(x, row) == player))
				{
					spaces += 1;
					row -= 1;
				}
				if (spaces < needed)
				{
					// Doesn't count
					length = 0;
				}
			}
		}

		return length;
	}

	/**
	 * Count diagonal run going upwards and right
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int lengthOfDiagonalUpRightRun(int x, int y, Field field)
	{
		int length = 0;
		int player = field.getDisc(x, y);

		int column = x;
		int row = y;
		boolean check = false;
		if (x > 0 && y < (field.getNrRows() - 1))
		{
			column = x - 1;
			row = y + 1;
			check = true;
		}

		// Is this the start of a run?
		if (!check || field.getDisc(column, row) != player)
		{
			// See how many tokens the same we have in a row
			column = x + 1;
			row = y - 1;
			while (column < field.getNrColumns() && row > 0
					&& field.getDisc(column, row) == player)
			{
				column += 1;
				row -= 1;
			}
			length = (column - x);

			// If this is less than the target then check there are enough
			// free slots for this to be of use...
			if (length < TARGET)
			{
				int spaces = 0;
				int needed = (TARGET - length);
				// Look right
				while (spaces < needed && column < field.getNrColumns()
						&& row > 0 && (field.getDisc(column, row) == 0
								|| field.getDisc(column, row) == player))
				{
					spaces += 1;
					column += 1;
					row -= 1;
				}
				// Look left if needed
				column = x - 1;
				row = y + 1;
				while (spaces < needed && column >= 0 && row < field.getNrRows()
						&& (field.getDisc(column, row) == 0
								|| field.getDisc(column, row) == player))
				{
					spaces += 1;
					column -= 1;
					row += 1;
				}
				if (spaces < needed)
				{
					// Doesn't count
					length = 0;
				}
			}
		}

		return length;
	}

	/**
	 * Count diagonal run going downwards and right
	 * 
	 * @param x
	 * @param y
	 * @param field
	 * @return
	 */
	private int lengthOfDiagonalDownRightRun(int x, int y, Field field)
	{
		int length = 0;
		int player = field.getDisc(x, y);

		int column = x;
		int row = y;
		boolean check = false;
		if (x > 0 && y > 0)
		{
			column = x - 1;
			row = y - 1;
			check = true;
		}

		// Is this the start of a run?
		if (!check || field.getDisc(column, row) != player)
		{
			// See how many tokens the same we have in a row
			column = x + 1;
			row = y + 1;
			while (column < field.getNrColumns() && row < field.getNrRows()
					&& field.getDisc(column, row) == player)
			{
				column += 1;
				row += 1;
			}
			length = (column - x);

			// If this is less than the target then check there are enough
			// free slots for this to be of use...
			if (length < TARGET)
			{
				int spaces = 0;
				int needed = (TARGET - length);
				// Look right
				while (spaces < needed && column < field.getNrColumns()
						&& row > field.getNrRows()
						&& (field.getDisc(column, row) == 0
								|| field.getDisc(column, row) == player))
				{
					spaces += 1;
					column += 1;
					row += 1;
				}
				// Look left if needed
				column = x - 1;
				row = y - 1;
				while (spaces < needed && column >= 0 && row >= 0
						&& (field.getDisc(column, row) == 0
								|| field.getDisc(column, row) == player))
				{
					spaces += 1;
					column -= 1;
					row -= 1;
				}
				if (spaces < needed)
				{
					// Doesn't count
					length = 0;
				}
			}
		}

		return length;
	}
}
