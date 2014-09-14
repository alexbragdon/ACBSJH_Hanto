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
package hanto.studentACBSJH.common;

import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseHantoGame {

	protected Collection<HantoPiece> HantoPieces;
	
	protected int TurnNumber; 
	
	protected BaseHantoGame()
	{
		HantoPieces = new ArrayList<HantoPiece>();
		TurnNumber = 0;
		setupHands();
	}
	
	protected HantoPlayerColor getCurrentPlayersTurn()
	{
		return (TurnNumber % 2 == 0) ? HantoPlayerColor.BLUE : HantoPlayerColor.RED;
	}
	
	protected abstract void setupHands();
	
}
