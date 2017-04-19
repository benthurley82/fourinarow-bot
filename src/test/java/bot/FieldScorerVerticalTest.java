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
public class FieldScorerVerticalTest
{

	private FieldScorer			scorer	= new FieldScorer();
	private Field				field	= new Field(7, 6);
	private static final int	PLAYER	= 1;

	/**
	 * 4 in a line is better than 3
	 */
	@Test
	public void testScoreVertical1()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 1: " + score1);

		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0");

		int score2 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 2: " + score2);

		assertTrue(score1 > score2);
	}

	/**
	 * 3 in a line is better than 2
	 */
	@Test
	public void testScoreVertical2()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 1: " + score1);

		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0");

		int score2 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 2: " + score2);

		assertTrue(score1 > score2);
	}

	/**
	 * 2 in a line is better than 1
	 */
	@Test
	public void testScoreVertical3()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,1;0,0,0,0,0,0,1");

		int score1 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 1: " + score1);

		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,1");

		int score2 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 2: " + score2);

		assertTrue(score1 > score2);
	}

	/**
	 * Opponents 4 outranks my 3
	 */
	@Test
	public void testScoreVertical4()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,2,0,0,0,0,0;1,2,0,0,0,0,0;1,2,0,0,0,0,0;1,2,0,0,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 1: " + score1);

		assertTrue(score1 < 0);
	}

	/**
	 * 3 in a line is no good if the spaces either side are not vacant
	 */
	@Test
	public void testScoreVertical6()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 1: " + score1);

		assertTrue(score1 == 0);
	}

	/**
	 * 2 in a line is no good if the spaces either side are not vacant
	 */
	@Test
	public void testScoreVertical7()
	{
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,0,0,0,0,0,0;1,0,0,0,0,0,0;1,0,0,0,0,0,0");

		int score1 = scorer.scoreField(field, PLAYER);
		System.out.println("Score 1: " + score1);

		assertTrue(score1 == 0);
	}

}