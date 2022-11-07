package com.spring.javawebserver.webserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebCalculatorApplicationTests {

	@Test
	void contextLoads() {
	}

}

class AppTest {

	@Test
	public void testingValidInput() {

		//Testing valid inputs
		assertTrue(App.validInput("123+235"));
		assertTrue(App.validInput("1.13-0.85"));
		assertTrue(App.validInput("5*5"));
		assertTrue(App.validInput("10/2"));
		assertTrue(App.validInput("2^2"));
		assertTrue(App.validInput("-5+10"));
		assertTrue(App.validInput("5--3"));
		assertTrue(App.validInput("-5*-5"));

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

	// testing invalid inputs
	@Test
	public void testingValidInputs(){
		//Testing invalid inputs
		assertFalse(App.validInput("^43"));
		assertFalse(App.validInput("52+"));
		assertFalse(App.validInput("/5"));
		assertFalse(App.validInput("*23"));
	}

	//Testing double operators
	@Test
	public void testingDoubleOperators() {
		assertFalse(App.validInput("5++6"));
		assertFalse(App.validInput("5*+6"));
		assertFalse(App.validInput("5//6"));
		assertFalse(App.validInput("5+^6"));
	}
}

