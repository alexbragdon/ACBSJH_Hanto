import static org.junit.Assert.*;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.alpha.AlphaHantoGame;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceAlphaACBSJH;

import org.junit.Before;
import org.junit.Test;

import hanto.common.*;
import hanto.studentACBSJH.*;

public class AlphaHantoTests {

	HantoGame alphaHantoGame;
	
	@Before
	public void setup()
	{
		alphaHantoGame  = new AlphaHantoGame();
	}
	
	@Test
	public void test() {
		HantoCoordinateACBSJH testCoordinate1 = new HantoCoordinateACBSJH(1,2);
		HantoPieceAlphaACBSJH testPieceRed = new HantoPieceAlphaACBSJH(HantoPlayerColor.RED);
		HantoPieceAlphaACBSJH testPieceBlue = new HantoPieceAlphaACBSJH(HantoPlayerColor.BLUE);
		
		assertEquals(1, testCoordinate1.getX());
		assertEquals(2, testCoordinate1.getY());
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
