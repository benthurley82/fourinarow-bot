// // Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * BotStarter class
 * 
 * Magic happens here. You should edit this file, or more specifically the
 * makeTurn() method to make your bot do more than random moves.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotStarter
{

	static boolean		devMode	= false;
	private MoveEngine	mover	= new MoveEngine();

	/**
	 * Makes a turn. Edit this method to make your bot smarter.
	 *
	 * @return The column where the turn was made.
	 */
	public int makeTurn(int round, Field field, int myId)
	{

		System.err.println("Round " + round + " - Player " + myId);
		String[] rows = field.toString().split(";");
		for (String row : rows)
		{
			System.err.println(row);
		}

		int move = mover.makeTurn(round, field, myId);

		System.err.println("Move " + move);
		System.err.println("");

		return move;
	}

	public static void main(String[] args)
	{

		if (args.length > 0 && args[0].equals("DEV"))
		{
			devMode = true;
			try
			{
				System.setErr(new PrintStream(
						new FileOutputStream(new File("out.txt"))));
			}
			catch (FileNotFoundException e)
			{
				devMode = false;
			}
		}

		BotParser parser = new BotParser(new BotStarter());
		parser.run();
	}

}
