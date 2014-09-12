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
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

public class HantoPieceACBSJH implements HantoPiece {
	
	private HantoPieceType type;
	private HantoPlayerColor playerColor;
	
	public HantoPieceACBSJH(HantoPlayerColor color) {
		type = HantoPieceType.BUTTERFLY;
		playerColor = color;
	}
	
	@Override
	public HantoPlayerColor getColor() {
		return playerColor;
	}

	@Override
	public HantoPieceType getType() {
		return type;
	}

}
