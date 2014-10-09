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
package gammaHantoTests;

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

import org.junit.Before;
import org.junit.Test;

import common.HantoTestGame;
import common.HantoTestGameFactory;

/**
 * @author Sean
 *
 */
public class GammaHantoTests {
	final HantoCoordinateACBSJH HAND = null;
	HantoCoordinateACBSJH origin;

	HantoGame gammaHantoGame;
	HantoTestGame testGammaHantoGame;

	/**
	 * 
	 */
	@Before
	public void setup() {
		origin = new HantoCoordinateACBSJH(0, 0);
		testGammaHantoGame = HantoTestGameFactory.getInstance()
				.makeHantoTestGame(HantoGameID.GAMMA_HANTO);
		gammaHantoGame = testGammaHantoGame;
	}

	/**
	 * @throws HantoException
	 */
	@Test
	public void testPlacePieceSparrowFromHand() throws HantoException {

		MoveResult result = gammaHantoGame.makeMove(HantoPieceType.SPARROW,
				HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = gammaHantoGame.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.BLUE, placedPiece.getColor());
		assertEquals(HantoPieceType.SPARROW, placedPiece.getType());
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void testCannotMoveOtherPlayerPiece() throws HantoException {

		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(1, 0);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotPlaceCrabTest() throws HantoException {
		gammaHantoGame.makeMove(HantoPieceType.CRAB, HAND,
				new HantoCoordinateACBSJH(0, 0));
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectMoveToNullLocation() throws HantoException {
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, null);
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectSecondMoveLocation() throws HantoException {

		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin,
				new HantoCoordinateACBSJH(1, 1));
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveNonExsistantPieceFromLocation() throws HantoException {

		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin,
				new HantoCoordinateACBSJH(0, 1));
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveOtherPlayersPieceOnSecondTurnTest()
			throws HantoException {

		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotPlacePieceOnOccupiedSpace() throws HantoException {

		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
	}

	@Test(expected = HantoException.class)
	public void cannotPlacePieceInNonAdjacentLocation() throws HantoException {

		// turn 1
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, 1));
		// turn 2
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 1));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(-1, 0));
		// turn 3
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(0, -1));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(3, 0));
	}

	@Test(expected = HantoException.class)
	public void noSuchPieceInHandExsists() throws HantoException {

		// turn 1
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));
		// turn 2
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(2, 0));
	}

	@Test(expected = HantoException.class)
	public void cannotBreakContinuityWithMove() throws HantoException {
		// turn 1
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));

		// turn 2
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(2, 0));
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY,
				new HantoCoordinateACBSJH(1, 0), new HantoCoordinateACBSJH(-1,
						0));

	}

	@Test
	public void testDrawOnTurnTwenty() throws HantoException {
		testGammaHantoGame.setTurnNumber(20);
		testGammaHantoGame.setPlayerMoving(HantoPlayerColor.RED);
		testGammaHantoGame
				.initializeBoard(new HantoTestGame.PieceLocationPair[] { new HantoTestGame.PieceLocationPair(
						HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, origin),
						new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED,
								HantoPieceType.BUTTERFLY, new HantoCoordinateACBSJH(0, 1))
		});

		assertEquals(MoveResult.DRAW,
				gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
						new HantoCoordinateACBSJH(0, 2)));
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
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 1));

		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 2));
	}
	
	@Test(expected = HantoException.class)
	public void testInitializeBoardRemovesPiecesFromHand() throws HantoException
	{
		testGammaHantoGame.initializeBoard(new HantoTestGame.PieceLocationPair[]
			{
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.BLUE, HantoPieceType.BUTTERFLY, origin),
				new HantoTestGame.PieceLocationPair(HantoPlayerColor.RED, HantoPieceType.BUTTERFLY, new HantoCoordinateACBSJH(0, 1))
			});
		testGammaHantoGame.setPlayerMoving(HantoPlayerColor.BLUE);
		testGammaHantoGame.setTurnNumber(2);
		
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(0, 2));
	}
	
	@Test(expected = HantoException.class)
	public void cannotMoveMoreThanOneSpace() throws HantoException {
		// turn 1
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND,
				new HantoCoordinateACBSJH(1, 0));

		// turn 2
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY,
				origin, new HantoCoordinateACBSJH(2,0));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND,
				new HantoCoordinateACBSJH(3, 0));
	}
	
	@Test(expected = HantoException.class)
	public void cannotPlacePieceFromHandNextToOpponentPiece() throws HantoException
	{
		//turn 1
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 0));
		//turn 2
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(2, 0));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 3
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(3, 0));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
	}
}
