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
package hanto.studentACBSJH.tournament;

import java.util.Collection;
import java.util.Random;

import hanto.common.HantoException;
import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.HantoGameFactory;
import hanto.studentACBSJH.epsilon.EpsilonHantoGame;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/**
 * @author Sean
 *
 */
public class HantoPlayer implements HantoGamePlayer {

	/**
	 * This is our local copy of the game.
	 */
	EpsilonHantoGame localGame;

	/** (non-Javadoc)
	 * @see hanto.tournament.HantoGamePlayer#startGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor, boolean)
	 *
	 * Starts a Hanto Game for the with the given parameters. 
	 * 
	 */
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst) {
		
		if (doIMoveFirst) {
			localGame = (EpsilonHantoGame) HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO, myColor);
		} else {
			if (myColor == HantoPlayerColor.BLUE) {
				localGame = (EpsilonHantoGame) HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.RED);
			} else {
				localGame = (EpsilonHantoGame) HantoGameFactory.getInstance().makeHantoGame(HantoGameID.EPSILON_HANTO, HantoPlayerColor.BLUE);
			}
		}
	}

	/** (non-Javadoc)
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 * 
	 * Returns a hanto move record based on the move of the other player.
	 * If the input is null assume we go first.
	 * 
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		boolean opponetWasNull = false;
		HantoMoveRecord toReturn = new HantoMoveRecord(null, null, null);
		
		if (opponentsMove == null) {
			opponetWasNull = true;
			toReturn = makeRandomValidMove();
		} else {
			try {
				localGame.makeMove(opponentsMove.getPiece(), opponentsMove.getFrom(), opponentsMove.getTo());
			} catch (HantoException e) {
				return new HantoMoveRecord(null, null, null);
			}
			if (!opponetWasNull) {
				toReturn = makeRandomValidMove();
			}
		}
		try {
			localGame.makeMove(toReturn.getPiece(), toReturn.getFrom(), toReturn.getTo());
		} catch (HantoException e) {
			return new HantoMoveRecord(null, null, null);
		}
		return toReturn;
	}
	
	/**
	 * Returns a random valid hanto move for the current player, if there is none then resign the game.
	 * @return HantoMoveRecord
	 */
	private HantoMoveRecord makeRandomValidMove() {
			Collection<HantoMoveRecord> legalMoves = localGame.getOptomizedPossibleMovesForCurrentPlayer();
			if (legalMoves.size() != 0) {
				Random generator = new Random(); 
				int selectedMoveNumber = generator.nextInt(legalMoves.size());
				int count = 0;
				for(HantoMoveRecord mr : legalMoves) {
					if (count == selectedMoveNumber) {
						return mr;
					} 
					count ++;
				}
			}
		return new HantoMoveRecord(null, null, null);
	}
}
