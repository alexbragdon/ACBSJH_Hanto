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
package BetaHantoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentACBSJH.beta.BetaHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;

import org.junit.Before;
import org.junit.Test;

/**
 * @author alexbragdon
 *
 */
public class BetaHantoTests {
	
	final HantoCoordinateACBSJH HAND = null;
	HantoCoordinateACBSJH origin;
	
	HantoGame betaHantoGame;
	
	/**
	 * 
	 */
	@Before
	public void setup()
	{
		origin = new HantoCoordinateACBSJH(0, 0);
		betaHantoGame = new BetaHantoGame();
	}
	
	/**
	 * @throws HantoException
	 */
	@Test
	public void testPlacePieceSparrow() throws HantoException {
		MoveResult result = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = betaHantoGame.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.BLUE, placedPiece.getColor());
		assertEquals(HantoPieceType.SPARROW, placedPiece.getType());
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testCannotMoveAllreadyPlacePieces() throws HantoException {
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(1, 0);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotPlaceCrabTest() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.CRAB, HAND, new HantoCoordinateACBSJH(0, 0));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectMoveToNullLocation() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, null);
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectSecondMoveLocation() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, new HantoCoordinateACBSJH(1, 1));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveNonExsistantPieceFromLocation() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, new HantoCoordinateACBSJH(0, 1));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveOtherPlayersPieceOnSecondTurnTest() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotPlacePieceOnOccupiedSpace() throws HantoException {
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void blueDoesNotPlaceButterFlyByFourthTurn() throws HantoException
	{
		//turn 1
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 2
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 0));
		//turn 3
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, -1));
		//turn 4
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, 0));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void redDoesNotPlaceButterFlyByFourthTurn() throws HantoException
	{
		//turn 1
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 2
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 0));
		//turn 3
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, -1));
		//turn 4
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 2));
	}
	
	@Test(expected = HantoException.class)
	public void cannotPlacePieceInNonAdjacentLocation() throws HantoException
	{
		//turn 1
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 2
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 0));
		//turn 3
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(3, 0));
	}
	
	@Test
	public void redWins() throws HantoException
	{
		//turn 1
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 0));
		//turn 2
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(2, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 3
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(3, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
		//turn 4
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(4, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 0));
		//turn 5
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(5, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		//turn 6
		MoveResult mrOK = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(6, 0));
		MoveResult mrWin = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, -1));
		assertEquals(MoveResult.OK, mrOK);
		assertEquals(MoveResult.RED_WINS, mrWin);
	}
	
	@Test
	public void blueWins() throws HantoException
	{
		//turn 1
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 2
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		//turn 3
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, 1));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -2));
		//turn 4
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 2));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -3));
		//turn 5
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 2));
		MoveResult mrOK = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -4));
		//turn 6
		MoveResult mrWin = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
		
		assertEquals(MoveResult.OK, mrOK);
		assertEquals(MoveResult.BLUE_WINS, mrWin);
	}

	@Test
	public void testDrawCondition() throws HantoException {
		//turn 3
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, 0));
		//turn 2
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(2, 0));
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(3, 0));
		//turn 3
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(4, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(5, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(6, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(7, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(8, 0));
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(9, 0));
		MoveResult okResult = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(10, 0));
		assertEquals(MoveResult.OK, okResult);
		MoveResult result = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(11, 0));
		assertEquals(MoveResult.DRAW, result);
	}
}
