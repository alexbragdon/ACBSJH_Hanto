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
package playerTests;

import static org.junit.Assert.*;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.tournament.HantoPlayer;
import hanto.tournament.HantoMoveRecord;

import org.junit.Before;
import org.junit.Test;

/**
 * @author alexbragdon
 *
 */
public class HantoPlayerTests {

	HantoPlayer testPlayer;
	HantoCoordinateACBSJH origin;
	HantoCoordinateACBSJH HAND;
	HantoMoveRecord resign;
	
	
	/**
	 * 
	 */
	@Before
	public void setup() {
		testPlayer = new HantoPlayer();
		origin = new HantoCoordinateACBSJH(0, 0);
		HAND = null;
		resign = new HantoMoveRecord(null, null, null);
	}

	/**
	 * @throws HantoException
	 */
	@Test
	public void testPlaysFirstPieceAtOriginFromHand() {
		for (int i = 0; i < 1000; i++) { //This player is random, 100 times should cover the scope.
			testPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
			HantoMoveRecord testRecord = testPlayer.makeMove(null);
			HantoCoordinateACBSJH to = (HantoCoordinateACBSJH) testRecord.getTo();
			assertTrue(origin.equals(to));
			assertEquals(null, testRecord.getFrom());
		}
	}	
	
	@Test
	public void testMoveRecordFunctions()
	{
		HantoMoveRecord testHMR = new HantoMoveRecord(HantoPieceType.BUTTERFLY, null, null);
		assertEquals(HantoPieceType.BUTTERFLY, testHMR.getPiece());
	}
	
	@Test
	public void startGameWithOponentRedFirstMovesToOrigin()
	{
		testPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, false);
		HantoMoveRecord hmr = testPlayer.makeMove(null);
		assertEquals(0, hmr.getTo().getX());
		assertEquals(0, hmr.getTo().getY());
	}
	
	@Test
	public void startGameWithOponentBlueFirstMovesToOrigin()
	{
		testPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED, false);
		HantoMoveRecord hmr = testPlayer.makeMove(null);
		assertEquals(0, hmr.getTo().getX());
		assertEquals(0, hmr.getTo().getY());
	}
	
	@Test
	public void makeMoveAfterOpponent()
	{
		testPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, false);
		HantoMoveRecord hmr = testPlayer.makeMove(new HantoMoveRecord(HantoPieceType.CRAB, HAND, origin));
		assertNotEquals(resign.getTo(), hmr.getTo());
		assertNotEquals(resign.getPiece(), hmr.getPiece());
	}
	
	@Test
	public void opponentMakesInvalidMove()
	{
		testPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, false);
		HantoMoveRecord hmr = testPlayer.makeMove(new HantoMoveRecord(HantoPieceType.CRAB, HAND, new HantoCoordinateACBSJH(1, 1)));
		assertEquals(resign.getTo(), hmr.getTo());
		assertEquals(resign.getFrom(), hmr.getFrom());
		assertEquals(resign.getPiece(), hmr.getPiece());
	}
}
