package bot;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Score the current field.
 * 
 * A +ve score means the current player is winning, conversely a -ve score means
 * the other player is winning.
 * 
 */
public class FieldScorerHorizontalTest
{

	private FieldScorer			scorer	= new FieldScorer();
	private Field				field	= new Field(7, 6);
	private static final int	PLAYER	= 1;

	/**
	 * 4 in a row is better than 3
	 */
	@Test
	public void testScoreHorizontal1()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,1,1,1,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 1: " + score1);

		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,1,1,0,0,0,0");

		int score2 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 2: " + score2);

		assertTrue(score1 > score2);
	}

	/**
	 * 3 in a row is better than 2
	 */
	@Test
	public void testScoreHorizontal2()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,1,1,0,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 1: " + score1);

		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,1,0,0,0,0,0");

		int score2 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 2: " + score2);

		assertTrue(score1 > score2);
	}

	/**
	 * 2 in a row is better than 1
	 */
	@Test
	public void testScoreHorizontal3()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,1,1");

		int score1 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 1: " + score1);

		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,1,0");

		int score2 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 2: " + score2);

		assertTrue(score1 > score2);
	}

	/**
	 * Opponents 4 outranks my 3
	 */
	@Test
	public void testScoreHorizontal4()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,1,1,0,0,0,0;2,2,2,2,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 1: " + score1);

		assertTrue(score1 < 0);
	}

	/**
	 * 3 in a row is no good if the spaces either side are not vacant
	 */
	@Test
	public void testScoreHorizontal6()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,1,1,1,2,0,0");

		int score1 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 1: " + score1);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,1,1,1,0,0,0");

		int score2 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 2: " + score1);

		assertTrue(score1 < score2);
	}

	/**
	 * 2 in a row is no good if the spaces either side are not vacant
	 */
	@Test
	public void testScoreHorizontal7()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,1,1,2,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 1: " + score1);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,1,1,0,0,0,0");

		int score2 = scorer.scoreField(field, PLAYER).getScore();
		System.out.println("Score 2: " + score2);

		assertTrue(score1 < score2);
	}

}