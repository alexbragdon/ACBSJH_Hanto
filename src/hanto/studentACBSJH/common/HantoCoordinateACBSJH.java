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

/**
 * @author alexbragdon
 *
 */
public class HantoCoordinateACBSJH implements HantoCoordinate {

	private int X, Y;
	
	/**
	 * @param Xin
	 * @param Yin
	 */
	public HantoCoordinateACBSJH(int Xin, int Yin) {
		X = Xin;
		Y = Yin;
	}
	

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
	
	public boolean equals(HantoCoordinate coordinate)
	{
		return coordinate.getX() == X && coordinate.getY() == Y;
	}

}
