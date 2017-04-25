package bot;

import static org.junit.Assert.assertEquals;

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

		assertEquals(
				"Need to go in column 3 to block player 2 setting up the win",
				3, move);
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

}
