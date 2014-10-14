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
package hanto.studentACBSJH.epsilon;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studentACBSJH.HantoGameFactory;
import hanto.studentACBSJH.common.BaseHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;
import hanto.tournament.HantoMoveRecord;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author alexbragdon
 *
 */
public class EpsilonHantoGame extends BaseHantoGame implements HantoGame {

	/**
	 * The number of moves that can be made before the butterfly must be placed
	 */
	private final int turnsToButterfly = 5;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int sparrowsPerPlayer = 2;
	
	/**
	 * The number of sparrow's in each player's hand.
	 */
	private final int crabsPerPlayer = 6;
	
	/**
	 * The number of horses in each player's hand.
	 */
	private final int horsesPerPlayer = 4;
	
	/**
	 * @param gameID
	 * @param firstPlayerColor
	 */
	private EpsilonHantoGame(HantoGameID gameID, HantoPlayerColor firstPlayerColor) {
		super(gameID, firstPlayerColor);
	}
	
	/**
	 * Constructs a GammaHantoGame
	 * 
	 * @param firstPlayerColor the color of the first player to move
	 */
	public EpsilonHantoGame(HantoPlayerColor firstPlayerColor)
	{
		this(HantoGameID.EPSILON_HANTO, firstPlayerColor);
	}

	/** (non-Javadoc)
	 * @throws HantoPrematureResignationException 
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException {
		preventMovesAfterGameOver();
		
		
		MoveResult resignCheckResult = playerResignCheck(pieceType, from, to);
		if(resignCheckResult != MoveResult.OK)
		{
			return  resignCheckResult;
		}
		
		checkMakeMoveInputForException(pieceType, from, to);
		checkButterflyIsPlacedByTurn(turnsToButterfly, pieceType);
		
		movePiece(pieceType, from, to);
		checkForContinuity();
		
		checkMoveForBadNeighbors(from, to);
		checkPlayerMovesTheCorrectNumberOfHexes(pieceType, from, to);
		
		TurnNumber++;
		
		MoveResult mr = checkForWinner();
		
		return mr;
	}
	
	/**
	 * Handles the case where one player tries to resign
	 * 
	 * @param pieceType must be null to resign
	 * @param from must be null to resign
	 * @param to must be null to resign
	 * @return OK if no player won as a result of resignation, otherwise RED_WINS or BLUE_WINS returned accordingly
	 * @throws HantoException thrown if one player resigned while still having a possible move 
	 */
	private MoveResult playerResignCheck(HantoPieceType pieceType, HantoCoordinate from, HantoCoordinate to) throws HantoException
	{
		MoveResult mr = MoveResult.OK;
		if(pieceType == null && from == null && to == null)
		{
			Collection<HantoMoveRecord> possibleMoves = getAllPossibleMovesForCurrentPlayer();
			if (possibleMoves.isEmpty())
			{
				if (getCurrentPlayersTurn() == HantoPlayerColor.BLUE) {
					mr = MoveResult.RED_WINS; 
				} else {
					mr = MoveResult.BLUE_WINS;
				}
				setGameOver();
			} 
			else
			{
				throw new HantoPrematureResignationException();
			}
		}
		return mr;
	}

	/** (non-Javadoc)
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
		
		for (int i=0; i<crabsPerPlayer; i++) {
			HantoPieceACBSJH blueCrab = new HantoPieceACBSJH(HantoPlayerColor.BLUE, HantoPieceType.CRAB);
			HantoPieceACBSJH redCrab = new HantoPieceACBSJH(HantoPlayerColor.RED, HantoPieceType.CRAB);
			HantoPieces.add(blueCrab);
			HantoPieces.add(redCrab);
		}
		
		for (int i=0; i<horsesPerPlayer; i++) {
			HantoPieceACBSJH blueHorse = new HantoPieceACBSJH(HantoPlayerColor.BLUE, HantoPieceType.HORSE);
			HantoPieceACBSJH redHorse = new HantoPieceACBSJH(HantoPlayerColor.RED, HantoPieceType.HORSE);
			HantoPieces.add(blueHorse);
			HantoPieces.add(redHorse);
		}
	}
	
	/**
	 * 
	 * Prevents either the butterfly or the crab from walking more than one hex, the sparrow from flying more than four hexes,
	 * and the horse from jumping in a non straight direction.
	 * 
	 * @param pieceType
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	protected void checkPlayerMovesTheCorrectNumberOfHexes(HantoPieceType pieceType, HantoCoordinate from, 
			HantoCoordinate to) throws HantoException {
		if(from != null){
			HantoCoordinateACBSJH ACBSJHTo = new HantoCoordinateACBSJH(to);
			HantoCoordinateACBSJH ACBSJHFrom = new HantoCoordinateACBSJH(from);
			
			if ((pieceType == HantoPieceType.BUTTERFLY || pieceType == HantoPieceType.CRAB)) {		
				if (!ACBSJHTo.isAdjacent(ACBSJHFrom)) {
					throw new HantoException(pieceType.toString() + " cannot move more than one space.");
				}
				checkForSlidingException(ACBSJHFrom, ACBSJHTo);
			} 
			else if (pieceType == HantoPieceType.HORSE)
			{
				if (ACBSJHFrom.isStraightLine(ACBSJHTo)) {
					checkForGapInHorsePath(ACBSJHFrom, ACBSJHTo);
				}
				else{
					throw new HantoException("The horse must jump in a straight line.");
				}
					
			} 
			else if (pieceType == HantoPieceType.SPARROW) {
				if (!(ACBSJHFrom.getDistance(ACBSJHTo) < 5)) {
					throw new HantoException("The sparrow can only fly up to four tiles.");
				}
			}
		}
	}
	
	/**
	 * Checks if a piece is trying to slide when it cannot due to pieces around it.
	 * 
	 * Assumes that the piece is trying to walk to an otherwise valid position.
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	private void checkForSlidingException(HantoCoordinateACBSJH from, HantoCoordinateACBSJH to) throws HantoException
	{
		Collection<HantoCoordinateACBSJH> fromNeighbors = from.getAllAdjacentCoordinates();
		Collection<HantoCoordinateACBSJH> toNeighbors = to.getAllAdjacentCoordinates();
		List<HantoCoordinateACBSJH> coordinatesToCheck = new ArrayList<HantoCoordinateACBSJH>();
		for(HantoCoordinateACBSJH fromNeighbor : fromNeighbors)
		{
			for(HantoCoordinateACBSJH toNeighbor : toNeighbors)
			{
				if(fromNeighbor.equals(toNeighbor))
				{
					coordinatesToCheck.add(fromNeighbor);
				}
			}
		}
		if(getPieceAt(coordinatesToCheck.get(0)) != null && getPieceAt(coordinatesToCheck.get(1)) != null)
		{
			throw new HantoException("Cannot slide piece, pieces are blocking it at " + from + " and " + to);
		}
	}

	/**
	 * Checks if a horse is trying to jump over a gap.
	 * 
	 * Assumes the path from 'from' to 'to' is straight.
	 * 
	 * @param from
	 * @param to
	 * @throws HantoException
	 */
	private void checkForGapInHorsePath(HantoCoordinateACBSJH from, HantoCoordinateACBSJH to) throws HantoException
	{
		if(from.getDistance(to) == 1)
		{
			throw new HantoException("Horse can only jump over pieces, it cannot walk.");
		}
		int deltaX = 0;
		if(to.getX() > from.getX())
		{
			deltaX = 1;
		}
		else if(to.getX() < from.getX())
		{
			deltaX = -1;
		}
		
		int deltaY = 0;
		if(to.getY() > from.getY())
		{
			deltaY = 1;
		}
		else if(to.getY() < from.getY())
		{
			deltaY = -1;
		}
		
		HantoCoordinateACBSJH visiting = new HantoCoordinateACBSJH(from.getX() + deltaX, from.getY() + deltaY);
		while(!visiting.equals(to))
		{
			if(getPieceAt(visiting) == null)
			{
				throw new HantoException("Horse cannot jump over gap at " + visiting);
			}
			visiting = new HantoCoordinateACBSJH(visiting.getX() + deltaX, visiting.getY() + deltaY);
		}
	}
	
	protected void addValidHantoPieceTypes()
	{
		super.addValidHantoPieceTypes();
		ValidHantoPieceTypes.add(HantoPieceType.SPARROW);
		ValidHantoPieceTypes.add(HantoPieceType.CRAB);
		ValidHantoPieceTypes.add(HantoPieceType.HORSE);
	}
	
	/**
	 * 
	 *  @return Collection of HantoMoveRecords
	 */
	public Collection<HantoMoveRecord> getAllPossibleMovesForCurrentPlayer()
	{
		
		Collection<HantoMoveRecord> listOfPossibleMoves = new ArrayList<HantoMoveRecord>();
		Collection<HantoCoordinateACBSJH> listOfPossibleDestinations = getMoveDestinations();
		
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if (hp.getColor() == getCurrentPlayersTurn())
			{
				for(HantoCoordinateACBSJH destination : listOfPossibleDestinations)
				{
					boolean isMoveInvalid = false;
					
					try
					{
						HantoGame copyHantoGame = HantoGameFactory.getInstance().cloneHantoGame(this);
						copyHantoGame.makeMove(hp.getType(), hp.getLocation(), destination);
					}
					catch (Exception he)
					{
						isMoveInvalid = true;
					}
						
					if (!isMoveInvalid)
					{
						listOfPossibleMoves.add(new HantoMoveRecord(hp.getType(), hp.getLocation(), destination));
					}
				
				}
			}
		}
		
		return listOfPossibleMoves;
	}
	
	private boolean isBoardEmpty()
	{
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if(!hp.isInHand())
			{
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return
	 */
	private Collection<HantoCoordinateACBSJH> getMoveDestinations() 
	{
		Collection<HantoCoordinateACBSJH> moveDestinations = new ArrayList<HantoCoordinateACBSJH>();
		if(isBoardEmpty())
		{
			moveDestinations.add(new HantoCoordinateACBSJH(0, 0));
		}
		else
		{
			for(HantoPieceACBSJH hp : HantoPieces)
			{
				if (hp.getLocation() != null) {
					for (HantoCoordinateACBSJH newDestination : hp.getLocation().getAllAdjacentCoordinates())
					{
						boolean foundInMoveDestinations = false;
						for(HantoCoordinateACBSJH moveDestination : moveDestinations)
						{
							if(moveDestination.equals(newDestination))
							{
								foundInMoveDestinations = true;
							}
						}
						if(!foundInMoveDestinations)
						{
							moveDestinations.add(newDestination);
						}
					}
				}
			}
		}
		return moveDestinations;
	}
	
	/**
	 * This is an optomized version of getAllPossibleMovesForCurrentPlayer, to give us a better chance at winning.
	 * @return a Collection of HantoMoveRecords
	 */
	public Collection<HantoMoveRecord> getOptomizedPossibleMovesForCurrentPlayer()
	{
		
		Collection<HantoMoveRecord> listOfPossibleMoves = new ArrayList<HantoMoveRecord>();
		Collection<HantoCoordinateACBSJH> listOfPossibleDestinations = getMoveDestinations();
		
		for(HantoPieceACBSJH hp : HantoPieces)
		{
			if (hp.getColor() == getCurrentPlayersTurn())
			{
				for(HantoCoordinateACBSJH destination : listOfPossibleDestinations)
				{
					boolean isMoveInvalid = false;
					
					try
					{
						HantoGame copyHantoGame = HantoGameFactory.getInstance().cloneHantoGame(this);
						MoveResult mr = copyHantoGame.makeMove(hp.getType(), hp.getLocation(), destination);
						
						if (getCurrentPlayersTurn() == HantoPlayerColor.BLUE) {
							if (mr == MoveResult.RED_WINS) {
								isMoveInvalid = true;
							}
						} else {
							if (mr == MoveResult.BLUE_WINS) {
								isMoveInvalid = true;
							}
						}
						
						if (getCurrentPlayersTurn() == HantoPlayerColor.BLUE) {
							if (mr == MoveResult.BLUE_WINS) {
								listOfPossibleMoves.clear();
								listOfPossibleMoves.add(new HantoMoveRecord(hp.getType(), hp.getLocation(), destination));
								return listOfPossibleMoves;
							}
						} else {
							if (mr == MoveResult.RED_WINS) {
								listOfPossibleMoves.clear();
								listOfPossibleMoves.add(new HantoMoveRecord(hp.getType(), hp.getLocation(), destination));
								return listOfPossibleMoves;
							}
						}
						
					}
					catch (Exception he)
					{
						isMoveInvalid = true;
					}
						
					if (!isMoveInvalid)
					{
						listOfPossibleMoves.add(new HantoMoveRecord(hp.getType(), hp.getLocation(), destination));
					}
				
				}
			}
		}
		
		return listOfPossibleMoves;
	}
	
}
