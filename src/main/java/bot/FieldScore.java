package bot;

/**
 * Container for the results of a field score
 * 
 * @author Ben Thurley
 *
 */
public final class FieldScore
{
	private final int		score;
	private final boolean	hasWinner;
	private final int		winner;

	public FieldScore(int score, int winner)
	{
		this.score = score;
		this.winner = winner;
		this.hasWinner = (winner != 0);
	}

	public int getScore()
	{
		return score;
	}

	public boolean hasWinner()
	{
		return hasWinner;
	}

	public int getWinner()
	{
		return winner;
	}
}
