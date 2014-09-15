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
import hanto.common.HantoGameID;
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
	
	protected Collection<HantoPieceType> ValidHantoPieceTypes;
	
	protected HantoGameID iD;
	
	protected HantoPlayerColor firstPlayer, secondPlayer;
	
	/**
	 * The number of the turn being played. This variable must
	 * be incremented by MakeMove.
	 */
	protected int TurnNumber; 
	
	protected HantoPieceACBSJH redButterfly = null;
	protected HantoPieceACBSJH blueButterfly = null;
	
	/**
	 * Constructs things in common between all Hanto games.
	 */
	/**
	 * @param gameID
	 * @param firstPlayerColor
	 */
	protected BaseHantoGame(HantoGameID gameID, HantoPlayerColor firstPlayerColor)
	{
		iD = gameID;
		firstPlayer = firstPlayerColor;
		if(firstPlayer.equals(HantoPlayerColor.BLUE))
		{
			secondPlayer = HantoPlayerColor.RED;
		}
		else
		{
			secondPlayer = HantoPlayerColor.BLUE;
		}
		HantoPieces = new ArrayList<HantoPieceACBSJH>();
		ValidHantoPieceTypes = new ArrayList<HantoPieceType>();
		TurnNumber = 0;
		setupHands();
		addValidHantoPieceTypes();
	}
	
	/**
	 * Gets the color of the player whose turn it is to move
	 * 
	 * @return the color of the player who should move next
	 */
	protected HantoPlayerColor getCurrentPlayersTurn()
	{
		return (TurnNumber % 2 == 0) ? firstPlayer : secondPlayer;
	}
	
	/**
	 *  Constructs HantoPieces for the game with null locations signifying that
	 *  they are in the hand.
	 */
	protected abstract void setupHands();

	/**
	 * 
	 */
	protected void addValidHantoPieceTypes()
	{
		ValidHantoPieceTypes.add(HantoPieceType.BUTTERFLY);
	}
	

	/** Moves a hanto piece from the hand to the board.
	 * @param pieceType
	 * @return a hantoPieceACBSJH or null.
	 */
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
	
	/** Returns whatever piece is at a given location.
	 * @param where
	 * @return either a piece or null.
	 */
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
	

	/** Part of the checks used to determine if a move was valid or not.
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void checkMakeMoveInputForException(HantoPieceType pieceType, HantoCoordinate from,
			HantoCoordinate to) throws HantoException
	{
		checkToCoordinateIsValid(to);
		checkForNoPieceToMoveException(from, to);
		checkForMovingWrongColorPieceException(from);
		checkFirstMoveIsToOriginException(to);
		checkForInvalidPieceTypeException(pieceType);
		checkForOccupiedMoveDestinatationException(to);
	}
	
	/**
	 * @param to
	 * @throws HantoException
	 */
	protected void checkForOccupiedMoveDestinatationException(HantoCoordinate to) throws HantoException
	{
		if(getPieceAt(to) != null)
		{
			throw new HantoException("Cannot move piece to "+(new HantoCoordinateACBSJH(to)).toString()
					+" because the space is already occupied.");
		}
	}
	
	/**
	 * Used for validating input on makeMove in subclasses.
	 * Throws an exception if there is no piece to move
	 * at the 'from' location.
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void checkForNoPieceToMoveException(HantoCoordinate from, HantoCoordinate to) throws HantoException
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
	
	/** Ensures that only allowable pieces are played in any hanto game.
	 * @param pieceType
	 * @throws HantoException
	 */
	protected void checkForInvalidPieceTypeException(HantoPieceType pieceType) throws HantoException
	{
		if(!ValidHantoPieceTypes.contains(pieceType))
		{
			throw new HantoException("Piece type " + pieceType.toString() + " not allowed in " + iD.toString() + " Hanto Game");
		}
	}
	
	/** Counts how many pieces surround a butterfly of the given color.
	 * @param color
	 * @return count if the butterfly is placed, zero otherwise.
	 */
	public int countSurroundingPieces(HantoPlayerColor color) {
		if (color == HantoPlayerColor.BLUE && blueButterfly != null) {
			int count = 0;
			HantoCoordinate butterfly = blueButterfly.getLocation();
			for(HantoPieceACBSJH hp : HantoPieces){
				if (!hp.isInHand() && hp.getLocation().isAdjacent(butterfly)) {
					count++;
				}
			}
			return count;
		} if (color == HantoPlayerColor.RED && redButterfly != null) {
			int count = 0;
			HantoCoordinate butterfly = redButterfly.getLocation();
			for(HantoPieceACBSJH hp : HantoPieces){
				if (!hp.isInHand() && hp.getLocation().isAdjacent(butterfly)) {
					count++;
				}
			}
			return count;
		}else {
		return 0;
		}
	}
	
}
