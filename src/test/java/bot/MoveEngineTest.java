package bot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MoveEngineTest
{

	MoveEngine mover = new MoveEngine();

	@Test
	public void testFirstMove()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0");
		int move = mover.makeTurn(1, field, 2);
		System.out.println(move);

		assertEquals("First move should always be in the middle", move, 3);
	}

	@Test
	public void testOffensiveWin()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,2,2,0,0,0,0;2,1,1,1,0,0,0");
		int move = mover.makeTurn(8, field, 1);
		System.out.println(move);

		assertEquals("Needs to go in column 4 to win", move, 4);
	}

	@Test
	public void testDefensiveWin()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;2,2,0,0,0,0,0;2,1,1,1,0,0,0");
		int move = mover.makeTurn(7, field, 2);
		System.out.println(move);

		assertEquals("Needs to go in column 4 to block player 1 winning", move,
				4);

	}

	@Test
	public void testOffensiveStrategy()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,1,0,2,1,2,0;0,2,0,2,2,1,0;0,1,0,1,2,1,0;2,2,0,1,1,1,2;1,2,0,1,1,2,2");
		int move = mover.makeTurn(24, field, 1);
		System.out.println(move);

		assertEquals("Needs to go in column 0 to setup the win", move, 0);
	}

	@Test
	public void testDefensiveStrategy()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,1,0,2,0,0,0;0,2,0,1,0,0,0;0,2,0,1,0,1,0;2,2,1,1,1,2,0");
		int move = mover.makeTurn(7, field, 2);
		System.out.println(move);

		assertEquals("Needs to go in column 4 to not give game away", move, 4);

	}

}
