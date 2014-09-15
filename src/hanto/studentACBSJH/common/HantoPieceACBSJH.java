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

/**
 * @author alexbragdon seanhalloran
 * Our implementation of the HantoPience Class. 
 */
public class HantoPieceACBSJH implements HantoPiece {
	
	private HantoPieceType type; 
	private HantoPlayerColor playerColor;
	private HantoCoordinateACBSJH location = null;
	
	/**
	 * @param color
	 */
	public HantoPieceACBSJH(HantoPlayerColor color) {
		this(color, HantoPieceType.BUTTERFLY);
	}
	
	public HantoPieceACBSJH(HantoPlayerColor color, HantoPieceType type) {
		playerColor = color;
		this.type = type;
	}
	
	/** (non-Javadoc)
	 * @see hanto.common.HantoPiece#getColor()
	 */
	@Override
	public HantoPlayerColor getColor() {
		return playerColor;
	}

	/** (non-Javadoc)
	 * @see hanto.common.HantoPiece#getType()
	 */
	@Override
	public HantoPieceType getType() {
		return type;
	}
	
	/**
	 * @return
	 */
	public HantoCoordinateACBSJH getLocation() {
		return location;
	}
	
	/**
	 * @param newLocation
	 */
	public void setLocation(HantoCoordinateACBSJH newLocation) {
		location = newLocation;
	}

	public boolean isInHand()
	{
		return location == null;
	}
	
	public String toString()
	{
		return type.toString() + " @ " + location.toString();
	}
	
}
