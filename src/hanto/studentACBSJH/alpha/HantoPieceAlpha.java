package hanto.studentACBSJH.alpha;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class HantoPieceAlpha implements HantoPiece {
	
	private HantoPieceType type;
	private HantoPlayerColor playerColor;
	
	public HantoPieceAlpha(HantoPlayerColor color) {
		type = HantoPieceType.BUTTERFLY;
		playerColor = color;
	}
	
	@Override
	public HantoPlayerColor getColor() {
		return playerColor;
	}

	@Override
	public HantoPieceType getType() {
		return type;
	}

}
