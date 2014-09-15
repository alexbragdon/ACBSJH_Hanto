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
import hanto.HantoGameFactory;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studentACBSJH.alpha.AlphaHantoGame;
import hanto.studentACBSJH.beta.BetaHantoGame;
import hanto.studentACBSJH.common.BaseHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

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
	 * 
	 */
	@Test
	public void testSetupHands() {
		HantoGame betaHanto = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.BETA_HANTO, HantoPlayerColor.BLUE);
		HantoPieceACBSJH blueButterfly = new HantoPieceACBSJH(HantoPlayerColor.BLUE);
	}
	
	@Test
	public void testMakePieceOtherThanButterfly() throws HantoException {
		MoveResult result = betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = betaHantoGame.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.BLUE, placedPiece.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, placedPiece.getType());
	}
	
	@Test(expected = HantoException.class)
	public void testCantMoveAllreadyPlacePieces() throws HantoException {
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(1, 0);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}

	@Test(expected = HantoException.class)
	public void cannotPlaceCrabTest() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.CRAB, HAND, new HantoCoordinateACBSJH(0, 0));
	}
	
	@Test(expected = HantoException.class)
	public void incorrectMoveToNullLocation() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, null);
	}
	
	@Test(expected = HantoException.class)
	public void incorrectSecondMoveLocation() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 1));
	}
	
	@Test(expected = HantoException.class)
	public void cannotMoveNonExsistantPieceFromLocation() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, new HantoCoordinateACBSJH(0, 1));
	}
	
	@Test(expected = HantoException.class)
	public void cannotMoveOtherPlayersPieceOnSecondTurnTest() throws HantoException
	{
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(0, 1);
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}
	
	@Test(expected = HantoException.class)
	public void cannotPlacePieceOnOccupiedSpace() throws HantoException {
		betaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		betaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
	}
}
