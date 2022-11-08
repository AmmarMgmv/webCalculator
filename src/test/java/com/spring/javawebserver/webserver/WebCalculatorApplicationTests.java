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
		
		//Testing invalid inputs
        	assertFalse(App.validInput("^43"));
        	assertFalse(App.validInput("52+"));
        	assertFalse(App.validInput("/5"));
        	assertFalse(App.validInput("*23"));

        	//Testing double operators
        	assertFalse(App.validInput("5++6"));
        	assertFalse(App.validInput("5*+6"));
        	assertFalse(App.validInput("5//6"));
        	assertFalse(App.validInput("5+^6"));

        	//Testing exp and log
        	assertTrue(App.validInput("log(2)"));
        	assertTrue(App.validInput("exp(5)"));
        	assertFalse(App.validInput("log()"));
        	assertFalse(App.validInput("exp()"));
        	assertFalse(App.validInput("log6"));
        	assertFalse(App.validInput("exp3"));
        	assertFalse(App.validInput("lg()"));
        	assertFalse(App.validInput("xp()"));

        	//Testing invalid characters
        	assertFalse(App.validInput("5..7-3"));
        	assertFalse(App.validInput("dsgkj"));
        	assertFalse(App.validInput("."));
        	assertFalse(App.validInput("()"));

        	//Testing invalid bracket amounts
        	assertFalse(App.validInput("((5+3)"));
        	assertFalse(App.validInput("(5+3))"));
        	assertFalse(App.validInput("3+((7-4)*5"));

	}

	@Test
	public void testingConvertToPostfix() {

		//Testing postfix int expressions
		String input = App.convertToPostfix("12+423-413");
		String expected = "12 423 + 413 - ";
		assertEquals(expected, input);

		input = App.convertToPostfix("12+423*413-7");
		expected = "12 423 413 * + 7 - ";
		assertEquals(expected, input);

		input = App.convertToPostfix("3+-5*((7+3/2)-1)");
		expected = "3 -5 7 3 2 / + 1 - * + ";
		assertEquals(expected, input);

		//Testing postfix float expressions
		input = App.convertToPostfix("12.342+423.12-413.87");
		expected = "12.342 423.12 + 413.87 - ";
		assertEquals(expected, input);

		input = App.convertToPostfix("12.123+423.123*413.123-7.123");
		expected = "12.123 423.123 413.123 * + 7.123 - ";
		assertEquals(expected, input);

		input = App.convertToPostfix("4.5^(7*2.3)");
		expected = "4.5 7 2.3 * ^ ";
		assertEquals(expected, input);

		//Testing with brackets
		input = App.convertToPostfix("12+(5-2)*5");
		expected = "12 5 2 - 5 * + ";
		assertEquals(expected, input);

		input = App.convertToPostfix("5/(6+3)*(4-2)");
		expected = "5 6 3 + / 4 2 - * ";
		assertEquals(expected, input);

		//Testing longer expressions
		input = App.convertToPostfix("5-3+2*(6.3-5+1)--4/2+2^4*74-(20*6-7-9/3)-(235*4)+5-3+2*(6.3-5+1)--4/2+2^4*74-(20*6-7-9/3)-(235*4)");
		expected = "5 3 - 2 6.3 5 - 1 + * + -4 2 / - 2 4 ^ 74 * + 20 6 * 7 - 9 3 / - - 235 4 * - 5 + 3 - 2 6.3 5 - 1 + * + -4 2 / - 2 4 ^ 74 * + 20 6 * 7 - 9 3 / - - 235 4 * - ";
		assertEquals(expected, input);

		input = App.convertToPostfix("5.234-2.7334*9.75-(1003.76+75.836/25.834)+72*(2.43+(4+(64.43+4)))+5.234-2.7334*9.75-(1003.76+75.836/25.834)+72*(2.43+(4+(64.43+4)))");
		expected = "5.234 2.7334 9.75 * - 1003.76 75.836 25.834 / + - 72 2.43 4 64.43 4 + + + * + 5.234 + 2.7334 9.75 * - 1003.76 75.836 25.834 / + - 72 2.43 4 64.43 4 + + + * + ";
		assertEquals(expected, input);
		
		//Testing log
		input = App.convertToPostfix("5+log(2)");
		expected = "5 0.6931471805599453 + ";
		assertEquals(expected, input);

		input = App.convertToPostfix("5+log(2.34+3)");
		expected = "5 1.6752256529721035 + ";
		assertEquals(expected, input);

		//Testing exp
		input = App.convertToPostfix("5+exp(2)*3");
		expected = "5 7.38905609893065 3 * + ";
		assertEquals(expected, input);

		input = App.convertToPostfix("5+exp(2.34+3)-7");
		expected = "5 208.51271028909628 + 7 - ";
		assertEquals(expected, input);
	}

	@Test
	public void testingEvaluateExpression() {
		//Testing postfix int expressions
		String input = App.evaluateExpression("12 423 + 413 - ", "#.###");
		String expected = "22.0";
		assertEquals(expected, input);

		input = App.evaluateExpression("12 423 413 * + 7 - ", "#.###");
		expected = "174704.0";
		assertEquals(expected, input);

		input = App.evaluateExpression("3 -5 7 3 2 / + 1 - * + ", "#.###");
		expected = "-34.5";
		assertEquals(expected, input);

		//Testing postfix float expressions
		input = App.evaluateExpression("12.342 423.12 + 413.87 - ", "#.###");
		expected = "21.592";
		assertEquals(expected, input);

		input = App.evaluateExpression("12.123 423.123 413.123 * + 7.123 - ", "#.###");
		expected = "174806.83";
		assertEquals(expected, input);

		//Testing with brackets
		input = App.evaluateExpression("12 5 2 - 5 * + ", "#.###");
		expected = "27.0";
		assertEquals(expected, input);

		input = App.evaluateExpression("5 6 3 + / 4 2 - * ", "#.###");
		expected = "1.111";
		assertEquals(expected, input);

		//Testing longer expressions
		input = App.evaluateExpression("5 3 - 2 6.3 5 - 1 + * + -4 2 / - 2 4 ^ 74 * + 20 6 * 7 - 9 3 / - - 235 4 * - 5 + 3 - 2 6.3 5 - 1 + * + -4 2 / - 2 4 ^ 74 * + 20 6 * 7 - 9 3 / - - 235 4 * - ", "#.###");
		expected = "285.2";
		assertEquals(expected, input);

		input = App.evaluateExpression("5.234 2.7334 9.75 * - 1003.76 75.836 25.834 / + - 72 2.43 4 64.43 4 + + + * + 5.234 + 2.7334 9.75 * - 1003.76 75.836 25.834 / + - 72 2.43 4 64.43 4 + + + * + ", "#.###");
		expected = "8723.615";
		assertEquals(expected, input);

		//Testing log
		input = App.evaluateExpression("5 0.6931471805599453 + ", "#.###");
		expected = "5.693";
		assertEquals(expected, input);

		input = App.evaluateExpression("5 1.6752256529721035 + ", "#.###");
		expected = "6.675";
		assertEquals(expected, input);

		//Testing exp
		input = App.evaluateExpression("5 7.38905609893065 3 * + ", "#.###");
		expected = "27.167";
		assertEquals(expected, input);

		input = App.evaluateExpression("5 208.51271028909628 + 7 - ", "#.###");
		expected = "206.513";
		assertEquals(expected, input);
	}
	
	@Test
	public void testCalculate() {
		//Testing top level function
		assertEquals("358.0", App.calculate("123+235"));
		assertEquals("0.28", App.calculate("1.13-0.85"));
		assertEquals("25.0", App.calculate("5*5"));
		assertEquals("0.0", App.calculate("0*0"));
		assertEquals("0.0", App.calculate("0*8"));
		assertEquals("1.0", App.calculate("2^0"));
		assertEquals("1.0", App.calculate("2.5^0"));
		assertEquals("5.0", App.calculate("10/2"));
		assertEquals("4.0", App.calculate("2^2"));
		assertEquals("5.0", App.calculate("-5+10"));
		assertEquals("8.0", App.calculate("5--3"));
		assertEquals("25.0", App.calculate("-5*-5"));
		assertEquals("-391.65", App.calculate("52-(75.73+13)*10/2"));
		assertEquals("24.0", App.calculate("(2^3)*5-8*6/(1+2)"));

		// Testing log
		assertEquals("0.693", App.calculate("log(2)"));
		assertEquals("0.693", App.calculate("log(1+1)"));
		assertEquals("Log 0 is undefined!", App.calculate("log(0)"));
		assertEquals("-5.051", App.calculate("log(2*2)-4*log(3+2)"));
		assertEquals("-0.367", App.calculate("log(log(2))"));

		// Testing exp
		assertEquals("1.0", App.calculate("exp(0)"));
		assertEquals("2.718", App.calculate("exp(1)"));
		assertEquals("54.598", App.calculate("exp(2*2)"));
		assertEquals("30.786", App.calculate("3+5*exp(4.2)/(5+7)"));
		assertEquals("13306.745", App.calculate("exp(17-5)/12.231"));

		//Testing error case
		assertEquals("This is not a valid expression. Please try again and make sure there are no spaces in your expression!", App.calculate("sdjhgf"));
		assertEquals("This is not a valid expression. Please try again and make sure there are no spaces in your expression!", App.calculate("5 + 2 + 4 - 5"));
	}
}

