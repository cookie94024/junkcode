package ntou.cs.java.rickychien;

import java.awt.Color;
import java.awt.Graphics;

/*
 * MyShape Class �e�X�H���Ϊ����ϧΡ]�u�B���B�x�Ρ^
 * ���@�ө�H���O�A�z�L��H�� draw ��k�e�X�U�ؤ��P�����ϧ�
 */
public abstract class MyShape {
	private int x;
	private int y;
	private Color c;
	
	MyShape() {
		setX( 0 );
		setY( 0 );
		setColor( Color.BLACK );
	}
	
	MyShape( int x, int y, Color c ) {
		setX( x );
		setY( y );
		setColor( c );
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getX() {
		return x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getY() {
		return y;
	}
	
	public void setColor( Color c ) {
		this.c = c;
	}

	public Color getColor() {
		return c;
	}
	
	// Abstract Method �z�LMyShape����I�s���P��Shape���� draw( g );
	public abstract void draw( Graphics g );
}
