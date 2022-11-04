package assignment;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.Assert.*;
//import org.junit.Test;
import org.junit.jupiter.api.Test;

public class AppTest {
	
	@Test
	public void tests() {
		
		App testA = new App();
		assertEquals("2", testA.calculate("1+1"));
		
		App testB = new App();
		assertEquals("3", testB.calculate("1+1+1"));
		
		App testC = new App();
		assertEquals("52", testC.calculate("35+17"));
		
		App testD = new App();
		assertEquals("10593", testD.calculate("244+591+9758"));
		
		App testE = new App();
		assertEquals("2", testE.calculate("1*2"));
		
		App testF = new App();
		assertEquals("6", testF.calculate("1*2*3"));
		
		App testG = new App();
		assertEquals("2184", testG.calculate("52*42"));
		
		App testH = new App();
		assertEquals("87972792", testH.calculate("1643*552*97"));
		
		App testI = new App();
		assertEquals("3", testI.calculate("1+1*2"));
		
		App testJ = new App();
		assertEquals("3", testJ.calculate("1*2+1"));
		
		App testK = new App();
		assertEquals("4", testK.calculate("1+2*1+1"));
		
		App testL = new App();
		assertEquals("-11015", testL.calculate("12435-2345*10"));
		
		App testM = new App();
		assertEquals("1", testM.calculate("2-1"));
		
		App testN = new App();
		assertEquals("0", testN.calculate("3-2-1"));
		
		App testO = new App();
		assertEquals("-85219", testO.calculate("1256-86475"));
		
		App testP = new App();
		assertEquals("-76396", testP.calculate("12435+34569-12345*10+50"));

		App testQ = new App();
		assertEquals("59338", testQ.calculate("1027*62-7920+32*112"));
		
		App testR = new App();
		assertEquals("409486", testR.calculate("234+552*1124-211244+48"));
		
		App testS = new App();
		assertEquals("-279313620", testS.calculate("1947-1193833*234+41355"));
		
		App testT = new App();
		assertEquals("79022959", testT.calculate("41433-234+55232*130*11"));
		
		App testU = new App();
		assertEquals("This is not a valid expression. A valid input contains only integers and operands such as +, - and *", testU.calculate("134gw-234234+234i*nsjdn8"));
		
		App testV = new App();
		assertEquals("This is not a valid expression. A valid input contains only integers and operands such as +, - and *", testV.calculate("gs1531-fglkne234524+hyug*134234*bhisb1717"));
		
	}

	@Test
	public void testingValidInput() {
		
		boolean input1 = App.validInput("12+423-413");
		assertEquals(true, input1);

		boolean input2 = App.validInput("123++62-test");
		assertEquals(false, input2);
		
		boolean input3 = App.validInput("4412*31290-1143211");
		assertEquals(true, input3);

		boolean input4 = App.validInput("-2342-1432+22");
		assertEquals(false, input4);
		
		boolean input5 = App.validInput("24536*123231*2342");
		assertEquals(true, input5);

		boolean input6 = App.validInput("+1232++1434/testing");
		assertEquals(false, input6);
		
		boolean input7 = App.validInput("11*111-111+111111");
		assertEquals(true, input7);

		boolean input8 = App.validInput("end");
		assertEquals(false, input8);
		
	}

	@Test
	public void testingConvertToPostfix() {
		
		String input1 = App.convertToPostfix("12+423-413");
		String expected1 = "12 423 + 413 - ";
		assertEquals(expected1, input1);

		String input2 = App.convertToPostfix("12+423*413-7");
		String expected2 = "12 423 413 * + 7 - ";
		assertEquals(expected2, input2);
		
		String input3 = App.convertToPostfix("11*111-111+111111");
		String expected3 = "11 111 * 111 - 111111 + ";
		assertEquals(expected3, input3);
		
		String input4 = App.convertToPostfix("4412*31290-1143211");
		String expected4 = "4412 31290 * 1143211 - ";
		assertEquals(expected4, input4);
		
		String input5 = App.convertToPostfix("12313*1414*1231*10043");
		String expected5 = "12313 1414 * 1231 * 10043 * ";
		assertEquals(expected5, input5);
		
		String input6 = App.convertToPostfix("142-4133+214*4132");
		String expected6 = "142 4133 - 214 4132 * + ";
		assertEquals(expected6, input6);
		
		String input7 = App.convertToPostfix("8852-1198224+1898928*1983");
		String expected7 = "8852 1198224 - 1898928 1983 * + ";
		assertEquals(expected7, input7);
		
		String input8 = App.convertToPostfix("979983-1123*1111+1434344");
		String expected8 = "979983 1123 1111 * - 1434344 + ";
		assertEquals(expected8, input8);
		
	}

	@Test
	public void testingPrecedence() {
		
		boolean precedence1 = App.hasPrecedence('*', '-');
		assertEquals(false, precedence1);

		boolean precedence2 = App.hasPrecedence('+', '*');
		assertEquals(true, precedence2);
		
		boolean precedence3 = App.hasPrecedence('-', '*');
		assertEquals(true, precedence3);
		
		boolean precedence4 = App.hasPrecedence('*', '+');
		assertEquals(false, precedence4);
		
	}

	@Test
	public void testingEvaluateExpression() {
		
		String input1 = App.evaluateExpression("12 423 + 413 - ");
		String expected1 = "22";
		assertEquals(expected1, input1);

		String input2 = App.evaluateExpression("12 423 413 * + 7 - ");
		String expected2 = "174704";
		assertEquals(expected2, input2);
		
		String input3 = App.evaluateExpression("11 111 * 111 - 111111 + ");
		String expected3 = "112221";
		assertEquals(expected3, input3);
		
		String input4 = App.evaluateExpression("4412 31290 * 1143211 - ");
		String expected4 = "136908269";
		assertEquals(expected4, input4);
		
		String input5 = App.evaluateExpression("1213 9912 + 1288 109892 * - ");
		String expected5 = "-141529771";
		assertEquals(expected5, input5);
		
		String input6 = App.evaluateExpression("142 4133 - 214 4132 * + ");
		String expected6 = "880257";
		assertEquals(expected6, input6);
		
		String input7 = App.evaluateExpression("121411 3112 - 892 - 1898 11111 * + ");
		String expected7 = "21206085";
		assertEquals(expected7, input7);
		
		String input8 = App.evaluateExpression("979983 1123 1111 * - 1434344 + ");
		String expected8 = "1166674";
		assertEquals(expected8, input8);
		
	}
} 