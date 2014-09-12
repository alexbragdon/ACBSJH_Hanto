import static org.junit.Assert.*;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

import org.junit.Before;
import org.junit.Test;


public class AlphaHantoTests {
	
	HantoCoordinateACBSJH testCoordinate1;
	HantoPieceACBSJH testPieceBlue;
	HantoPieceACBSJH testPieceRed;
	
	
	@Before
	public void setup()
	{
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
}
