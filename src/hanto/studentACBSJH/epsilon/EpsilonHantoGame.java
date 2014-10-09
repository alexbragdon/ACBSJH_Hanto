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
package hanto.studentACBSJH.epsilon;

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
public class EpsilonHantoGame extends BaseHantoGame implements HantoGame {

	/**
	 * The number of moves that can be made before the butterfly must be placed
	 */
	private final int turnsToButterfly = 5;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int sparrowsPerPlayer = 2;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int crabsPerPlayer = 6;
	
	/**
	 * The number of horses in each player's hand.
	 */
	private final int horsesPerPlayer = 4;
	
	/**
	 * @param gameID
	 * @param firstPlayerColor
	 */
	private EpsilonHantoGame(HantoGameID gameID, HantoPlayerColor firstPlayerColor) {
		super(gameID, firstPlayerColor);
	}
	
	/**
	 * Constructs a GammaHantoGame
	 * 
	 * @param firstPlayerColor the color of the first player to move
	 */
	public EpsilonHantoGame(HantoPlayerColor firstPlayerColor)
	{
		this(HantoGameID.EPSILON_HANTO, firstPlayerColor);
	}

	/** (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		preventMovesAfterGameOver();
		
		if (pieceType == null && from == null && to == null){
			MoveResult mr = null;
			if (getCurrentPlayersTurn() == HantoPlayerColor.BLUE) {
				mr = MoveResult.RED_WINS; 
			} else {
				mr = MoveResult.BLUE_WINS;
			}
			setGameOver();
			return mr;
		}
		
		checkMakeMoveInputForException(pieceType, from, to);
		checkButterflyIsPlacedByTurn(turnsToButterfly, pieceType);
		
		movePiece(pieceType, from, to);
		checkForContinuity();
		
		checkMoveForBadNeighbors(from, to);
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
		
		for (int i=0; i<horsesPerPlayer; i++) {
			HantoPieceACBSJH blueHorse = new HantoPieceACBSJH(HantoPlayerColor.BLUE, HantoPieceType.HORSE);
			HantoPieceACBSJH redHorse = new HantoPieceACBSJH(HantoPlayerColor.RED, HantoPieceType.HORSE);
			HantoPieces.add(blueHorse);
			HantoPieces.add(redHorse);
		}
	}
	
	/**
	 * 
	 * Prevents either the butterfly or the crab from walking more than one hex, the sparrow from flying more than four hexes,
	 * and the horse from jumping in a non straight direction.
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void checkPlayerMovesTheCorrectNumberOfHexes(HantoPieceType pieceType, HantoCoordinate from, 
			HantoCoordinate to) throws HantoException {
		if(from != null){
			HantoCoordinateACBSJH ACBSJHTo = new HantoCoordinateACBSJH(to);
			HantoCoordinateACBSJH ACBSJHFrom = new HantoCoordinateACBSJH(from);
			
			if ((pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB) && from != null) {		
				if (!ACBSJHTo.isAdjacent(ACBSJHFrom)) {
					throw new HantoException(pieceType.toString() + " cannot move more than one space.");
				}		
			} else if (pieceType == HantoPieceType.HORSE)
			{
				if (!ACBSJHFrom.isStraightLine(ACBSJHTo)) {
					throw new HantoException("The horse must jump in a straight line!");
				}
			}
		}
	}
	
	protected void addValidHantoPieceTypes()
	{
		super.addValidHantoPieceTypes();
		ValidHantoPieceTypes.add(HantoPieceType.SPARROW);
		ValidHantoPieceTypes.add(HantoPieceType.CRAB);
		ValidHantoPieceTypes.add(HantoPieceType.HORSE);
	}
}
