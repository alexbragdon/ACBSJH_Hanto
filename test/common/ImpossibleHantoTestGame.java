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
package common;

import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;

/**
 * @author Sean
 *
 */
public interface ImpossibleHantoTestGame extends HantoTestGame {
	
	public void clearPieces();
	
	public void addPiecesToHand(HantoPieceType[] newPieces, HantoPlayerColor player);

}
