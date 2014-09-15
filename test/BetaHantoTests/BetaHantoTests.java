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
	
	/**
	 * 
	 */
	@Before
	public void setup()
	{
		origin = new HantoCoordinateACBSJH(0, 0);
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
		HantoGame betaHanto = new BetaHantoGame();
		MoveResult result = betaHanto.makeMove(HantoPieceType.SPARROW, HAND, origin);
		assertEquals(MoveResult.OK, result);
		HantoPiece placedPiece = betaHanto.getPieceAt(origin);
		assertNotEquals(null, placedPiece);
		assertEquals(HantoPlayerColor.BLUE, placedPiece.getColor());
		assertEquals(HantoPieceType.BUTTERFLY, placedPiece.getType());
	}
	
	@Test(expected = HantoException.class)
	public void testCantMoveAllreadyPlacePieces() throws HantoException {
		HantoGame betaHanto = new BetaHantoGame();
		betaHanto.makeMove(HantoPieceType.BUTTERFLY, HAND, origin);
		HantoCoordinate destination = new HantoCoordinateACBSJH(1, 0);
		betaHanto.makeMove(HantoPieceType.BUTTERFLY, origin, destination);
	}
}
