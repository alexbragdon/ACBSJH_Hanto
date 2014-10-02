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
package deltaHantoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
import hanto.studentACBSJH.gamma.GammaHantoGame;

import org.junit.Before;
import org.junit.Test;

import common.HantoTestGame;
import common.HantoTestGameFactory;

/**
 * @author Alex
 *
 */
public class DeltaHantoTests {
	final HantoCoordinateACBSJH HAND = null;
	HantoCoordinateACBSJH origin;

	HantoGame deltaHantoGame;
	HantoTestGame testDeltaHantoGame;

	/**
	 * 
	 */
	@Before
	public void setup() {
		origin = new HantoCoordinateACBSJH(0, 0);
		testDeltaHantoGame = HantoTestGameFactory.getInstance()
				.makeHantoTestGame(HantoGameID.DELTA_HANTO);
		deltaHantoGame = testDeltaHantoGame;
	}

	/**
	 * @throws HantoException
	 */
	@Test
	public void testPlacePieceSparrowFromHand() throws HantoException {

		MoveResult result = deltaHantoGame.makeMove(HantoPieceType.SPARROW,
				HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = deltaHantoGame.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.BLUE, placedPiece.getColor());
		assertEquals(HantoPieceType.SPARROW, placedPiece.getType());
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testCannotMoveOtherPlayerPiece() throws HantoException {

		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(1, 0);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectMoveToNullLocation() throws HantoException {
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, null);
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectSecondMoveLocation() throws HantoException {

		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin,
				new HantoCoordinateACBSJH(1, 1));
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveNonExsistantPieceFromLocation() throws HantoException {

		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin,
				new HantoCoordinateACBSJH(0, 1));
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveOtherPlayersPieceOnSecondTurnTest()
			throws HantoException {

		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotPlacePieceOnOccupiedSpace() throws HantoException {

		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
	}

	@Test(expected = HantoException.class)
	public void cannotPlacePieceInNonAdjacentLocation() throws HantoException {

		// turn 1
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, 1));
		// turn 2
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 1));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 0));
		// turn 3
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, -1));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(3, 0));
	}

	@Test
	public void redWinsSurroundingBlueWithPiecesFromHand()
			throws HantoException {

		// turn 1
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));
		// turn 2
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(2, 0));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, 1));
		// turn 3
		deltaHantoGame.makeMove(HantoPieceType.CRAB, HAND,
				new HantoCoordinateACBSJH(3, 0));
		deltaHantoGame.makeMove(HantoPieceType.CRAB, HAND,
				new HantoCoordinateACBSJH(-1, 1));
		// turn 4
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(4, 0));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 0));
		// turn 5
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(5, 0));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, -1));
		// turn 6
		MoveResult mrOK = deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(6, 0));
		MoveResult mrWin = deltaHantoGame.makeMove(HantoPieceType.SPARROW,
				HAND, new HantoCoordinateACBSJH(1, -1));
		assertEquals(MoveResult.OK, mrOK);
		assertEquals(MoveResult.RED_WINS, mrWin);
	}

	@Test
	public void blueWinsSurroundingBlueWithPiecesFromHand()
			throws HantoException {

		// turn 1
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 1));
		// turn 2
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(1, 0));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, -1));
		// turn 3
		deltaHantoGame.makeMove(HantoPieceType.CRAB, HAND,
				new HantoCoordinateACBSJH(1, 1));
		deltaHantoGame.makeMove(HantoPieceType.CRAB, HAND,
				new HantoCoordinateACBSJH(0, -2));
		// turn 4
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, 2));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, -3));
		// turn 5
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 2));
		MoveResult mrOK = deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, -4));
		// turn 6
		MoveResult mrWin = deltaHantoGame.makeMove(HantoPieceType.SPARROW,
				HAND, new HantoCoordinateACBSJH(-1, 1));

		assertEquals(MoveResult.OK, mrOK);
		assertEquals(MoveResult.BLUE_WINS, mrWin);
	}

	@Test(expected = HantoException.class)
	public void noSuchPieceInHandExsists() throws HantoException {

		// turn 1
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));
		// turn 2
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(2, 0));
	}

	@Test(expected = HantoException.class)
	public void cannotBreakContinuityWithMove() throws HantoException {
		// turn 1
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));

		// turn 2
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(2, 0));
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY,
				new HantoCoordinateACBSJH(1, 0), new HantoCoordinateACBSJH(-1,
						0));

	}

	@Test(expected = HantoException.class)
	public void cannotMoveMoreThanOneSpace() throws HantoException {
		// turn 1
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));

		// turn 2
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY,
				origin, new HantoCoordinateACBSJH(2,0));
		deltaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(3, 0));

	}
	
	@Test
	public void testGammaHantoGameFactory() {
		HantoGameFactory.getInstance().makeHantoGame(HantoGameID.GAMMA_HANTO);
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
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 1));

		deltaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 2));
	}

}
