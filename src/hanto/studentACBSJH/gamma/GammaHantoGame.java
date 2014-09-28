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

/**
 * @author Sean
 *
 */
public class GammaHantoGame extends BaseHantoGame implements HantoGame {

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
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see hanto.studentACBSJH.common.BaseHantoGame#setupHands()
	 */
	@Override
	protected void setupHands() {
		// TODO Auto-generated method stub

	}

}
