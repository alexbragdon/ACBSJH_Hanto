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
package hanto.studentACBSJH.common;

import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseHantoGame {

	/**
	 * All of the HantoPieces used in this HantoGame,
	 * including those in the hand.
	 */
	protected Collection<HantoPiece> HantoPieces;
	
	/**
	 * The number of the turn being played. This variable must
	 * be incremented by MakeMove.
	 */
	protected int TurnNumber; 
	
	/**
	 * Constructs things in common between all Hanto games.
	 */
	protected BaseHantoGame()
	{
		HantoPieces = new ArrayList<HantoPiece>();
		TurnNumber = 0;
		setupHands();
	}
	
	/**
	 * Gets the color of the player whose turn it is to move
	 * 
	 * @return the color of the player who should move next
	 */
	protected HantoPlayerColor getCurrentPlayersTurn()
	{
		return (TurnNumber % 2 == 0) ? HantoPlayerColor.BLUE : HantoPlayerColor.RED;
	}
	
	/**
	 *  Constructs HantoPieces for the game with null locations signifying that
	 *  they are in the hand.
	 */
	protected abstract void setupHands();
	
}
