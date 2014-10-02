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
package hanto.studentACBSJH.delta;

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
public class DeltaHantoGame extends BaseHantoGame implements HantoGame {
	
	/**
	 * The number of moves that can be made before the butterfly must be placed
	 */
	private final int turnsToButterfly = 5;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int sparrowsPerPlayer = 4;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int crabsPerPlayer = 4;
	
	/**
	 * @param gameID
	 * @param firstPlayerColor
	 */
	private DeltaHantoGame(HantoGameID gameID, HantoPlayerColor firstPlayerColor) {
		super(gameID, firstPlayerColor);
	}
	
	/**
	 * @param gameID
	 * @param firstPlayerColor
	 */
	public DeltaHantoGame(HantoPlayerColor firstPlayerColor) 
	{
		this(HantoGameID.DELTA_HANTO, firstPlayerColor);
	}

	/** (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		
		if (pieceType == null && from == null && to == null){
			MoveResult mr = null;
			if (getCurrentPlayersTurn() == HantoPlayerColor.BLUE) {
				mr = MoveResult.RED_WINS; 
			} else {
				mr = MoveResult.BLUE_WINS;
			}
			return mr;
		}
		
		checkMakeMoveInputForException(pieceType, from, to);
		checkButterflyIsPlacedByTurn(turnsToButterfly, pieceType);
		
		movePiece(pieceType, from, to);
		checkForContinuity();
		checkPlayerMovesTheCorrectNumberOfHexes(pieceType, from, to);
		
		TurnNumber++;
		
		MoveResult mr = checkForWinner();
		
		return mr;
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
		
		for (int i=0; i<sparrowsPerPlayer; i++) {
			HantoPieceACBSJH blueSparrow = new HantoPieceACBSJH(HantoPlayerColor.BLUE, HantoPieceType.SPARROW);
			HantoPieceACBSJH redSparrow = new HantoPieceACBSJH(HantoPlayerColor.RED, HantoPieceType.SPARROW);
			HantoPieces.add(blueSparrow);
			HantoPieces.add(redSparrow);
		}
		
		for (int i=0; i<crabsPerPlayer; i++) {
			HantoPieceACBSJH blueCrab = new HantoPieceACBSJH(HantoPlayerColor.BLUE, HantoPieceType.CRAB);
			HantoPieceACBSJH redCrab = new HantoPieceACBSJH(HantoPlayerColor.RED, HantoPieceType.CRAB);
			HantoPieces.add(blueCrab);
			HantoPieces.add(redCrab);
		}
	}
	
	protected void checkPlayerMovesTheCorrectNumberOfHexes(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		
		if (pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB) {
			HantoCoordinateACBSJH newTo = (HantoCoordinateACBSJH) to;
			HantoCoordinateACBSJH newFrom = (HantoCoordinateACBSJH) from;
			
			if (!newTo.isAdjacent(newFrom)) {
				throw new HantoException(pieceType.toString() + " cannot move more than one space.");
			}		
		}
	}
}
