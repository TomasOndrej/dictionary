package cz.english.dictionary;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainWindow {

	private final String WINDOW_NAME = "dictionary";
	
	private Map<String, String> words = new LinkedHashMap<String, String>();
	
	private List<String> history = new ArrayList<String>();
	
	private int wordCounter = 0;
	private int wordMaxCounter = 0;
	
	public MainWindow(Map<String, String> words) {
		if(words.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Nebyl nalezen slovník", "Chyba", JOptionPane.ERROR_MESSAGE);
		}
		this.words = words;
		
		final List<String> list = getRandomWord();
		final JFrame frame = new JFrame(WINDOW_NAME);
		final JPanel panelOne = new JPanel();
		final JPanel panelTwo = new JPanel();
		final JPanel panelThree = new JPanel();
		final JTextField textField = new JTextField(20);
		final JLabel labelCZ = new JLabel(list.get(0));
		final JLabel labelEN = new JLabel(list.get(1));
		final JButton buttonBack = new JButton("BACK");
		final JButton buttonSend = new JButton("SEND");
		final JButton buttonNext = new JButton("NEXT");

		frame.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				textField.requestFocus();
			}
		});

		textField.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				showButton(labelEN, textField);
			}
		});

		textField.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) { }

			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_R) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					repeatWord(labelEN, labelCZ, textField);
				}
			}

			public void keyPressed(KeyEvent e) { }
		});
		
		buttonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backButton(labelEN, labelCZ, textField);
			}
		});

		buttonSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showButton(labelEN, textField);
			}
		});

		buttonNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repeatWord(labelEN, labelCZ, textField);
			}
		});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setSize(480, 250);
		frame.setVisible(true);

		labelCZ.setFont(new Font("Serif", Font.PLAIN, 20));
		labelEN.setFont(new Font("Serif", Font.PLAIN, 20));
		labelEN.setVisible(false);

		panelOne.add(labelCZ);
		panelTwo.add(textField);
		panelTwo.add(buttonBack);
		panelTwo.add(buttonSend);
		panelTwo.add(buttonNext);
		panelThree.add(labelEN);

		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.add(panelOne);
		frame.add(panelTwo);
		frame.add(panelThree);
	}

	public List<String> getRandomWord() {
		int random = ThreadLocalRandom.current().nextInt(0, words.size());
		List<String> randomWord = new ArrayList<String>();

		int index = 0;
		for (Map.Entry<String, String> iter : words.entrySet()) {
			if (index == random) {
				randomWord.add(iter.getKey());
				randomWord.add(iter.getValue());
				history.add(iter.getKey());
			}
			index++;
		}
		
		return randomWord;
	}
	
	private void backButton(JLabel labelEN, JLabel labelCZ, JTextField textField) {
		if (wordCounter > 0) {
			String wordKeyBefore = history.get(wordCounter - 1);
			String wordValueBefore = words.get(wordKeyBefore);
			
			System.out.println("B- " + wordKeyBefore);
			
			labelCZ.setText(wordKeyBefore);
			labelEN.setVisible(false);
			labelEN.setText(wordValueBefore);
			textField.setText("");
			
			wordCounter--;	
		}
	}

	private void showButton(JLabel labelEN, JTextField textField) {
		labelEN.setVisible(true);
		if (textField.getText().equals(labelEN.getText())) {
			labelEN.setForeground(Color.BLUE);
		} else {
			labelEN.setForeground(Color.RED);
		}
	}
	
	private void repeatWord(JLabel labelEN, JLabel labelCZ, JTextField textField) {
		if (wordCounter < wordMaxCounter) {
			wordCounter++;
			String wordKeyNext = history.get(wordCounter);
			String wordValueNext = words.get(wordKeyNext);
			
			labelCZ.setText(wordKeyNext);
			labelEN.setVisible(false);
			labelEN.setText(wordValueNext);
			textField.setText("");
			
			System.out.println("R- " + wordKeyNext);
			
		} else {
			List<String> list = getRandomWord();
			labelCZ.setText(list.get(0));
			labelEN.setVisible(false);
			labelEN.setText(list.get(1));
			textField.setText("");
			wordMaxCounter++;
			wordCounter++;
			
			System.out.println("R+ " + list.get(0));
		}
		
	}
}
