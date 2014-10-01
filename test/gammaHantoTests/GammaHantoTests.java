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
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;

import org.junit.Before;
import org.junit.Test;

import sun.net.ftp.FtpDirEntry.Permission;
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
	public void setup()
	{
		origin = new HantoCoordinateACBSJH(0, 0);
		testGammaHantoGame = HantoTestGameFactory.getInstance().makeHantoTestGame(HantoGameID.GAMMA_HANTO);
		gammaHantoGame = testGammaHantoGame;
	}
	
	/**
	 * @throws HantoException
	 */
	@Test
	public void testPlacePieceSparrowFromHand() throws HantoException {
		
		MoveResult result = gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
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
	public void cannotPlaceCrabTest() throws HantoException
	{
		
		
		gammaHantoGame.makeMove(HantoPieceType.CRAB, HAND, new HantoCoordinateACBSJH(0, 0));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectMoveToNullLocation() throws HantoException
	{
		
		
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, null);
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void incorrectSecondMoveLocation() throws HantoException
	{
		
		
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, new HantoCoordinateACBSJH(1, 1));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveNonExsistantPieceFromLocation() throws HantoException
	{
		
		
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, origin, new HantoCoordinateACBSJH(0, 1));
	}
	
	/**
	 * @throws HantoException
	 */
	@Test(expected = HantoException.class)
	public void cannotMoveOtherPlayersPieceOnSecondTurnTest() throws HantoException
	{
		
		
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
	public void cannotPlacePieceInNonAdjacentLocation() throws HantoException
	{
		
		
		//turn 1
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 2
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 0));
		//turn 3
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(3, 0));
	}
	
	@Test
	public void redWinsSurroundingBlueWithPiecesFromHand() throws HantoException
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
		//turn 4
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(4, 0));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 0));
		//turn 5
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(5, 0));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		//turn 6
		MoveResult mrOK = gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(6, 0));
		MoveResult mrWin = gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, -1));
		assertEquals(MoveResult.OK, mrOK);
		assertEquals(MoveResult.RED_WINS, mrWin);
	}
	
	@Test
	public void blueWinsSurroundingBlueWithPiecesFromHand() throws HantoException
	{
		
		
		//turn 1
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(0, 1));
		//turn 2
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, 0));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -1));
		//turn 3
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(1, 1));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -2));
		//turn 4
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, 2));
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -3));
		//turn 5
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 2));
		MoveResult mrOK = gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(0, -4));
		//turn 6
		MoveResult mrWin = gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(-1, 1));
		
		assertEquals(MoveResult.OK, mrOK);
		assertEquals(MoveResult.BLUE_WINS, mrWin);
	}
	
	@Test(expected = HantoException.class)
	public void noSuchPieceInHandExsists() throws HantoException
	{
		
		//turn 1
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 0));
		//turn 2
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(2, 0));
	}

	@Test(expected = HantoException.class)
	public void cannotBreakContinuityWithMove() throws HantoException
	{
		//turn 1
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, HAND, new HantoCoordinateACBSJH(1, 0));
		
		//turn 2
		gammaHantoGame.makeMove(HantoPieceType.SPARROW, HAND, new HantoCoordinateACBSJH(2, 0));
		gammaHantoGame.makeMove(HantoPieceType.BUTTERFLY, new HantoCoordinateACBSJH(1, 0), new HantoCoordinateACBSJH(-1, 0));
		
	}
	
}
