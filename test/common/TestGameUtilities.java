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
	 * 
	 * @param HantoPiecesReference
	 * @param secondPlayer
	 * 
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
			boolean found = false;
			for(int i = 0; i < HantoPieces.size() && !found; i++)
			{
				HantoPieceACBSJH hp = (HantoPieceACBSJH) HantoPieces.toArray()[i];
				if(hp.isInHand() && hp.getType() == pair.pieceType && hp.getType() == pair.pieceType && hp.getColor() == pair.player)
				{
					hp.setLocation(new HantoCoordinateACBSJH(pair.location));
					found = true;
				}
			}
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
	
	public void clearPieces()
	{
		HantoPieces.clear();
	}
	
	public void addPiecesToHand(HantoPieceType[] newPieces, HantoPlayerColor player)
	{
		for(HantoPieceType hpt : newPieces)
		{
			HantoPieces.add(new HantoPieceACBSJH(player, hpt));
		}
	}

}
