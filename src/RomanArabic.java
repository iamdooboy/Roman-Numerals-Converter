import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Last 4 UIN: 1062
 * Last date modified: 10/17/2016
 * RomanArabic 
 * COP 3003
 */
public class RomanArabic extends JFrame implements KeyListener {

	public char arabic;
	public char roman;
	public JTextField arabicField = new JTextField(15);
	public JTextField romanField = new JTextField(15);
	public JLabel romanLabel = new JLabel("Roman Numeral");
	public JLabel arabicLabel = new JLabel("Arabic Numeral");
	static JPanel panel = new JPanel();
	private static int userInp;
	private static int intValue;

	public RomanArabic(String title) {

		super(title);

		panelConstruction();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	/*
	 * creating the panel, text fields, and layout
	 */
	public JPanel panelConstruction() {

		setSize(300, 100);

		panel.setLayout(new GridLayout(2, 1)); 

		panel.add(arabicLabel, BorderLayout.WEST);

		panel.add(arabicField, BorderLayout.CENTER);

		arabicField.addKeyListener(this);

		romanField.addKeyListener(this);

		panel.add(romanLabel, BorderLayout.WEST);

		panel.add(romanField, BorderLayout.CENTER);

		add(panel);

		setVisible(true);

		setResizable(false);

		return panel;

	}

	/*
	 * converting to Roman
	 */
	public String toRoman(int arabic) {

			String Roman = ""; //result string

			//1-9 in Roman
			String onesArray[] = { "I", "II", "III", 
								   "IV", "V", "VI", 
								   "VII", "VIII", "IX" };
			//10-90 in Roman 
			String tensArray[] = { "X", "XX", "XXX", 
								   "XL", "L", "LX", "LXX", 
								   "LXXX", "XC" };
			//100-900 in Roman
			String hundredsArray[] = { "C", "CC", "CCC", 
									   "CD", "D", "DC", 
									   "DCC", "DCCC", "CM" };
			
			//get the ones 
			int ones = arabic % 10;

			//get the tens
			arabic = (arabic - ones) / 10;
			int tens = arabic % 10;

			//get the hundreds
			arabic = (arabic - tens) / 10;
			int hundreds = arabic % 10;

			//get the thousands 
			arabic = (arabic - hundreds) / 10;
			for (int i = 0; i < arabic; i++) {
				Roman = Roman + "M";
			}

			//write the hundreds
			if (hundreds >= 1) {
				Roman = Roman + hundredsArray[hundreds - 1];
			}

			//write the tens
			if (tens >= 1) {
				Roman = Roman + tensArray[tens - 1];
			}

			//write the ones
			if (ones >= 1) {
				Roman = Roman + onesArray[ones - 1];
			}

			//return the string.
			return String.valueOf(Roman);
	}

	/*
	 * converting to Arabic
	 */
	public static String toArabic(String roman) {
		int arabic = 0; //result int
		int last = 0;
		int current = 0;

		for (int i = 0; i < roman.length(); i++) {
			if (roman.charAt(i) == 'I' || roman.charAt(i) == 'i') {
				current = 1;
			}
			if (roman.charAt(i) == 'V' || roman.charAt(i) == 'v') {
				current = 5;
			}
			if (roman.charAt(i) == 'X' || roman.charAt(i) == 'x') {
				current = 10;
			}
			if (roman.charAt(i) == 'L' || roman.charAt(i) == 'l') {
				current = 50;
			}
			if (roman.charAt(i) == 'C' || roman.charAt(i) == 'c') {
				current = 100;
			}
			if (roman.charAt(i) == 'D' || roman.charAt(i) == 'd') {
				current = 500;
			}
			if (roman.charAt(i) == 'M' || roman.charAt(i) == 'm') {
				current = 1000;
			}
			
			/*
			 * if the last number is less than the current number,
			 * subtract the last number from the current number
			 * else add current number
			 */
			if (last < current && last != 0) {
				current = current - last;
				arabic = arabic - last;
				arabic = arabic + current;
				last = current;
				current = 0;
			} else { 
				last = current;
				arabic = arabic + current;
				current = 0;
			}
			
		} if (arabic > 0){ 
			return String.valueOf(arabic);
		} else {
			return String.valueOf("");
		}
	}

	/*
	 * implementation of keyReleased 
	 */
	public void keyReleased(KeyEvent key) {
		
		if (key.getSource() == arabicField) { // if type in arabicField
			
			String romanText = romanField.getText();
			String arabicText = null;
			arabicText = arabicField.getText(); //get text from arabicField
			try {
				userInp = Integer.parseInt(arabicText); //parse into int
				if (userInp > 3999) { //reject if int is greater than 3999
					romanText = romanField.getText();
					arabicField.setText(toArabic(romanText));
				} else if (userInp == 0){ //handle input '0' from user
					arabicField.setText(null); 
				} else {
					//convert int to Roman then set it to romanField
					romanField.setText(toRoman(userInp)); 
				}								   
			} catch (NumberFormatException e) { //exception handling when
												//user enter letters/symbols
				arabicField.setText(toArabic(romanField.getText()));
			}
			if (arabicText.isEmpty()) { //if arabicText length is empty (0)
				arabicField.setText(null); //set both fields to null
				romanField.setText(null);
			}
		} else {  // type in romanField
			String romanText = null;
			String arabicText = null;
			try {
				romanText = romanField.getText(); //get text from romanField
				 //convert to Arabic then parse into int
				userInp = Integer.parseInt(toArabic(romanText));				  
				if (userInp > 3999) { //reject if int is greater than 3999
					arabicText = arabicField.getText(); //get text from field
					//parse Arabic into string then convert to Roman then set
					//it to romanField
					romanField.setText(toRoman(Integer.parseInt(arabicText)));
				} else {
					//convert romanText to Arabic then set it to arabicField
					arabicField.setText(toArabic(romanText));
					intValue = Integer.parseInt(arabicField.getText());
					romanField.setText(toRoman(intValue));
					intValue = 0; // reset after used
				}
			} catch (NumberFormatException e) {
				//exception handling when user type in invalid inputs
				romanField.setText(toRoman(intValue));
			}
			if (romanText.isEmpty()) { //if romanText length is empty (0)
				arabicField.setText(null); //set both fields to null
				romanField.setText(null);
			}
		}
	}

	/*
	 * not used
	 */
	public void keyPressed(KeyEvent e) {

	}

	/*
	 * not used
	 */
	public void keyTyped(KeyEvent e) {

	}

	public static void main(String args[]) {
		RomanArabic conversion = new RomanArabic("Roman <--> Arabic");
	}

}
