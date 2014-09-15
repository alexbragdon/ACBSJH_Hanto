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

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author alexbragdon
 *
 */
public abstract class BaseHantoGame {

	/**
	 * All of the HantoPieces used in this HantoGame,
	 * including those in the hand.
	 */
	protected Collection<HantoPieceACBSJH> HantoPieces;
	
	/**
	 * The number of the turn being played. This variable must
	 * be incremented by MakeMove.
	 */
	protected int TurnNumber; 
	
	protected HantoPiece redButterfly = null;
	protected HantoPiece blueButterfly = null;
	
	/**
	 * Constructs things in common between all Hanto games.
	 */
	protected BaseHantoGame()
	{
		HantoPieces = new ArrayList<HantoPieceACBSJH>();
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

	protected HantoPieceACBSJH getPieceFromHand(HantoPieceType pieceType)
	{
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if(hp.isInHand() && hp.getType().equals(pieceType)
					&& hp.getColor().equals(getCurrentPlayersTurn()))
			{
				return hp;
			}
		}
		return null;
	}
	
	public HantoPiece getPieceAt(HantoCoordinate where) {
		for(HantoPieceACBSJH piece : HantoPieces)
		{
			if(!piece.isInHand() && piece.getLocation().equals(where))
			{
				return piece;
			}
		}
		return null;
	}
	
	/**
	 * Makes a printable board, a list of all the pieces on 
	 * the board as defined by their type and location
	 * 
	 * @return a string list of all the pieces on the board
	 */
	public String getPrintableBoard()
	{
		String board = "-=AlphaHantoGame=-\n";
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if(!hp.isInHand())
			{
				board += hp.toString() + '\n';
			}
		}
		board += "-end-";
		return board; 
	}
	
	/**
	 * Used for validating input on makeMove in subclasses.
	 * Throws an exception if there is no piece to move
	 * at the from location.
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void checkForNoPieceOnBoardToMoveException(HantoCoordinate from, HantoCoordinate to) throws HantoException
	{
		if(from != null && getPieceAt(from) == null)
		{
			throw new HantoException("There is no piece to move at "+(new HantoCoordinateACBSJH(to)).toString());
		}
	}
	
	/**
	 * Used for validating input on makeMove in subclasses.
	 * Throws an exception if the wrong player is moving a piece
	 * on the board. 
	 * 
	 * @param from
	 * @throws HantoException
	 */
	protected void checkForMovingWrongColorPieceException(HantoCoordinate from) throws HantoException
	{
		if(from != null)
		{
			HantoPiece HPToMove = getPieceAt(from);
			if(!HPToMove.getColor().equals(getCurrentPlayersTurn()))
			{
				throw new HantoException("Cannot move "+HPToMove.getColor().toString() +
						"piece when it is " + getCurrentPlayersTurn() + " turn");
			}
		}
	}
	
	/**
	 * Used for validating input on makeMove in subclasses.
	 * Throws an exception if the first move is not on (0,0)  
	 * 
	 * @param to
	 * @throws HantoException
	 */
	protected void checkFirstMoveIsToOriginException(HantoCoordinate to) throws HantoException
	{
		HantoCoordinateACBSJH origin = new HantoCoordinateACBSJH(0, 0);
		if(TurnNumber == 0 && !origin.equals(to))
		{
			throw new HantoException("Expected first move to be (0, 0), got " +
									 (new HantoCoordinateACBSJH(to)).toString());
		}	
	}
	
	/**
	 * Used for validating input on makeMove in subclasses.
	 * Throws an exception if to coordinate is null 
	 * 
	 * @param to coordinate MakeMove
	 * @throws HantoException
	 */
	protected void checkToCoordinateIsValid(HantoCoordinate to) throws HantoException
	{
		//if to is not a valid coordinate
		if(to == null)
		{
			throw new HantoException("Cannot move piece to a null location.");
		}
	}
	
}
