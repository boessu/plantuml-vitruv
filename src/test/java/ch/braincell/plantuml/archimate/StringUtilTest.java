package ch.braincell.plantuml.archimate;

import ch.braincell.plantuml.vitruv.StringUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class StringUtilTest {
	@Test
	void test() {
		String test = "**bold with more that 10 chars**\n* this is a bullet list and this line won't be wrapped.\n**If it is Bold from the beginning, it will be wrapped.**\n\n[[https:www.heise.de/newsticker this is a newsticker link and won't be wrapped.]] This is a **//~~string with some strange behaviour and~~//** some very long, long, long text and a //~~new line~~ in it//.";

		String[] expectedResult = {
				"**bold with more that 10 chars**",
				"* this is a bullet list and this line won't be wrapped.",
				"**If it is Bold from the**",
				"**beginning, it will be wrapped.**",
				"",
				"[[https:www.heise.de/newsticker this is a newsticker link and won't be wrapped.]]",
				"This is a **//~~string with some~~//**",
				"**//~~strange behaviour and~~//** some very",
				"long, long, long text and a //~~new~~//",
				"//~~line~~ in it//."
		};
		
		String result = StringUtil.wrap(test, 10, "\n");

		String[] lines = result.split("\n");
		assertArrayEquals(lines, expectedResult);
	}

}
