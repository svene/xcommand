package org.xcommand.example.xc100;

import org.junit.jupiter.api.Test;
import org.xcommand.core.Factory;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SubroutineTest {

	@Test
	void test1() {
		Map<String, String> ctx = new HashMap<>();
		ctx.put("name", "Sven");
		execute(ctx);
	}
	private void execute(Map<String, String> aCtx)
	{
		Map<String, String> ctx = Factory.newInheritableMap(aCtx);
		ctx.put("tmpString", "hi there");
		subExecute(ctx);
		assertThat(aCtx.get("name")).isEqualTo("Sven");
		assertThat(aCtx.get("tmpString")).isNull();
	}

	private void subExecute(Map<String, String> aCtx)
	{
		assertThat(aCtx.get("name")).isEqualTo("Sven");
		assertThat(aCtx.get("tmpString")).isEqualTo("hi there");
	}
}
