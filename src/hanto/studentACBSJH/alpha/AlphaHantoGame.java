/*******************************************************************************
 * Copyright (c) 2014 Sean J. Halloran & Alexander C. Bragdon
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Sean Halloran
 *		Alex Bragdon
 *******************************************************************************/
package hanto.studentACBSJH.alpha;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentACBSJH.common.BaseHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

/**
 * A class for playing an AlphaHantoGame. In Alpha Hanto, blue always moves first,
 * each player has one butterfly, and once each is on the board in a valid location,
 * the game ends in a draw.
 * 
 * @author alexbragdon
 *
 */
public class AlphaHantoGame extends BaseHantoGame implements HantoGame {
	
	/**
	 * Private constructed needed to extend BaseHantoGame abstract class.
	 * This will never be called outside this class.
	 * 
	 * @param gameID
	 * @param firstPlayerColor
	 */
	private AlphaHantoGame(HantoGameID gameID,
			HantoPlayerColor firstPlayerColor) {
		super(gameID, firstPlayerColor);
	}
	
	/**
	 * Constructs an AlphaHanto game where the first player to move is always Blue.
	 */
	public AlphaHantoGame()
	{
		this(HantoGameID.ALPHA_HANTO, HantoPlayerColor.BLUE);
	}

	/** (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		checkMakeMoveInputForException(pieceType, from, to);
		
		if(TurnNumber == 1) {
			HantoCoordinateACBSJH toACBSJH = new HantoCoordinateACBSJH(to.getX(), to.getY());
			HantoCoordinateACBSJH otherButterfly = new HantoCoordinateACBSJH(0, 0);
			if(!otherButterfly.isAdjacent(toACBSJH)) {
				throw new HantoException("Cannot move here! The tile is not ajacent to the other butterfly!");
			}
		}
		
		HantoPieceACBSJH pieceToMove = getPieceFromHand(pieceType);
		pieceToMove.setLocation(new HantoCoordinateACBSJH(to));
		TurnNumber++;
		if (TurnNumber == 2) {
			return MoveResult.DRAW;
		} else {
			return MoveResult.OK;
		}
	}

	/** (non-Javadoc)
	 * @see hanto.studentACBSJH.common.BaseHantoGame#setupHands()
	 */
	@Override
	protected void setupHands() {
		HantoPieceACBSJH blueButterfly = new HantoPieceACBSJH(HantoPlayerColor.BLUE);
		HantoPieces.add(blueButterfly);
		
		HantoPieceACBSJH redButterfly = new HantoPieceACBSJH(HantoPlayerColor.RED);
		HantoPieces.add(redButterfly);
	}

}
