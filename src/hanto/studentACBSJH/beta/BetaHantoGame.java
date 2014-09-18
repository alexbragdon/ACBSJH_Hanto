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
 * Controller for playing the BetaHantoGame
 * 
 * @author alexbragdon
 *
 */
public class BetaHantoGame extends BaseHantoGame implements HantoGame {

	/**
	 * The number of moves that can happen before a draw
	 */
	private final int turnsToDraw = 12;
	
	/**
	 * The number of moves that can be made before the butterfly must be placed
	 */
	private final int turnsToButterfly = 5;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int sparrowsPerPlayer = 5;
	
	/**
	 * Private constructed needed to extend BaseHantoGame abstract class.
	 * This will never be called outside this class.
	 * 
	 * @param gameID
	 * @param firstPlayerColor
	 */
	private BetaHantoGame(HantoGameID gameID,
			HantoPlayerColor firstPlayerColor) {
		super(gameID, firstPlayerColor);
	}
	
	/**
	 * Constructs a BetaHantoGame
	 * 
	 * @param firstPlayerColor the color of the first player to move
	 */
	public BetaHantoGame(HantoPlayerColor firstPlayerColor)
	{
		this(HantoGameID.BETA_HANTO, firstPlayerColor);
	}

	/**
	 * Constructs a BetaHantoGame where Blue goes first.
	 */
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
		
		checkButterflyIsPlacedByTurn(turnsToButterfly, pieceType);
		
		HantoPieceACBSJH pieceToMove = getPieceFromHand(pieceType);
		pieceToMove.setLocation(new HantoCoordinateACBSJH(to));
		
		saveButterfly(pieceToMove);

		TurnNumber++;
		
		MoveResult mr = checkForWinner();
		if(mr == MoveResult.OK)
		{
			mr = drawGameOnTurn(turnsToDraw);
		}
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
	}
	
	/** (non-Javadoc)
	 * @see hanto.studentACBSJH.common.BaseHantoGame#addValidHantoPieceTypes()
	 */
	@Override
	protected void addValidHantoPieceTypes()
	{
		super.addValidHantoPieceTypes();
		ValidHantoPieceTypes.add(HantoPieceType.SPARROW);
	}

}
