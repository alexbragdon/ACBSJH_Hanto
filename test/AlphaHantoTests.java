import static org.junit.Assert.*;
import hanto.common.HantoPlayerColor;
import hanto.studentACBSJH.common.HantoCoordinateACBSJH;
import hanto.studentACBSJH.common.HantoPieceACBSJH;

import org.junit.Test;


public class AlphaHantoTests {

	
	
	@before
	public void setup()
	{
		
	}
	
	@Test
	public void test() {
		HantoCoordinateACBSJH testCoordinate1 = new HantoCoordinateACBSJH(1,2);
		HantoPieceACBSJH testPieceRed = new HantoPieceACBSJH(HantoPlayerColor.RED);
		HantoPieceACBSJH testPieceBlue = new HantoPieceACBSJH(HantoPlayerColor.BLUE);
		
		assertEquals(1, testCoordinate1.getX());
		assertEquals(2, testCoordinate1.getY());
		assertEquals(HantoPlayerColor.BLUE, testPieceBlue.getColor());
		assertEquals("Butterfly", testPieceRed.getType().getPrintableName());
		
	}

}
