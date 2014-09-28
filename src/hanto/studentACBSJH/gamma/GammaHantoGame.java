/*******************************************************************************
 * Copyright (c) 2014 Sean J. Halloran
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Sean Halloran
 *******************************************************************************/
package hanto.studentACBSJH.gamma;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentACBSJH.common.BaseHantoGame;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

/**
 * @author Sean
 *
 */
public class GammaHantoGame extends BaseHantoGame implements HantoGame {

	/**
	 * The number of moves that can happen before a draw
	 */
	private final int turnsToDraw = 40;
	
	/**
	 * The number of moves that can be made before the butterfly must be placed
	 */
	private final int turnsToButterfly = 5;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int sparrowsPerPlayer = 5;
	
	/**
	 * @param gameID
	 * @param firstPlayerColor
	 */
	private GammaHantoGame(HantoGameID gameID, HantoPlayerColor firstPlayerColor) {
		super(gameID, firstPlayerColor);
	}
	
	/**
	 * Constructs a GammaHantoGame
	 * 
	 * @param firstPlayerColor the color of the first player to move
	 */
	public GammaHantoGame(HantoPlayerColor firstPlayerColor)
	{
		this(HantoGameID.GAMMA_HANTO, firstPlayerColor);
	}

	/* (non-Javadoc)
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException {
		checkMakeMoveInputForException(pieceType, from, to);
		
		checkButterflyIsPlacedByTurn(turnsToButterfly, pieceType);
		
		movePiece(pieceType, from, to);

		TurnNumber++;
		
		MoveResult mr = checkForWinner();
		if(mr == MoveResult.OK)
		{
			mr = drawGameOnTurn(turnsToDraw);
		}
		return mr;
	}

	/* (non-Javadoc)
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
