package org.xcommand.web;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.xcommand.core.ClassAndMethodKeyProvider;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.threadcontext.ITIn2OutCV;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegExpDispatcherCmdTest {
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
	private final ITIn2OutCV inoutCV = dbp.newBeanForInterface(ITIn2OutCV.class);

	@Test
	void dispatchingByRegex() {
		ICommand cmd1 = () -> inoutCV.setOutput("cmd1");
		ICommand cmd2 = () -> inoutCV.setOutput("cmd2");

		RegExpDispatcherCmd cmd = new RegExpDispatcherCmd();
		Map<String, ICommand> commands = new HashMap<>();
		commands.put(".*p1$", cmd1);
		commands.put(".*p2$", cmd2);
		cmd.setCommands(commands);

		MockHttpServletRequest request = new MockHttpServletRequest();
		webCV.setRequest(request);

		request.setRequestURI("/bla/p1");
		cmd.execute();
		assertEquals("cmd1", inoutCV.getOutput());

		request.setRequestURI("/bla/p2");
		cmd.execute();
		assertEquals("cmd2", inoutCV.getOutput());

	}
}
