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
		HantoGame alphaHanto = new AlphaHantoGame();
		assertEquals(null, alphaHanto.getPieceAt(new HantoCoordinateACBSJH(0, 0)));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test
	public void correctFirstBlueMove() throws HantoException
	{
		HantoGame alphaHanto = new AlphaHantoGame();
		MoveResult result = alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = alphaHanto.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
	}
	
	@Test
	public void correctSecondRedMove() throws HantoException 
	{
		HantoGame alphaHanto = new AlphaHantoGame();
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		MoveResult result = alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, destination);
		assertEquals(MoveResult.DRAW, result);
		HantoPiece placedPiece = alphaHanto.getPieceAt(destination);
		assertNotEquals(null, placedPiece);

	}
	
	@Test
	public void testFactoryCanMakeGames() throws HantoException {
		HantoGame alphaHanto = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.ALPHA_HANTO, HantoPlayerColor.BLUE);
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		MoveResult result = alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, destination);
		assertEquals(MoveResult.DRAW, result);
	}

	@Test(expected = HantoException.class)
	public void incorrectFirstBlueMoveLocation() throws HantoException
	{
		HantoGame alphaHanto = new AlphaHantoGame();
		alphaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(0, 1));
	}
	
	@Test(expected = HantoException.class)
	public void incorrectFirstBlueMovePieceType() throws HantoException
	{
		HantoGame alphaHanto = new AlphaHantoGame();
		alphaHanto.makeMove(HantoPieceType.CRAB, HAND, new HantoCoordinateACBSJH(0, 0));
	}
	
	
}
