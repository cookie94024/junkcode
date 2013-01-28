package ntou.cs.java.rickychien;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

/*
 * ShapeGenerator Class �D�{��
 * �̷ӨϥΪ̿�J�n��ܪ��ϧέӼơA�H����ø�s�b�e���W
 */
public class ShapeGenerator extends JPanel {
	private int numOfLine;
	private int numOfOval;
	private int numOfRect;
	//�w�qMyShape�}�C����Ӧs��h���ϧ�
	MyShape[] myShapes;
	
	//�{���i�J�I���|�I�s��L�Ѽƪ��غc�̡]���|�Ψ�^
	ShapeGenerator() {
	}
	
	//�{���ݱ����ϥΪ̿�J���ӼơA�ç�ѼƶǶi���غc��
	ShapeGenerator( int numOfShape ) {
		//�ΨӰO�нu�B���B�x�Ϊ��X�{����
		numOfLine = 0;
		numOfOval = 0;
		numOfRect = 0;
		
		myShapes = new MyShape[numOfShape];
		Random gen = new Random();
		
		for( int i = 0; i < numOfShape; i++ ) {
			//�غc�ϧή��H������X�b�y�Ц�m
			int x = gen.nextInt( 300 );
			//�غc�ϧή��H������y�b�y�Ц�m
			int y = gen.nextInt( 300 );
			//�غc�ϧή��H������Color
			int rgb = gen.nextInt();
			
			//���T�عϧΡ]�u�B���B�x�Ρ^�A�H����@��
			switch ( gen.nextInt( 3 ) ) {
			case 0:
				++numOfLine;
				myShapes[i] = new MyLine( x, y, new Color( rgb ) );
				break;
			case 1:
				++numOfOval;
				myShapes[i] = new MyOval( x, y, new Color( rgb ) );
				break;
			case 2:
				++numOfRect;
				myShapes[i] = new MyRectangle( x, y, new Color( rgb ) );
				break;
			}
		}
	}
	
	public int getLines() {
		return numOfLine;
	}
	
	public int getOvals() {
		return numOfOval;
	}
	
	public int getRectangles() {
		return numOfRect;
	}
	
	// Override ø�Ϥ�k�éI�s�۩w�q���ϧΪ���ø�s�Ϥ�
	public void paintComponent( Graphics g ) {
		super.paintComponents( g );
		
		for( MyShape shape : myShapes ) {
			shape.draw( g );
		}
	}
}
