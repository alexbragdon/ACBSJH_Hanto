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

import java.util.Collection;

import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.tournament.HantoMoveRecord;

/**
 * @author alexbragdon seanhalloran
 * Our implementation of the HantoPience Class. 
 */
public class HantoPieceACBSJH implements HantoPiece {
	
	/**
	 * The type of Hanto Piece this is.
	 */
	private HantoPieceType type; 
	
	/**
	 * The color of this piece, denoting which player the piece belongs to.
	 */
	private HantoPlayerColor playerColor;
	
	/**
	 * The location of this piece. If null, then the piece is in a player's
	 * hand.
	 */
	private HantoCoordinateACBSJH location = null;
	
	/**
	 * @param color
	 */
	public HantoPieceACBSJH(HantoPlayerColor color) {
		this(color, HantoPieceType.BUTTERFLY);
	}
	
	/**
	 * Creates a piece of a certain color and type
	 * 
	 * @param color
	 * @param type
	 */
	public HantoPieceACBSJH(HantoPlayerColor color, HantoPieceType type) {
		playerColor = color;
		this.type = type;
	}
	
	/**
	 * Construct a HantoPieceACBSJH from the HantoPiece interface
	 * 
	 * @param hp the interface to construct this object from
	 */
	public HantoPieceACBSJH(HantoPiece hp) {
		playerColor = hp.getColor();
		type = hp.getType();
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
	
	/** Gets the location of the piece.
	 * @return
	 */
	public HantoCoordinateACBSJH getLocation() {
		return location;
	}
	
	/** Sets the Location of the piece.
	 * @param newLocation
	 */
	public void setLocation(HantoCoordinateACBSJH newLocation) {
		location = newLocation;
	}

	/**
	 * Says if this piece is in a player's hand. This is denoted by the location
	 * of this piece being null.
	 * 
	 * @return if the piece is in a player's hand
	 */
	public boolean isInHand()
	{
		return location == null;
	}
	
	/***
	 * Returns a string with the type location and color of this piece
	 */
	public String toString()
	{
		return playerColor.toString() + " " + type.toString() + " @ " + 
				((location != null) ? location.toString() : "Hand");
	}
}
