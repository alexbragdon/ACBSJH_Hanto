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

import hanto.common.HantoCoordinate;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Implementation of HantoCoordinate interface. Contains an
 * x and y coordinate on a hex grid.
 * 
 * @author alexbragdon
 *
 */
public class HantoCoordinateACBSJH implements HantoCoordinate {

	/**
	 * x and y coordinates on the hex grid of Hanto
	 */
	private int X, Y;
	
	/**
	 * Creates a new coordinate and given location.
	 * 
	 * @param Xin
	 * @param Yin
	 */
	public HantoCoordinateACBSJH(int Xin, int Yin) {
		X = Xin;
		Y = Yin;
	}

	/**
	 * Converts a HantoCoordinate to a HantoCoordinateACBSJH.
	 * 
	 * @param coordinate
	 */
	public HantoCoordinateACBSJH(HantoCoordinate coordinate)
	{
		this(coordinate.getX(), coordinate.getY());
	}
	

	/** (non-Javadoc)
	 * @see hanto.common.HantoCoordinate#getX()
	 */
	@Override
	public int getX() {
		return X;
	}

	/** (non-Javadoc)
	 * @see hanto.common.HantoCoordinate#getY()
	 */
	@Override
	public int getY() {
		return Y;
	}
	
	/** If two coordinates are equal.
	 * @param coordinate
	 * @return a string.
	 */
	public boolean equals(HantoCoordinate coordinate)
	{
		return (coordinate != null) ? coordinate.getX() == X && coordinate.getY() == Y : false;
	}

	/**
	 * Returns an ordered pair for this location in a string
	 */
	public String toString()
	{
		return "(" + X + ", " + Y + ")";
	}
	
	/** Checks if a given coordinate is adjacent to this one.
	 * @param to the coordinate to check adjacency with
	 * @return true if to is adjacent to this
	 */
	public boolean isAdjacent(HantoCoordinate to) {
		int toX = to.getX();
		int toY = to.getY();
		
		boolean adjacency =
			((toX == X) && (toY == Y + 1)) ||
			((toX == X + 1) && (toY == Y)) ||
			((toX == X + 1) && (toY == Y - 1)) ||
			((toX == X) && (toY == Y - 1)) ||
			((toX == X - 1) && (toY == Y)) ||
			((toX == X - 1) && (toY == Y + 1));
		
		return adjacency;
	}
	
	/**
	 * Checks if another location is in a straight line from this location in one of six directions
	 * 
	 * @param to the location to check against, so the line is from this to 'to'
	 * @return if the line between locations is straight in one of six directions
	 */
	public boolean isStraightLine(HantoCoordinate to) {
		if (X - to.getX() == 0 || Y - to.getY() == 0) {
			return true;
		} else if (((X - to.getX()) / (Y - to.getY())) == -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Computes the distance between two coordinates.
	 * @param ACBSJHTo
	 * @return distance between this and ACBSJHTo
	 */
	public int getDistance(HantoCoordinateACBSJH ACBSJHTo) {
		int toX = ACBSJHTo.getX();
		int toY = ACBSJHTo.getY();
		
		return ((Math.abs(X - toX) + Math.abs(Y - toY)
				+ Math.abs(X + Y - toX - toY)) / 2);
	}

	/**
	 * A function to get all coor adjacent coordinates .
	 * @return Collection<HantoCoordinateACBSJH>
	 */
	public Collection<HantoCoordinateACBSJH> getAllAdjacentCoordinates() 
	{
		Collection<HantoCoordinateACBSJH> listOfMoveLocations = new ArrayList<HantoCoordinateACBSJH>();
		listOfMoveLocations.add(new HantoCoordinateACBSJH(X, Y + 1));
		listOfMoveLocations.add(new HantoCoordinateACBSJH(X + 1, Y));
		listOfMoveLocations.add(new HantoCoordinateACBSJH(X + 1, Y - 1));
		listOfMoveLocations.add(new HantoCoordinateACBSJH(X, Y - 1));
		listOfMoveLocations.add(new HantoCoordinateACBSJH(X - 1, Y));
		listOfMoveLocations.add(new HantoCoordinateACBSJH(X - 1, Y + 1));
		return listOfMoveLocations;
		
	}
}
