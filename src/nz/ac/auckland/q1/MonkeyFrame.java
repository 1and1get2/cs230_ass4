package nz.ac.auckland.q1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingWorker;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nz.ac.auckland.q2.Generator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class MonkeyFrame extends JFrame implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField monkeysPerGenerationSizeTextField;
	
	private JTextArea generatedTextArea;
	private JTextArea targetTextArea; 
	private JCheckBox chckbxParallel ;
	private JButton btnStart;
	
	private JLabel lblUpdateTime;
	private JLabel lblUpdateGeneration;
	private JLabel lblUpdateGenerationPerSec;
	
	private SwingWorker<Void, String> sw;
	
	/**
	 * Create the frame.
	 */
	public MonkeyFrame()
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 955, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("images/HamletMonkey.jpg"));
		
		generatedTextArea = new JTextArea();
		generatedTextArea.setText("");
		targetTextArea = new JTextArea();
		targetTextArea.setText("To be or not to be, that is the question;\nWhether 'tis nobler in the mind to suffer\nThe slings and arrows of outrageous fortune,\nOr to take arms against a sea of troubles,\nAnd by opposing, end them.");
		
		btnStart = new JButton("Start");
		btnStart.setActionCommand("Start");
		
		btnStart.addActionListener(this);
		
		chckbxParallel = new JCheckBox("Parallel");
		
		JLabel lblMonkeysPerGeneration = new JLabel("Monkeys Per Generation");
		lblMonkeysPerGeneration.setFont(new Font("Arial", Font.BOLD, 14));
		
		monkeysPerGenerationSizeTextField = new JTextField();
		monkeysPerGenerationSizeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		monkeysPerGenerationSizeTextField.setText("2000");
		monkeysPerGenerationSizeTextField.setColumns(10);
		
		JLabel lblTime = new JLabel("Time:");
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblGenerations = new JLabel("Generations:");
		lblGenerations.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblGenerationsPerSec = new JLabel("Generations / Sec");
		lblGenerationsPerSec.setHorizontalAlignment(SwingConstants.RIGHT);
		
		lblUpdateTime = new JLabel("___");
		lblUpdateTime.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblUpdateGeneration = new JLabel("___");
		lblUpdateGeneration.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblUpdateGenerationPerSec = new JLabel("___");
		lblUpdateGenerationPerSec.setHorizontalAlignment(SwingConstants.CENTER);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblMonkeysPerGeneration, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(monkeysPerGenerationSizeTextField, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnStart, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxParallel))
						.addComponent(generatedTextArea, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(lblGenerationsPerSec, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblGenerations, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblTime, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lblUpdateGenerationPerSec, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblUpdateTime, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lblUpdateGeneration, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(targetTextArea, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 371, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblMonkeysPerGeneration, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
								.addComponent(monkeysPerGenerationSizeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTime)
								.addComponent(lblUpdateTime))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnStart)
										.addComponent(chckbxParallel)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(6)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblGenerations)
										.addComponent(lblUpdateGeneration))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblGenerationsPerSec)
										.addComponent(lblUpdateGenerationPerSec))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(generatedTextArea, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE)
								.addComponent(targetTextArea, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == btnStart)
		{
			
			// check for errors:
			try {
				if (Integer.parseInt(monkeysPerGenerationSizeTextField.getText()) < 1 || Integer.parseInt(monkeysPerGenerationSizeTextField.getText()) > 10000){
					throw new Exception("PopulationSize out of range (1 - 10000): " + monkeysPerGenerationSizeTextField.getText());
				}
				if (targetTextArea.getText().length() < 1 || targetTextArea.getText().length() > 300){
					throw new Exception("length of targetTextArea: " + targetTextArea.getText().length() + " out of range (1-300)");
				}
				for (char c : targetTextArea.getText().toCharArray()){
					int cInt = (int) c;
					if (cInt != 10 && cInt != 13 && (cInt < 32 || cInt > 126)){
						throw new Exception("character " + c + " not in set of {10, 13, 32-126}");
					}
				}
				} catch (Exception exception) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, exception.toString());
					exception.printStackTrace();
					return;
				}
			
			if (btnStart.getActionCommand() == "Cancel")
			{
				sw.cancel(true);
				return;
			}
			
			btnStart.setText("Cancel");
			btnStart.setActionCommand("Cancel");
			
			generatedTextArea.setEnabled(false);
			targetTextArea.setEnabled(false);
			chckbxParallel.setEnabled(false);
			monkeysPerGenerationSizeTextField.setEnabled(false);
			;
			
			final boolean flag = chckbxParallel.isSelected();
			
			sw = new SwingWorker<Void, String>()
			{
				int monkeysPerGenerationSizeInt = Integer.parseInt(monkeysPerGenerationSizeTextField.getText());
				String string = new String();
				List<Generator> generatorList = new LinkedList<Generator>();
//				List<Callable<Void>> callables = new LinkedList<Callable<Void>>();

				@Override
				protected Void doInBackground() throws Exception
				{
//					create the initial random population (strings or arrays of valid characters, 
//					each one same size as the target text size, i.e. TargetText.Length)
					
//					Generator generator = new Generator();
//					string = generator.generateRandomMonkey(targetTextArea.getText().length(), generator.generateStrings());
					
					for (int i = 0; i < monkeysPerGenerationSizeInt; i ++){
						generatorList.add(new Generator());
					}
					for (int x = 0 ; x < 100 ; x++)
					{
						if (this.isCancelled())
							break;
						
						if (x % 10 == 0)
							publish(String.valueOf(x));
							
						
						Thread.sleep(100);
					}
					
					return null;
				}			
				
				@Override
				protected void process(List<String> chunks)
				{
					if (flag == true)
						lblUpdateTime.setText(chunks.get(0));
					
					generatedTextArea.setText(string);
					super.process(chunks);
				}

				@Override
				protected void done()
				{
					lblUpdateTime.setText("Job done");
					
					btnStart.setText("Start");
					btnStart.setActionCommand("Start");
					
					generatedTextArea.setEnabled(true);
					targetTextArea.setEnabled(true);
					chckbxParallel.setEnabled(true);
					monkeysPerGenerationSizeTextField.setEnabled(false);
				}


			};
			
			sw.execute();
		}
		
	}

}








