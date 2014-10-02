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
import hanto.common.MoveResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;



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
	 * A list of the types of Hanto pieces used in this game.
	 */
	protected Collection<HantoPieceType> ValidHantoPieceTypes;
	
	/**
	 * The type of Hanto Game this is.
	 */
	protected HantoGameID iD;
	
	/**
	 * The color of each player.
	 */
	protected HantoPlayerColor firstPlayer, secondPlayer;
	
	/**
	 * The number of the turn being played. This variable must
	 * be incremented by MakeMove.
	 */
	protected int TurnNumber; 
	
	/**
	 * The Hanto Piece that is the red player's butterfly. This
	 * is set by makeMove as soon as it see's it.
	 */
	protected HantoPieceACBSJH redButterfly = null;
	
	/**
	 * The Hanto Piece that is the blue player's butterfly. This
	 * is set by makeMove as soon as it see's it.
	 */
	protected HantoPieceACBSJH blueButterfly = null;
	
	/**
	 * Constructs things in common between all Hanto games.
	 * 
	 * @param gameID the type of hanto game this is
	 * @param firstPlayerColor color of the first player to move
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
	 * moves a piece of pieceType from one location to another, and makes sure to
	 * save the butterfly with saveButterfly, also assumes that input validation
	 * has already done
	 * 
	 * @param pieceType  type of piece to move
	 * @param to location to move piece too
	 */
	protected void movePiece(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to)
	{
		HantoPieceACBSJH pieceToMove;
		if(from == null)
		{
			pieceToMove = getPieceFromHand(pieceType);
		}
		else
		{
			pieceToMove = (HantoPieceACBSJH) getPieceAt(from);
		}
		pieceToMove.setLocation(new HantoCoordinateACBSJH(to));
		saveButterfly(pieceToMove);
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
	 *  Creates the list of valid Hanto pieces for this game. Sub classes
	 *  of this class should override this method and add their own valid pieces.
	 *  In this method we assume every Hanto game will at least have a butterfly in it.
	 */
	protected void addValidHantoPieceTypes()
	{
		ValidHantoPieceTypes.add(HantoPieceType.BUTTERFLY);
	}
	

	/**
	 * Searches for a Hanto piece of a certain type that is in the hand.
	 * 
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
		checkForNoPieceToMoveException(pieceType, from, to);
		checkForMovingWrongColorPieceException(from);
		checkFirstMoveIsToOriginException(to);
		checkForInvalidPieceTypeException(pieceType);
		checkForOccupiedMoveDestinatationException(to);
		checkForPieceNotInHandException(pieceType, from);
		checkForNoAdjacentPieceToDestination(to);
	}
	
	/**
	 * Throws an exception if there is no HantoPiece adjacent to the given coordinate.
	 * 
	 * @param to 	the coordinate to check adjacency to
	 * @throws HantoException
	 */
	private void checkForNoAdjacentPieceToDestination(HantoCoordinate to) throws HantoException
	{
		if(TurnNumber != 0)
		{
			boolean foundAdjacentPiece = false;
			for(HantoPieceACBSJH hp : HantoPieces)
			{
				if(!hp.isInHand() && hp.getLocation().isAdjacent(to))
				{
					foundAdjacentPiece = true;
				}
			}
			if(!foundAdjacentPiece)
			{
				throw new HantoException("The destination "+(new HantoCoordinateACBSJH(to)).toString() 
						+ " is not adjacent to any piece and therefor not a valid destination"); 
			}
		}
	}

	
	/**
	 * Throws an exception if the location given is occupied so that a piece cannot move there.
	 * 
	 * @param to the location to check
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
	protected void checkForNoPieceToMoveException(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException
	{
		if(from != null)
		{
			if(getPieceAt(from) == null)
			{
				throw new HantoException("There is no piece to move at "+(new HantoCoordinateACBSJH(to)).toString());
			}
			if(getPieceAt(from).getType() != pieceType)
			{
				throw new HantoException("There is no piece of type "+pieceType.toString()+"to move at "+(new HantoCoordinateACBSJH(to)).toString());
			}
		}
		if(from == null && getPieceFromHand(pieceType) == null)
		{
			throw new HantoException("There is no piece "+pieceType.toString()+" in the hand.");
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
	
	/**
	 * Ensures that only allowable pieces are played in any Hanto game.
	 * 
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
	
	/**
	 * Throws an exception if the piece trying to be moves from the hand, is not in the hand.
	 * 
	 * @param pieceType
	 * @param from
	 * @throws HantoException
	 */
	protected void checkForPieceNotInHandException(HantoPieceType pieceType,
			HantoCoordinate from) throws HantoException
	{
		if(from == null)
		{
			boolean pieceFound = false;
			for(HantoPieceACBSJH hp : HantoPieces)
			{
				if(hp.isInHand() && hp.getType().equals(pieceType))
				{
					pieceFound = true;
				}
			}
			if(!pieceFound)
			{
				throw new HantoException("A piece of type "+pieceType.toString()
						+" is not in your hand to move onto the baord");
			}
		}
	}
	
	
	/**
	 * Counts how many pieces surround a butterfly of the given color.
	 * 
	 * @param color
	 * @return count if the butterfly is placed, zero otherwise.
	 */
	public int countPiecesSurroundingButterfly(HantoPlayerColor color) {
		if (color == HantoPlayerColor.BLUE && blueButterfly != null) {
			int count = 0;
			HantoCoordinate butterfly = blueButterfly.getLocation();
			for(HantoPieceACBSJH hp : HantoPieces){
				if (!hp.isInHand() && hp.getLocation().isAdjacent(butterfly)) {
					count++;
				}
			}
			return count;
		} else if (color == HantoPlayerColor.RED && redButterfly != null) {
			int count = 0;
			HantoCoordinate butterfly = redButterfly.getLocation();
			for(HantoPieceACBSJH hp : HantoPieces){
				if (!hp.isInHand() && hp.getLocation().isAdjacent(butterfly)) {
					count++;
				}
			}
			return count;
		} else {
		return 0;
		}
	}
	
	/**
	 * If the user if placing the butterfly this turn, save a reference to it.
	 * 
	 * @param piece piece to check for being the butterfly
	 */
	protected void saveButterfly(HantoPieceACBSJH piece)
	{
		//Saves the the butterfly's location to prevent having to search for it in the future. 
		if ((piece.getType() == HantoPieceType.BUTTERFLY) && (getCurrentPlayersTurn() == HantoPlayerColor.BLUE)) {
			blueButterfly = piece;
		}
		else if ((piece.getType() == HantoPieceType.BUTTERFLY) && (getCurrentPlayersTurn() == HantoPlayerColor.RED)) {
			redButterfly = piece;
		}
	}
	
	/**
	 * Checks if the butterfly has been placed yet
	 * 
	 * @param color the color of the butterfly to check
	 * @return if the butterfly of color color has been placed
	 */
	protected boolean isButterflyPlaced(HantoPlayerColor color)
	{
		if(color == HantoPlayerColor.RED)
		{
			return redButterfly != null;
		}
		else
		{
			return blueButterfly != null;
		}
	}
	
	/**
	 * Throws an exception if the butterfly of the current player who's turn it is has not placed their
	 * butterfly by a given turn.
	 * 
	 * @param turn the turn a player must place their butterfly by
	 * @param pieceType the piece the current player is trying to place
	 * @throws HantoException
	 */
	protected void checkButterflyIsPlacedByTurn(int turn, HantoPieceType pieceType) throws HantoException
	{
		HantoPlayerColor currentPlayerColor = getCurrentPlayersTurn(); 
		if ((TurnNumber > turn) && (currentPlayerColor == HantoPlayerColor.BLUE) && !isButterflyPlaced(currentPlayerColor) 
				&& (pieceType != HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Must place butterfly on or before turn " + turn);
		}
		if ((TurnNumber > turn) && (currentPlayerColor == HantoPlayerColor.RED) && !isButterflyPlaced(currentPlayerColor) 
				&& (pieceType != HantoPieceType.BUTTERFLY)) {
			throw new HantoException("Must place butterfly on or before turn " + turn);
		}
	}
	
	/**
	 * Checks if the game is a draw based on the game drawing on a given turn number
	 * 
	 * @param drawTurn the turn number that the game ends in a draw on
	 * @return OK if the game continues, DRAW if the game is a draw
	 */
	protected MoveResult drawGameOnTurn(int drawTurn)
	{
		MoveResult mr = MoveResult.OK;
		if(TurnNumber >= drawTurn)
		{
			mr = MoveResult.DRAW;
		}
		return mr;
	}
	
	/**
	 * Checks if either player has their butterfly surrounded
	 * @return if either the red or blue player wins, or OK if niether does
	 */
	protected MoveResult checkForWinner()
	{
		MoveResult mr = MoveResult.OK;
		if (countPiecesSurroundingButterfly(HantoPlayerColor.RED) == 6) {
			mr = MoveResult.BLUE_WINS;
		}
		else if (countPiecesSurroundingButterfly(HantoPlayerColor.BLUE) == 6) {
			mr = MoveResult.RED_WINS;
		}
		return mr;
	}
	
	protected void checkForContinuity() throws HantoException
	{	
		HantoPieceACBSJH startPiece = null;
		
		List<HantoPieceACBSJH> visited = new LinkedList<HantoPieceACBSJH>();
		List<HantoPieceACBSJH> toVisit = new LinkedList<HantoPieceACBSJH>();
		List<HantoPieceACBSJH> tempToVisit = new LinkedList<HantoPieceACBSJH>();
		
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if (!hp.isInHand()) {
				startPiece = hp;
			}
		}
		
		toVisit.addAll(getListOfNeighbors(startPiece));
		visited.add(startPiece);
		
		while (toVisit.size() != 0) {
			tempToVisit.addAll(toVisit);
			for (HantoPieceACBSJH visiting : tempToVisit) {
				for (HantoPieceACBSJH neighborToVisiting : getListOfNeighbors(visiting)) {
					if (!visited.contains(neighborToVisiting)){
						toVisit.add(neighborToVisiting);
					}
				}
				if(!visited.contains(visiting))
				{
					visited.add(visiting);
				}
				toVisit.remove(visiting);
			}
			tempToVisit.clear();
		}
		int numOfPicesOnBoard = getNumPiecesOnBoard();
		if (visited.size() != numOfPicesOnBoard) {
			throw new HantoException("All pieces must be contiguous. Could only reach " + visited.size() + " out of " + numOfPicesOnBoard);
		}
	}
	
	protected int getNumPiecesOnBoard() 
	{
		int count = 0;
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if (!hp.isInHand()) {
				count++;
			}
		}
		return count;
	}
	
	protected List<HantoPieceACBSJH> getListOfNeighbors(HantoPieceACBSJH startPiece) {
		List<HantoPieceACBSJH> neighbors = new LinkedList<HantoPieceACBSJH>();
		
		if (startPiece != null){
			for(HantoPieceACBSJH hp : HantoPieces)
			{
				if (!hp.isInHand()) {
					if (hp.getLocation().isAdjacent(startPiece.getLocation())) {
						neighbors.add(hp);
					}
				}
			}
		}
		
		return neighbors;
	}
}
