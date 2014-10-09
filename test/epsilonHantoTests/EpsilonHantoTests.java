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
package epsilonHantoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentACBSJH.HantoGameFactory;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;
import common.HantoTestGame;
import common.HantoTestGameFactory;

/**
 * @author Sean
 *
 */
public class EpsilonHantoTests {

	final HantoCoordinateACBSJH HAND = null;
	HantoCoordinateACBSJH origin;
	
	HantoGame epsilonHantoGame;
	HantoTestGame epsilonHantoTestGame;
	
	@Before
	public void setup() {
		origin = new HantoCoordinateACBSJH(0, 0);
		epsilonHantoTestGame = HantoTestGameFactory.getInstance()
				.makeHantoTestGame(HantoGameID.EPSILON_HANTO);
		epsilonHantoGame = epsilonHantoTestGame;
	}
	
	@Test
	public void testPlacePieceSparrowFromHand() throws HantoException {
		MoveResult result = epsilonHantoGame.makeMove(HantoPieceType.SPARROW,
				HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = epsilonHantoGame.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.BLUE, placedPiece.getColor());
		assertEquals(HantoPieceType.SPARROW, placedPiece.getType());
	}

	@Test(expected = HantoException.class)
	public void testCannotMoveOtherPlayerPiece() throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(1, 0);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	@Test(expected = HantoException.class)
	public void incorrectMoveToNullLocation() throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, null);
	}

	@Test(expected = HantoException.class)
	public void incorrectSecondMoveLocation() throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin,
				new HantoCoordinateACBSJH(1, 1));
	}

	@Test(expected = HantoException.class)
	public void cannotMoveNonExsistantPieceFromLocation() throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, new HantoCoordinateACBSJH(0, 1),
				new HantoCoordinateACBSJH(0, 2));
	}

	@Test(expected = HantoException.class)
	public void cannotMoveOtherPlayersPieceOnSecondTurnTest()
			throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	@Test(expected = HantoException.class)
	public void cannotPlacePieceOnOccupiedSpace() throws HantoException {

		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
	}

	@Test(expected = HantoException.class)
	public void cannotPlacePieceInNonAdjacentLocation() throws HantoException {

		// turn 1
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, 1));
		// turn 2
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 1));
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 0));
		// turn 3
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, -1));
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(3, 0));
	}

	@Test(expected = HantoException.class)
	public void noSuchPieceInHandExsists() throws HantoException {

		// turn 1
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));
		// turn 2
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(2, 0));
	}

	@Test(expected = HantoException.class)
	public void cannotBreakContinuityWithMove() throws HantoException {
		// turn 1
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));

		// turn 2
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(2, 0));
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY,
				new HantoCoordinateACBSJH(1, 0), new HantoCoordinateACBSJH(-1,
						0));

	}

	@Test 
	public void testGammaHantoGameFactory() {
		HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO);
	}

	@Test
	public void testHantoCoordinateACBSJHConstructor() {
		HantoCoordinate hc1 = new HantoCoordinateACBSJH(0, 0);
		HantoCoordinateACBSJH hc2 = new HantoCoordinateACBSJH(hc1);
		assertEquals(hc1.getX(), hc2.getX());
		assertEquals(hc1.getY(), hc2.getY());
	}

	@Test
	public void testHantoPieceACBSJHConstructor() {
		HantoPiece hp1 = new HantoPieceACBSJH(HantoPlayerColor.BLUE,
				HantoPieceType.BUTTERFLY);
		HantoPieceACBSJH hp2 = new HantoPieceACBSJH(hp1);
		assertEquals(hp1.getType(), hp2.getType());
		assertEquals(hp1.getColor(), hp2.getColor());
	}

	@Test(expected = HantoException.class)
	public void testCannotMovePieceFromHandThatIsNotInHand()
			throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 1));

		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 2));
	}
	
	@Test
	public void testBlueResign() throws HantoException {
		MoveResult result = epsilonHantoGame.makeMove(null, null, null);
		assertEquals(MoveResult.RED_WINS, result);
	}

	@Test(expected = HantoException.class)
	public void testInitializeBoardRemovesPiecesFromHand() throws HantoException
	{
		epsilonHantoTestGame.initializeBoard(new HantoTestGame.PieceLocationPair[]
			{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, origin),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new HantoCoordinateACBSJH(0, 1))
			});
		epsilonHantoTestGame.setPlayerMoving(HantoPlayerColor.BLUE);
		epsilonHantoTestGame.setTurnNumber(2);
		
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 2));
	}
	
	@Test
	public void testRedResign() throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		MoveResult result = epsilonHantoGame.makeMove(null, null, null);
		assertEquals(MoveResult.BLUE_WINS, result);
	}
	
	@Test(expected = HantoException.class)
	public void testCannotMoveAfterGameOver() throws HantoException {
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(null, null, null);
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
	}
	
	@Test(expected = HantoException.class)
	public void cannotPlacePieceFromHandNextToOpponentPiece() throws HantoException
	{
		//turn 1
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		epsilonHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 0));
		//turn 2
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(2, 0));
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 3
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(3, 0));
		epsilonHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
	}
	
	@Test(expected = HantoException.class)
	public void butterflyPlacedByFourthTurn()
	{
		epsilonHantoTestGame.initializeBoard(new HantoTestGame.PieceLocationPair[]
			{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, origin),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new HantoCoordinateACBSJH(0, 1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new HantoCoordinateACBSJH(1, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.CRAB, new HantoCoordinateACBSJH(2, -1)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new HantoCoordinateACBSJH(1, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new HantoCoordinateACBSJH(2, 0)),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.CRAB, new HantoCoordinateACBSJH(1, 1)),
			});
		epsilonHantoTestGame.setTurnNumber(4);
		epsilonHantoTestGame.setPlayerMoving(HantoPlayerColor.RED);
		
		epsilonHantoGame.makeMove(HantoPieceType.CRAB, HAND, new HantoCoordinateACBSJH(0, 2));
	}
	
	
}
