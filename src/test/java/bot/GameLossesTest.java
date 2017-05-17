package bot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Test cases representing games this bot has lost
 * 
 * @author Ben Thurley
 *
 */
public class GameLossesTest
{

	MoveEngine mover = new MoveEngine();

	@Test
	public void testLoss1()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,1,0,0,0,0;0,0,2,1,2,0,0;0,0,1,2,1,0,0;0,2,1,1,2,2,0");
		field.printField();
		int move = mover.makeTurn(13, field, 1);
		System.out.println(move);

		assertEquals(
				"Need to go in column 3 to block player 2 setting up the win",
				3, move);
	}

	@Test
	public void testLoss2()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,2,2,0,0,0;0,0,1,2,2,0,0;0,2,1,1,1,0,0;1,2,1,1,2,0,0");
		field.printField();
		int move = mover.makeTurn(15, field, 1);
		System.out.println(move);

		assertNotEquals("Need to go in column 0 or 3", 1, move);
		assertNotEquals("Need to go in column 0 or 3", 2, move);
		assertNotEquals("Need to go in column 0 or 3", 4, move);
		assertNotEquals("Need to go in column 0 or 3", 5, move);
		assertNotEquals("Need to go in column 0 or 3", 6, move);
	}

	@Test
	public void testLoss3()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,1,0,0,0;0,0,0,2,0,0,0;0,0,0,2,2,0,0;0,2,0,2,1,0,0;0,1,0,1,1,0,0;2,1,0,1,2,0,0");
		field.printField();
		int move = mover.makeTurn(15, field, 1);
		System.out.println(move);

		assertEquals("Need to go in column 1 to setup the win", 1, move);
	}

	@Test
	public void testLoss4()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,2,0,0,0;1,0,1,1,0,0,0;2,0,2,1,1,0,0;1,0,1,2,2,0,0;1,0,2,1,2,2,0");
		field.printField();
		int move = mover.makeTurn(18, field, 2);
		System.out.println(move);

		assertNotEquals("Need to go in column 2 or 5", 0, move);
		assertNotEquals("Need to go in column 2 or 5", 1, move);
		assertNotEquals("Need to go in column 2 or 5", 3, move);
		assertNotEquals("Need to go in column 2 or 5", 4, move);
	}

	@Test
	public void testLoss5()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,2,0,0;0,1,0,2,1,0,0;0,2,0,1,2,0,0;0,2,1,2,1,2,0;0,2,1,1,2,1,1");
		field.printField();
		int move = mover.makeTurn(19, field, 1);
		System.out.println(move);

		assertEquals("Need to go in column 2 to stop the opponent winning", 2,
				move);
	}

	@Test
	public void testLoss6()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,2,1,0,0;0,2,0,1,1,0,0;0,1,0,1,1,1,0;0,2,0,1,2,2,0;0,2,1,2,1,1,0;0,2,2,1,2,2,0");
		field.printField();
		int move = mover.makeTurn(24, field, 2);
		System.out.println(move);

		assertEquals("Need to go in column 5", 5, move);

	}

	@Test
	public void testLoss7()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,1,2,1,0,0;0,0,2,2,2,0,0;1,0,2,1,2,0,1;2,0,2,1,1,0,2;1,0,1,2,2,0,1;1,2,1,1,2,0,1");
		field.printField();
		int move = mover.makeTurn(28, field, 2);
		System.out.println(move);

		assertNotEquals("Don't go in column 1 which allows opponent to win", 1,
				move);
	}

	@Test
	public void testLoss8()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,2,2,0,0,0;0,0,1,2,0,2,0;2,0,2,2,0,1,0;1,0,1,1,0,2,0;1,0,1,2,1,1,0;2,1,1,1,2,2,0");
		field.printField();
		int move = mover.makeTurn(25, field, 1);
		System.out.println(move);

		assertNotEquals("Don't go in column 1 which allows opponent to win", 1,
				move);
	}

	@Test
	public void testLoss9()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,2,1,0,0,0;0,0,2,2,0,0,0;0,0,1,1,0,0,0;0,0,2,1,0,0,0");
		field.printField();
		int move = mover.makeTurn(9, field, 1);
		System.out.println(move);

		assertNotEquals("Don't go in column 0 which allows opponent to win", 0,
				move);
		assertNotEquals("Don't go in column 1 which allows opponent to win", 1,
				move);
		assertNotEquals("Don't go in column 3 which allows opponent to win", 3,
				move);
		assertNotEquals("Don't go in column 6 which allows opponent to win", 6,
				move);
	}

	@Test
	public void testLoss10()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,2,0,0,0;1,0,1,1,0,0,0;2,0,2,1,1,0,0;1,0,1,2,2,0,0;1,0,2,1,2,2,0");
		field.printField();
		int move = mover.makeTurn(18, field, 2);
		System.out.println(move);

		assertNotEquals("Don't go in column 0 which allows opponent to win", 0,
				move);
		assertNotEquals("Don't go in column 1 which allows opponent to win", 1,
				move);
		assertNotEquals("Don't go in column 3 which allows opponent to win", 3,
				move);
		assertNotEquals("Don't go in column 4 which allows opponent to win", 4,
				move);
	}

	@Test
	public void testLoss11()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,2,0,0,0;0,0,0,1,0,0,0;0,0,0,1,0,0,0;1,0,0,2,0,0,0;1,0,2,1,2,0,0");
		field.printField();
		int move = mover.makeTurn(10, field, 2);
		System.out.println(move);

		assertEquals("Need to go in column 4", 4, move);
	}

}
