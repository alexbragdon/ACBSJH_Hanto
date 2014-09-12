package hanto.studentACBSJH.common;

import hanto.common.HantoCoordinate;

public class HantoCoordinateACBSJH implements HantoCoordinate {

	private int X, Y;
	
	public HantoCoordinateACBSJH(int Xin, int Yin) {
		X = Xin;
		Y = Yin;
	}
	
	@Override
	public int getX() {
		return X;
	}

	@Override
	public int getY() {
		return Y;
	}

}
