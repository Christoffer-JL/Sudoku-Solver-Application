import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Gui implements ActionListener {
	private JTextField[][] matrix = new JTextField[9][9];
	
	/**
	 * Creates and displays a 500 x 500 window with the Sudoku grid, a Solve button and a Clear button.
	 */
	public Gui() {
		createWindow("Sudoku Solver", 500, 500);
	}
	
	private void createWindow(String title, int width, int height) {
		
		Solver solver = new Solver();
		JFrame frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container pane = frame.getContentPane();
		JPanel panelBtns = new JPanel();
		JPanel panelTxt = new JPanel(new GridLayout(9, 9, 0, 0));
		panelTxt.setPreferredSize(new Dimension(500, 500));
		
		JButton solveBtn = new JButton("Solve");
		//if the user input is accepted, convert the inputs to integers and attempt to solve, else prompt user to insert a number between 1 and 9.
		//if solvable, display the solved sudoku in the gui.
		//if not solvable, display a message informing the user of this.
		solveBtn.addActionListener(event -> {
			if (checkTextFields()) {
				solver.setMatrix(intMatrix());
				if (solver.solve()) {
					transferIntMatrix(solver.getMatrix());
				} else {
					JOptionPane.showMessageDialog(null, "Not solvable!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Insert a number between 1 and 9.");
			}
		});
		panelBtns.add(solveBtn);
		
		JButton clearBtn = new JButton("Clear");
		clearBtn.addActionListener(event -> {
			if (checkTextFields()) {
				solver.setMatrix(intMatrix());
				solver.clear();
			}
			emptyTextFields();
		});
		panelBtns.add(clearBtn);
		
		addMatrix(panelTxt);
		pane.add(panelTxt);
		pane.add(panelBtns, BorderLayout.SOUTH);
				
		frame.pack();
		frame.setVisible(true);
	}
	
	private void addMatrix(JPanel panel) {
		for (int row = 0; row < matrix.length; row++) {
			for (int col = 0; col < matrix[row].length; col++) {
				JTextField txt = new JTextField(1);
				txt.setHorizontalAlignment(JTextField.CENTER);
				Font currentFont = txt.getFont();
				Font largerFont = currentFont.deriveFont(30.0f);
				txt.setFont(largerFont);
				
				if ((((row/3) + (col/3)) % 2 == 0)) {
					txt.setBackground(Color.decode("#003333"));
					txt.setForeground(Color.WHITE);
					txt.setCaretColor(Color.WHITE);
				} else {
					txt.setForeground(Color.BLACK);
					txt.setCaretColor(Color.BLACK);
				}
				matrix[row][col] = txt;
				panel.add(matrix[row][col]);
			}
		}
	}
	
	//converts the user inputs from textfields to integers and returns the int[][]
	private int[][] intMatrix() {
		int[][] matrix = new int[9][9];
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (!this.matrix[i][j].getText().isEmpty()) {
					matrix[i][j] = Integer.parseInt(this.matrix[i][j].getText());
				}
			}
		}
		return matrix;
	}
	
	//sets the non-empty textfields to null
	private void emptyTextFields() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (!this.matrix[i][j].getText().isEmpty()) {					
					matrix[i][j].setText(null);
				}
			}
		}
	}
	
	//transfers the int matrix inputs into textfields for display in the gui
	private void transferIntMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				this.matrix[i][j].setText(matrix[i][j]+ "");
			}
		}
	}
	
	//makes sure the user input is a number with length 1
	private boolean checkTextFields() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				String s = matrix[i][j].getText();
				if (!s.isBlank() && (s.length() != 1 || !Character.isDigit(s.charAt(0)) || Integer.parseInt(s) == 0)) {	
					return false;
				}
			}
		}
		return true;
	}
	
	public void actionPerformed(ActionEvent e) {
	}
	
}
