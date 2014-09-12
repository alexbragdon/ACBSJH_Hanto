package hanto.studentACBSJH.alpha;

import hanto.common.HantoCoordinate;

public class HantoCoordinateAlpha implements HantoCoordinate {

	private int X, Y;
	
	public HantoCoordinateAlpha(int Xin, int Yin) {
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
