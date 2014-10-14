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

import hanto.common.HantoPlayerColor;

import common.HantoTestGame;
import common.TestGameUtilities;

/**
 * The same as a GammaHantoGame, but with the ability to set the turn
 * number 
 * 
 * @author Sean
 *
 */
public class GammaHantoTestGame extends GammaHantoGame implements HantoTestGame {

	/**
	 * A class with functions for implementing the HantoTestGameInterface
	 */
	private TestGameUtilities testGameBoardUtilities;
	
	/**
	 * @param firstPlayerColor
	 */
	public GammaHantoTestGame(HantoPlayerColor firstPlayerColor) {
		super(firstPlayerColor);
		testGameBoardUtilities = new TestGameUtilities(HantoPieces, secondPlayer);
		
	}

	/** (non-Javadoc)
	 * @see common.HantoTestGame#initializeBoard(common.HantoTestGame.PieceLocationPair[])
	 */
	@Override
	public void initializeBoard(PieceLocationPair[] initialPieces) {
		testGameBoardUtilities.initializeBoard(initialPieces);
	}

	/** (non-Javadoc)
	 * @see common.HantoTestGame#setTurnNumber(int)
	 */
	@Override
	public void setTurnNumber(int turnNumber) {
		TurnNumber = testGameBoardUtilities.setTurnNumber(turnNumber);
	}
	
	/** (non-Javadoc)
	 * @see common.HantoTestGame#setPlayerMoving(hanto.common.HantoPlayerColor)
	 */
	@Override
	public void setPlayerMoving(HantoPlayerColor player) {
		TurnNumber = testGameBoardUtilities.setPlayerMoving(TurnNumber, player);
	}

}
