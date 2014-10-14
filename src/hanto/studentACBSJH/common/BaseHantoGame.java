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
import hanto.common.HantoGame;
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
public abstract class BaseHantoGame implements HantoGame{

	/**
	 * All of the HantoPieces used in this HantoGame,
	 * including those in the hand.
	 */
	protected Collection<HantoPieceACBSJH> HantoPieces;
	
	/**
	 * A bool for if the game is over.
	 */
	protected boolean isGameOver = false;
	
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
	 * @return the collection of all the hanto pieces in the hands and on the board
	 */
	public Collection<HantoPieceACBSJH> getAllHantoPieces()
	{
		return HantoPieces;
	}
	
	/**
	 * @param newHantoPieces sets the collection of all hanto pieces in this game to a new collection
	 */
	public void setAllHantoPieces(Collection<HantoPieceACBSJH> newHantoPieces)
	{
		HantoPieces = newHantoPieces;
	}
	
	/**
	 * @return the internal turn number for this game that increments on every single move
	 */
	public int getInternalTurnNumber()
	{
		return TurnNumber;
	}
	
	/**
	 * @param newTurnNumber the new internal turn number that incriments on every single move
	 */
	public void setInternalTurnNumber(int newTurnNumber)
	{
		TurnNumber = newTurnNumber;
	}
	
	/**
	 * Gets the color of the first player
	 * 
	 * @return the color of the first player
	 */
	public HantoPlayerColor getFirstPlayer()
	{
		return firstPlayer;
	}
	
	/**
	 * @return the ID of this hanto game
	 */
	public HantoGameID getHantoGameID()
	{
		return iD;
	}
	
	/**
	 * moves a piece of pieceType from one location to another, and makes sure to
	 * save the butterfly with saveButterfly, also assumes that input validation
	 * has already done
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
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
	public HantoPlayerColor getCurrentPlayersTurn()
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
		checkForInvalidPieceTypeException(pieceType);
		checkForNoPieceToMoveException(pieceType, from, to);
		checkForMovingWrongColorPieceException(from);
		checkFirstMoveIsToOriginException(to);
		checkForOccupiedMoveDestinatationException(to);
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
	 * @param pieceType
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
						" piece when it is " + getCurrentPlayersTurn() + " turn");
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
	 * Counts the number of pieces surrounding a butterfly of color
	 * 
	 * @param color the color of the butterfly to check
	 * @return the number of pieces surrounding the butterfly
	 */
	public int countPiecesSurroundingButterfly(HantoPlayerColor color) {
		HantoPieceACBSJH blueB = getButterflyOnBoard(HantoPlayerColor.BLUE);
		HantoPieceACBSJH redB = getButterflyOnBoard(HantoPlayerColor.RED);
		
		if(HantoPlayerColor.BLUE == color)
		{
			if(blueB != null)
			{
				return getListOfNeighbors(blueB).size();
			}
			else
			{
				return 0;
			}
		}
		else
		{
			if(redB != null)
			{
				return getListOfNeighbors(redB).size();
			}
			else
			{
				return 0;
			}
		}
	}
	
	/**
	 * If the user if placing the butterfly this turn, save a reference to it.
	 * 
	 * @param piece piece to check for being the butterfly
	 */
	public void saveButterfly(HantoPieceACBSJH piece)
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
		HantoPieceACBSJH butterflyToCheck;
		if(color == HantoPlayerColor.RED)
		{
			butterflyToCheck = redButterfly;
		}
		else
		{
			butterflyToCheck = blueButterfly;
		}
		
		if(butterflyToCheck != null)
		{
			return true;
		}
		else
		{
			for(HantoPieceACBSJH hp : HantoPieces)
			{
				if(!hp.isInHand() && hp.getType() == HantoPieceType.BUTTERFLY && hp.getColor() == color)
				{
					butterflyToCheck = hp;
					return true;
				}
			}
			return false;
		}
		
	}
	
	/**
	 * Returns the butterfly of color on the board. Returns null if no butterfly of color
	 * yet placed on the board.
	 * 
	 * @param color The color of the butterfly to look for
	 * @return the butterfly of color on the board, or null if its not on the board yet
	 */
	public HantoPieceACBSJH getButterflyOnBoard(HantoPlayerColor color)
	{
		if(color == HantoPlayerColor.RED)
		{
			if(redButterfly != null)
			{
				return redButterfly;
			}
		}
		else
		{
			if(blueButterfly != null)
			{
				return blueButterfly;
			}
		}
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if(hp.getType() == HantoPieceType.BUTTERFLY && hp.getColor() == color && !hp.isInHand())
			{
				if(color == HantoPlayerColor.RED)
				{
					redButterfly = hp;
				}
				else
				{
					blueButterfly = hp;
				}
				return hp;
			}
		}
		return null;
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
			isGameOver = true;
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
			isGameOver = true;
		}
		else if (countPiecesSurroundingButterfly(HantoPlayerColor.BLUE) == 6) {
			mr = MoveResult.RED_WINS;
			isGameOver = true;
		}
		return mr;
	}
	
	/**
	 * Checks that all the pieces on the board are in one continuous block.
	 * @throws HantoException
	 */
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
		int numVisited = visited.size();
		if (visited.size() != numOfPicesOnBoard) {
			throw new HantoException("All pieces must be contiguous. Could only reach " + numVisited + " out of " + numOfPicesOnBoard);
		}
	}
	
	/**
	 * A function that gets the number of pieces on the board.
	 * @return The number of pieces on the board. 
	 */
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
	
	/**
	 * Takes in a piece and gets all pieces adjacent to it.
	 * @param startPiece
	 * @return a list of neighbors.
	 */
	protected List<HantoPieceACBSJH> getListOfNeighbors(HantoPieceACBSJH startPiece) {
		return getListOfNeighbors(startPiece.getLocation());
	}
	
	/**
	 * Takes in a location and gets all pieces adjacent to it.
	 * @param location
	 * @return a list of neighbor pieces
	 */
	protected List<HantoPieceACBSJH> getListOfNeighbors(HantoCoordinateACBSJH location) {
		List<HantoPieceACBSJH> neighbors = new LinkedList<HantoPieceACBSJH>();
		
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if (!hp.isInHand()) {
				if (hp.getLocation().isAdjacent(location)) {
					neighbors.add(hp);
				}
			}
		}
		
		return neighbors;
	}
	
	/**
	 * Throws an exception if a player tries to move after the game has ended.
	 * @throws HantoException
	 */
	protected void preventMovesAfterGameOver() throws HantoException 
	{
	if (isGameOver) {
		throw new HantoException("The game is over.");
		}
	}
	
	/**
	 *Sets the game to be over. 
	 */
	public void setGameOver() {
		isGameOver = true;
	}
		
	/**
	 * Checks if a piece of a color is adjacent to a given location
	 * 
	 * @param location
	 * @param color
	 * @return true if a piece of color is adjacent to location
	 */
	protected boolean isAdjacentToColor(HantoCoordinateACBSJH location, HantoPlayerColor color)
	{
		List<HantoPieceACBSJH> neighbors = getListOfNeighbors(location);
		for(HantoPieceACBSJH hp : neighbors)
		{
			if(hp.getColor() == color)
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks that a piece placed from the hand is next to a friendly piece and not next to an enemy piece
	 * 
	 * @param from 
	 * @param to
	 * @throws HantoException
	 */
	protected void checkMoveForBadNeighbors(HantoCoordinate from, HantoCoordinate to) throws HantoException
	{
		if(TurnNumber < 2 || from != null)
		{
			return;
		}
		
		HantoPlayerColor currentColor = getCurrentPlayersTurn();
		HantoPlayerColor oppositeColor = (currentColor == HantoPlayerColor.BLUE) ? HantoPlayerColor.RED : HantoPlayerColor.BLUE;
		if(!(isAdjacentToColor(new HantoCoordinateACBSJH(to), currentColor) && !isAdjacentToColor(new HantoCoordinateACBSJH(to), oppositeColor)))
		{
			throw new HantoException("Cannot place piece there, bad neighbors.");
		}
	}
}
