package AlphaHantoTests;
import static org.junit.Assert.*;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.alpha.AlphaHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;
import org.junit.Before;
import org.junit.Test;

import hanto.common.*;
import hanto.studentACBSJH.*;

public class AlphaHantoTests {

	HantoGame alphaHantoGame;
	HantoCoordinateACBSJH testCoordinate1;
	HantoPieceACBSJH testPieceBlue;
	HantoPieceACBSJH testPieceRed;
	
	
	@Before
	public void setup()
	{
		alphaHantoGame  = new AlphaHantoGame();
		testCoordinate1 = new HantoCoordinateACBSJH(1,2);
		testPieceRed = new HantoPieceACBSJH(HantoPlayerColor.RED);
		testPieceBlue = new HantoPieceACBSJH(HantoPlayerColor.BLUE);
	}
	
	@Test
	public void testGettersForCoordinates() {
		assertEquals(1, testCoordinate1.getX());
		assertEquals(2, testCoordinate1.getY());
	}

	@Test
	public void testPieces() {
		assertEquals(HantoPlayerColor.BLUE, testPieceBlue.getColor());
		assertEquals("Butterfly", testPieceRed.getType().getPrintableName());
	}
	
	/**
	 * 		AlphaHantoGame Tests
	 */
	
	@Test
	public void emptyCoordinateTest()
	{
		assertEquals(null, alphaHantoGame.getPieceAt(new HantoCoordinateACBSJH(0, 0)));
	}
	
	@Test
	public void correctFirstBlueMove()
	{
		//alphaHantoGame.makeMove(HantoPieceType., from, to)
	}

}
