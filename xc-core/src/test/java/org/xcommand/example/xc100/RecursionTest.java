package org.xcommand.example.xc100;

import org.junit.jupiter.api.Test;
import org.xcommand.core.Factory;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class RecursionTest {
	private List<String> list;

	@Test
	void example1() {
		list = new ArrayList<>();
		Map<String, Object> ctx = new HashMap<>();
		ctx.put("name", "Sven");
		String level = "";
		ctx.put("level", level);
		execute(ctx);
		assertThat(list).isEqualTo(Arrays.asList(
			"before call : name=Sven, level=",
			"before call : name=Sven1, level=#",
			"before call : name=Sven12, level=##",
			"before call : name=Sven123, level=###",
			"before call : name=Sven1234, level=####",
			"before call : name=Sven12345, level=#####",
			"after call  : name=Sven12345, level=#####",
			"after call  : name=Sven1234, level=#####",
			"after call  : name=Sven123, level=####",
			"after call  : name=Sven12, level=###",
			"after call  : name=Sven1, level=##",
			"after call  : name=Sven, level=#"
		));
	}

	private void execute(Map<String, Object> aCtx)
	{
		String name = (String) aCtx.get("name");
		String level = (String) aCtx.get("level");
		list.add("before call : name=%s, level=%s".formatted(name, level));

		if (level.length() < 5)
		{
			Map<String, Object> ctx = Factory.newInheritableMap(aCtx);
			level += "#";
			ctx.put("name", ctx.get("name") + String.valueOf(level.length()));
			ctx.put("level", level);
			execute(ctx);
		}
		list.add("after call  : name=%s, level=%s".formatted(name, level));
	}
}
