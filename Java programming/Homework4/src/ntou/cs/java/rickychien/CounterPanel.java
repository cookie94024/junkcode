package ntou.cs.java.rickychien;

import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * CounterPanel Class ��ܦ��X���u�B���B�x�ΦbPanel�W
 */
public class CounterPanel extends JPanel {
	CounterPanel() {
		JLabel label = new JLabel( 
				"Lines: " + 0 +
				", Ovals: " + 0 +
				", Rectangles: " + 0
				);
		add( label );
	}
	
	CounterPanel( int lines, int ovals, int rects ) {
		JLabel label = new JLabel( 
				"Lines: " + lines +
				", Ovals: " + ovals +
				", Rectangles: " + rects
				);
		add( label );
	}
}
