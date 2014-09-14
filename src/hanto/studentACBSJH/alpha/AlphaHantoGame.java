/**
 * *********8TRIVIAL CHANGE*******
 */
package hanto.studentACBSJH.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentACBSJH.common.BaseHantoGame;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

/**
 * @author alexbragdon
 *
 */
public class AlphaHantoGame extends BaseHantoGame implements HantoGame {

	public AlphaHantoGame()
	{
		super();
	}
	
	/* (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		HantoPieceACBSJH newPiece = new HantoPieceACBSJH(getCurrentPlayersTurn());
		
		HantoPieces.add(newPiece);
		TurnNumber++;
		
		return MoveResult.OK;
	}

	/* (non-Javadoc)
	 * @see hanto.common.HantoGame#getPieceAt(hanto.common.HantoCoordinate)
	 */
	@Override
	public HantoPiece getPieceAt(HantoCoordinate where) {
		return new HantoPieceACBSJH(HantoPlayerColor.BLUE);
	}

	/* (non-Javadoc)
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
