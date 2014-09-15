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
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentACBSJH.common.BaseHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

/**
 * @author alexbragdon
 *
 */
public class AlphaHantoGame extends BaseHantoGame implements HantoGame {
	
	/** (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		checkToCoordinateIsValid(to);
		checkForNoPieceToMoveException(from, to);
		checkForMovingWrongColorPieceException(from);
		checkFirstMoveIsToOriginException(to);
		
		//if to is not a valid coordinate
		if(to == null)
		{
			throw new HantoException("Cannot move piece to a null location.");
		}
		
		//make sure only butterflies are in play
		if(!pieceType.equals(HantoPieceType.BUTTERFLY))
		{
			throw new HantoException("Expected butterfly to be moved for Alpha Hanto, got " + pieceType.toString());
		}
		
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
