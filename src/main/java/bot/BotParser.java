// Copyright 2015 theaigames.com (developers@theaigames.com)

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

import java.util.Scanner;

/**
 * MyBot class
 * 
 * Main class that will keep reading output from the engine. Will either update
 * the bot state or get actions.
 * 
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class BotParser
{

	final Scanner		scan;
	final BotStarter	bot;

	private Field		mField;
	private int			mBotId	= 0;
	private int			round	= 0;

	public BotParser(BotStarter bot)
	{
		this.scan = new Scanner(System.in);
		this.bot = bot;
	}

	public void run()
	{
		mField = new Field(0, 0);
		while (scan.hasNextLine())
		{
			String line = scan.nextLine();
			System.err.println("Input: " + line);

			if (line.length() == 0)
			{
				continue;
			}

			String[] parts = line.split(" ");

			if (parts[0].equals("settings"))
			{
				settings(parts);
			}
			else if (parts[0].equals("update"))
			{
				update(parts);
			}
			else if (parts[0].equals("action"))
			{
				action(parts);
			}
			else
			{
				System.out.println("unknown command");
			}
		}
	}

	private void action(String[] parts)
	{
		if (parts[1].equals("move"))
		{ /* move requested */
			int column = bot.makeTurn(round, mField, mBotId);
			System.out.println("place_disc " + column);
		}
	}

	private void update(String[] parts)
	{
		if (parts[2].equals("field"))
		{
			String data = parts[3];
			mField.parseFromString(data); /* Parse Field with data */
		}
		else if (parts[2].equals("round"))
		{
			round = Integer.parseInt(parts[3]);
		}
	}

	private void settings(String[] parts)
	{
		if (parts[1].equals("field_columns"))
		{
			mField.setColumns(Integer.parseInt(parts[2]));
		}
		else if (parts[1].equals("field_rows"))
		{
			mField.setRows(Integer.parseInt(parts[2]));
		}
		else if (parts[1].equals("your_botid"))
		{
			mBotId = Integer.parseInt(parts[2]);
		}
	}
}