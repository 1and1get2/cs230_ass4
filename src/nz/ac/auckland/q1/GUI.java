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
import nz.ac.auckland.q3.ScoreMatcher;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class GUI extends JFrame implements ActionListener
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
	public GUI()
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
					throw new Exception("length of targetTextArea(" + targetTextArea.getText().length() + ") out of range (1-300)");
				}
				for (char c : targetTextArea.getText().toCharArray()){
					int cInt = (int) c;
					if (cInt != 10 && cInt != 13 && (cInt < 32 || cInt > 126)){
						throw new Exception("character " + c + " not in set of {10, 13, 32-126}");
					}
				}
				} catch (Exception exception) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, exception.toString(), "Error", 2);
//					exception.printStackTrace();
					System.err.println(exception.toString());
//					sw.cancel(true);	// don't even think this statement is necessary
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

				String targetString = targetTextArea.getText();
				String generatedString = new String();
				String[] string = new String[monkeysPerGenerationSizeInt];
				int[] currentScore = new int[monkeysPerGenerationSizeInt];
				List<String> theWholeGenerationList = new LinkedList<String>();
				List<String> theNewGenerationList = new LinkedList<String>();
				int theBestMatchIndex = 0;
//				List<Callable<Void>> callables = new LinkedList<Callable<Void>>();

				@Override
				protected Void doInBackground() throws Exception
				{
//					create the initial random population (strings or arrays of valid characters, 
//					each one same size as the target text size, i.e. TargetText.Length)					
					for (int i = 0; i < monkeysPerGenerationSizeInt; i ++){
						theWholeGenerationList.add(new Generator().generateRandomMonkey(targetTextArea.getText().length()));	
					}
					

/*					List<Callable<Void>> callables = new LinkedList<Callable<Void>>();
					if (flag){
						// Parallel
						int chunk = 500;
		                
		                for (int low = 0; low < monkeysPerGenerationSizeInt; low += chunk) {
		                    final int Low = low;
		                    final int High = Math.min(monkeysPerGenerationSizeInt, low+chunk);
		                    callables.add(new Callable<Void>() {                        
		                        public Void call() {
		                            for (int i = Low; i < High; i++) {
//		                                int k = task(i);
		                            	// TODO:
		                            	string = new String[monkeysPerGenerationSizeInt];
		                            }
		                            return null;
		                        }
		                    });
							int maxallowed_threads = 1 * Runtime.getRuntime().availableProcessors();
				            ExecutorService executor = new ForkJoinPool(maxallowed_threads); 
							List<Future<Void>> futures = executor.invokeAll(callables);
		                }
					} else {
						// sequentially
						for (int i = 0; i < monkeysPerGenerationSizeInt; i++){
							//TODO:
							
						}
					}*/

					
					for (int x = 0 ;; x++)
					{
						int genPerSecond = 0;
						// evolution loop
						if (this.isCancelled())
							break;
						for (int i = 0; i < monkeysPerGenerationSizeInt; i ++){
//							theWholeGenerationList.add(new Generator().generateRandomMonkey(targetTextArea.getText().length()));
//							Generator generator = new Generator();
//							theWholeGenerationList.add(generator.generateRandomMonkey(targetTextArea.getText().length()));
							// compute fitness scores and find best match of the current generation
							ScoreMatcher scoreMatcher = new ScoreMatcher();
							currentScore[i] = scoreMatcher.diff(targetString,theWholeGenerationList.get(i));
							if (currentScore[0] > currentScore[i]){
								theBestMatchIndex = i;
								generatedString = theWholeGenerationList.get(i);
							}
						}

						
						if (x % 10 == 0){

							publish(String.valueOf(x));
						}
						
						Thread.sleep(100);
//						Thread.sleep(10);
					}
					
					return null;
				}			
				
				@Override
				protected void process(List<String> chunks)
				{
					//if (flag == true)
						lblUpdateTime.setText(chunks.get(0));
					
					generatedTextArea.setText(chunks.get(1));
					for (Object s:chunks.toArray()) System.out.println(s.toString());
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
	public List<String> createNewGeneration(List<String> oldGeneration, int populationSize){
		for (int i = 0; i < populationSize / 2; i++){
			ScoreMatcher scoreMatcher = new ScoreMatcher();
			
		}
		return null;
	}
	public int getPopulationSize(){
		return Integer.parseInt((monkeysPerGenerationSizeTextField.getText()));
	}
	public boolean isParallel(){
		return chckbxParallel.isSelected();
	}

}









