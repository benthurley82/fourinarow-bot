package bot;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Test cases where we didn't lose but made a bad choice
 * 
 * @author Ben Thurley
 *
 */
public class BadMovesTest
{

	MoveEngine mover = new MoveEngine();

	@Test
	public void testMove1()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,2,0,0,0;0,0,1,2,0,0,0;2,0,2,1,2,0,1;1,0,1,1,1,0,2;2,0,1,2,1,0,2;2,1,1,1,2,0,2");
		field.printField();
		int move = mover.makeTurn(25, field, 1);
		System.out.println(move);

		assertEquals("Need to go in column 4 to set up the win", 4, move);
	}

	@Test
	public void testMove2()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,2,0,0,0;0,0,0,1,0,0,0;0,1,0,1,2,0,0;0,2,2,1,1,1,0;2,2,1,2,1,2,0;2,2,1,1,2,1,0");
		field.printField();
		int move = mover.makeTurn(23, field, 1);
		System.out.println(move);

		assertEquals("Need to go in column 2 to win", 2, move);
	}

	@Test
	public void testMove3()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,2,0,0,0;0,0,1,1,1,0,0;0,0,2,1,2,0,2");
		field.printField();
		int move = mover.makeTurn(9, field, 1);
		System.out.println(move);

		assertNotEquals("Avoid column 1 giving the win away", 1, move);
		assertNotEquals("Avoid column 5 giving the win away", 5, move);
	}

}