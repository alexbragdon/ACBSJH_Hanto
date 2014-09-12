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

public class HantoCoordinateACBSJH implements HantoCoordinate {

	private int X, Y;
	
	public HantoCoordinateACBSJH(int Xin, int Yin) {
		X = Xin;
		Y = Yin;
	}
	
	@Override
	public int getX() {
		return X;
	}

	@Override
	public int getY() {
		return Y;
	}

}
