package bot;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Early moves that resulted in a lost game
 * 
 * @author Ben Thurley
 *
 */
public class EarlyMoveLosses
{

	MoveEngine mover = new MoveEngine();

	@Test
	public void testLoss1()
	{
		Field field = new Field(7, 6);
		field.parseFromString(
				"0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,0,0,0,0,0,0;0,2,0,1,1,2,0");
		field.printField();
		int move = mover.makeTurn(13, field, 1);
		System.out.println(move);

		assertEquals("Need to go in column 1", 1, move);
	}

}
