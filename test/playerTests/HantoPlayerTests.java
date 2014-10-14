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
	
	
	/**
	 * 
	 */
	@Before
	public void setup() {
		testPlayer = new HantoPlayer();
		origin = new HantoCoordinateACBSJH(0, 0);
	}

	/**
	 * @throws HantoException
	 */
	@Test
	public void testPlaysFirstPieceAtOrigin() {
		testPlayer.startGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE, true);
		//assertEquals(HantoPieceType.BUTTERFLY, testPlayer.makeMove(null).getPiece());
		HantoMoveRecord testRecord = testPlayer.makeMove(null);
		HantoCoordinateACBSJH to = (HantoCoordinateACBSJH) testRecord.getTo();
		assertTrue(origin.equals(to));
		assertEquals(null, testRecord.getFrom());
	}
		
}
