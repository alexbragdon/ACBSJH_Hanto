import static org.junit.Assert.*;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.alpha.HantoCoordinateAlpha;
import hanto.studentACBSJH.alpha.HantoPieceAlpha;

import org.junit.Test;


public class AlphaHantoTests {

	@Test
	public void test() {
		HantoCoordinateAlpha testCoordinate1 = new HantoCoordinateAlpha(1,2);
		HantoPieceAlpha testPieceRed = new HantoPieceAlpha(HantoPlayerColor.RED);
		HantoPieceAlpha testPieceBlue = new HantoPieceAlpha(HantoPlayerColor.BLUE);
		
		assertEquals(1, testCoordinate1.getX());
		assertEquals(2, testCoordinate1.getY());
		assertEquals(HantoPlayerColor.BLUE, testPieceBlue.getColor());
		assertEquals("Butterfly", testPieceRed.getType().getPrintableName());
		
	}

}
