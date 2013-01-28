package ntou.cs.java.rickychien;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * ShapeGeneratorTest Class �D�{��������
 * ø�s�F���ShapeGenerator���򥻤���
 */
public class ShapeGeneratorTest {
	public static void main(String[] args) {
		//�ϥΪ̿�J��Shape�Ӽ�
		int numOfShape = 0; 
		String inputNum = JOptionPane.showInputDialog( "Number of shapes" );
		
		//�ˬd�ϥΪ̬O�_����J
		if( inputNum != null && !( inputNum.equals( "" ) ) ) {
			numOfShape = Integer.parseInt( inputNum );
		} else {
			return;
		}
		
		//ø�s²�檺�{������
		JFrame frame = new JFrame( "Shape Generator" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		frame.setLayout( new BorderLayout() );
		frame.setBackground( Color.GRAY );
		
		ShapeGenerator shapeGenerator = new ShapeGenerator( numOfShape );
		CounterPanel counterPanel = new CounterPanel( 
				shapeGenerator.getLines(), 
				shapeGenerator.getOvals(), 
				shapeGenerator.getRectangles()
				);
		
		frame.add( shapeGenerator, BorderLayout.CENTER );
		frame.add( counterPanel, BorderLayout.SOUTH );
		frame.setSize( 700, 600 );
		frame.setVisible( true );
	}
}
