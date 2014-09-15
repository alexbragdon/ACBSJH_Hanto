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
package hanto.studentACBSJH.beta;

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
 * @author alexbragdon
 *
 */
public class BetaHantoGame extends BaseHantoGame implements HantoGame {

	/**
	 * @param gameID
	 * @param firstPlayerColor
	 */
	private BetaHantoGame(HantoGameID gameID,
			HantoPlayerColor firstPlayerColor) {
		super(gameID, firstPlayerColor);
	}
	
	/**
	 * @param firstPlayerColor
	 */
	public BetaHantoGame(HantoPlayerColor firstPlayerColor)
	{
		this(HantoGameID.BETA_HANTO, firstPlayerColor);
	}

	public BetaHantoGame()
	{
		this(HantoPlayerColor.BLUE);
	}

	/** (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		checkMakeMoveInputForException(pieceType, from, to);
		
		checkForNoAdjacentPieceToDestination(to);
		
		//Ensures that both players play their butterfly on or before the fourth turn. 
		if ((TurnNumber > 5) && (getCurrentPlayersTurn() == HantoPlayerColor.BLUE) && blueButterfly == null 
				&& (pieceType != HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Must place butterfly on or before the fourth turn!!");
		}
		if ((TurnNumber > 5) && (getCurrentPlayersTurn() == HantoPlayerColor.RED) && redButterfly == null 
				&& (pieceType != HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Must place butterfly on or before the fourth turn!!");
		}
			
		
		
		HantoPieceACBSJH pieceToMove = getPieceFromHand(pieceType);
		pieceToMove.setLocation(new HantoCoordinateACBSJH(to));
		
		//Updates the the butterflys location to prevent having to iterate too many times. 
		if ((pieceType == HantoPieceType.BUTTERFLY) && (getCurrentPlayersTurn() == HantoPlayerColor.BLUE)) {
			blueButterfly = pieceToMove;
			blueButterfly.setLocation(new HantoCoordinateACBSJH(to));
		}
		if ((pieceType == HantoPieceType.BUTTERFLY) && (getCurrentPlayersTurn() == HantoPlayerColor.RED)) {
			redButterfly = pieceToMove;
			redButterfly.setLocation(new HantoCoordinateACBSJH(to));
		}

		TurnNumber++;
		
		
		if (countPiecesSurroundingButterfly(HantoPlayerColor.RED) == 6) {
			return MoveResult.BLUE_WINS;
		} else if (countPiecesSurroundingButterfly(HantoPlayerColor.BLUE) == 6) {
			return MoveResult.RED_WINS;
		}
		if (TurnNumber == 12) {
			return MoveResult.DRAW;
		}
		
		return MoveResult.OK;
	}
	
	private void checkForNoAdjacentPieceToDestination(HantoCoordinate to) throws HantoException
	{
		if(TurnNumber != 0)
		{
			boolean foundAdjacentPiece = false;
			for(HantoPieceACBSJH hp : HantoPieces)
			{
				if(!hp.isInHand() && hp.getLocation().isAdjacent(to))
				{
					foundAdjacentPiece = true;
				}
			}
			if(!foundAdjacentPiece)
			{
				throw new HantoException("The destination "+(new HantoCoordinateACBSJH(to)).toString() 
						+ " is not adjacent to any piece and therefor not a valid destination"); 
			}
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
		
		for (int i=0; i<5; i++) {
			HantoPieceACBSJH blueSparrow = new HantoPieceACBSJH(HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
			HantoPieceACBSJH redSparrow = new HantoPieceACBSJH(HantoPlayerColor.RED, HantoPieceType.SPARROW);
			HantoPieces.add(blueSparrow);
			HantoPieces.add(redSparrow);
		}
	}
	
	protected void addValidHantoPieceTypes()
	{
		super.addValidHantoPieceTypes();
		ValidHantoPieceTypes.add(HantoPieceType.SPARROW);
	}

}
