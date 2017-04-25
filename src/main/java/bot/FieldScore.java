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

	public FieldScore(int score, boolean hasWinner)
	{
		this.score = score;
		this.hasWinner = hasWinner;
	}

	public int getScore()
	{
		return score;
	}

	public boolean hasWinner()
	{
		return hasWinner;
	}
}
