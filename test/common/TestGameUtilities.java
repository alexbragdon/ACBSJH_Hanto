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
package common;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.common.BaseHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

import java.util.Collection;

import common.HantoTestGame.PieceLocationPair;

/**
 * Utilities for TestHantoGames to implement the HantoTestGame interface 
 * 
 * @author Sean
 *
 */
public class TestGameUtilities {

	/**
	 * The reference to the HantoPieces in the HantoTestGame
	 */
	private Collection<HantoPieceACBSJH> HantoPieces;
	
	/**
	 * The player who is currently moving in the HantoTestGame
	 */
	private HantoPlayerColor PlayerMoving = null;
	
	/**
	 * The second player who moves in the HantoTestGame
	 */
	private HantoPlayerColor SecondPlayer;
	
	/**
	 * Constructs this class with all the data it needs from the HantoTestGame
	 */
	public TestGameUtilities(Collection<HantoPieceACBSJH> HantoPiecesReference, HantoPlayerColor secondPlayer)
	{
		HantoPieces = HantoPiecesReference;
		SecondPlayer = secondPlayer;
	}
	
	/**
	 * Adds pieces at specified locations to the list of pieces this class has from a HantoTestGame
	 * 
	 * @param initialPieces
	 */
	public void initializeBoard(PieceLocationPair[] initialPieces)
	{
		for(PieceLocationPair pair : initialPieces)
		{
			HantoPieceACBSJH hp = new HantoPieceACBSJH(pair.player, pair.pieceType);
			hp.setLocation(new HantoCoordinateACBSJH(pair.location));
			HantoPieces.add(hp);
		}
	}
	
	/**
	 * Takes a turn number that increments after every pair of moves and returns
	 * a turn number that increments on every single player move.
	 * 
	 * @param turnNumber turn number that increments after every pair of moves
	 * @return turn number that increments on every single player move
	 */
	public int setTurnNumber(int turnNumber) {
		turnNumber = turnNumber * 2 - 2;
		if(PlayerMoving != null)
		{
			turnNumber = setPlayerMoving(turnNumber, PlayerMoving);
		}
		return turnNumber;
	}
	
	/**
	 * sets the Player Moving by adjusting the turn number to be odd or even
	 * Appropriately for the internal turn numbering system in BaseHantoGame
	 * 
	 * @param turnNumber turn number that increments after every pair of moves
	 * @param player color of player who is moving now
	 * @return the new turn number that increments after every pair of moves
	 */
	public int setPlayerMoving(int turnNumber, HantoPlayerColor player) {
		if(PlayerMoving == null)
		{
			PlayerMoving = player;
		}
		if(player == SecondPlayer && turnNumber % 2 != 1)
		{
			turnNumber++;
		}
		else if(PlayerMoving != player)
		{
			PlayerMoving = player;
			turnNumber--;
		}
		return turnNumber;
	}

}
