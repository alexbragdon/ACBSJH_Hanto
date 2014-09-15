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

package AlphaHantoTests;
import static org.junit.Assert.*;
import hanto.HantoGameFactory;
import hanto.common.HantoException;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.alpha.AlphaHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

import org.junit.Before;
import org.junit.Test;

import hanto.common.*;

/**
 * @author alexbragdon
 *
 */
public class AlphaHantoTests {

	HantoGame alphaHantoGame;
	HantoCoordinateACBSJH testCoordinate1;
	HantoPieceACBSJH testPieceBlue;
	HantoPieceACBSJH testPieceRed;
	
	final HantoCoordinateACBSJH HAND = null;
	
	HantoCoordinateACBSJH origin;
	
	/**
	 * 
	 */
	@Before
	public void setup()
	{
		alphaHantoGame  = new AlphaHantoGame();
		testCoordinate1 = new HantoCoordinateACBSJH(1, 2);
		origin = new HantoCoordinateACBSJH(0, 0);
		testPieceRed = new HantoPieceACBSJH(HantoPlayerColor.RED);
		testPieceBlue = new HantoPieceACBSJH(HantoPlayerColor.BLUE);
	}
	
	/**
	 * 
	 */
	@Test
	public void testGettersForCoordinates() {
		assertEquals(1, testCoordinate1.getX());
		assertEquals(2, testCoordinate1.getY());
	}
	
	/**
	 * 
	 */
	@Test
	public void testPieces() {
		assertEquals(HantoPlayerColor.BLUE, testPieceBlue.getColor());
		assertEquals("Butterfly", testPieceRed.getType().getPrintableName());
	}
	
	/**
	 * 		AlphaHantoGame Tests
	 */
	
	@Test
	public void emptyCoordinateTest()
	{
		assertEquals(null, alphaHantoGame.getPieceAt(new HantoCoordinateACBSJH(0, 0)));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test
	public void correctFirstBlueMove() throws HantoException
	{
		MoveResult result = alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = alphaHantoGame.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.BLUE, placedPiece.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, placedPiece.getType());
	}
	
	/**
	 * @throws HantoException
	 */
	@Test
	public void correctSecondRedMove() throws HantoException 
	{
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		MoveResult result = alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, destination);
		assertEquals(MoveResult.DRAW, result);
		HantoPiece placedPiece = alphaHantoGame.getPieceAt(destination);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.RED, placedPiece.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, placedPiece.getType());

	}
	
	/**
	 * @throws HantoException
	 */
	@Test
	public void testFactoryCanMakeGames() throws HantoException {
		HantoGame alphaHanto = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.ALPHA_HANTO, HantoPlayerColor.BLUE);
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		MoveResult result = alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, destination);
		assertEquals(MoveResult.DRAW, result);
	}

	/**
	 * 
	 */
	@Test
	public void testAdjacency() {
		HantoCoordinateACBSJH from = new HantoCoordinateACBSJH(0, 0);
		HantoCoordinate to1 = new HantoCoordinateACBSJH(0, 1);
		HantoCoordinate to2 = new HantoCoordinateACBSJH(1, 0);
		HantoCoordinate to3 = new HantoCoordinateACBSJH(1, -1);
		HantoCoordinate to4 = new HantoCoordinateACBSJH(0, -1);
		HantoCoordinate to5 = new HantoCoordinateACBSJH(-1, 0);
		HantoCoordinate to6 = new HantoCoordinateACBSJH(-1, 1);
		
		HantoCoordinate not1 = new HantoCoordinateACBSJH(1, 2);
		HantoCoordinate not2 = new HantoCoordinateACBSJH(-1, -2);
		HantoCoordinate not3 = new HantoCoordinateACBSJH(0, -2);
		
		assertFalse(from.isAdjacent(not1));
		assertFalse(from.isAdjacent(not2));
		assertFalse(from.isAdjacent(not3));
		
		assertTrue(from.isAdjacent(to1));
		assertTrue(from.isAdjacent(to2));
		assertTrue(from.isAdjacent(to3));
		assertTrue(from.isAdjacent(to4));
		assertTrue(from.isAdjacent(to5));
		assertTrue(from.isAdjacent(to6));
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectFirstBlueMoveLocation() throws HantoException
	{
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(0, 1));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectFirstBlueMovePieceType() throws HantoException
	{
		alphaHantoGame.makeMove(HantoPieceType.CRAB, HAND, new HantoCoordinateACBSJH(0, 0));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectMoveToNullLocation() throws HantoException
	{
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, null);
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectSecondMoveLocation() throws HantoException
	{
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 1));
	}

	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveNonExsistantPieceFromLocation() throws HantoException
	{
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, new HantoCoordinateACBSJH(0, 1));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveOtherPlayersPieceOnSecondTurnTest() throws HantoException
	{
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}
	
	/**
	 * @throws HantoException
	 */
	@Test
	public void testPrintableBoard() throws HantoException
	{
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		alphaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, destination);
		String board = alphaHantoGame.getPrintableBoard();
		String expected = "-=AlphaHantoGame=-\nButterfly @ (0, 0)\nButterfly @ (0, 1)\n-end-";
		assertEquals(expected, board);
	}
}
