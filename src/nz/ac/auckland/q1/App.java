package nz.ac.auckland.q1;

import java.awt.EventQueue;

public class App
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					MonkeyFrame frame = new MonkeyFrame();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
