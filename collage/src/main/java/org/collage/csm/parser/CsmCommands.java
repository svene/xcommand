package org.collage.csm.parser;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.template.parser.IParserCV;

public class CsmCommands {
	public static ICommand appendJavaCodeCommand = () -> {
		var sb = CsmCommands.parserCV.getStringBuffer();
		var value = CsmCommands.parserCV.getValue();
		sb.append(value);
	};

	public static ICommand appendEolCommand = () -> {
		StringBuffer sb = CsmCommands.parserCV.getStringBuffer();
//		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");

		Boolean javaMode = CsmCommands.domNodeCreationHandlerCV.getProduceJavaSource();
		if (javaMode == Boolean.TRUE) {
//			sb.append("*PJS*");
			sb.append('\\');
			sb.append('n');
		} else {
//			sb.append("!PJS!");
			sb.append("\n");
		}
	};

	public static ICommand appendTextCommand = () -> {
		StringBuffer sb = CsmCommands.parserCV.getStringBuffer();
		String value = CsmCommands.parserCV.getValue();
//		System.out.println("*** TextTokenHandler.execute: appending '" + value + "'");
		sb.append(value);
	};

	public static ICommand createVariableDomNodeCommand = () -> {
		var value = CsmCommands.parserCV.getValue();
		CsmCommands.domNodeCreationHandlerCV.setValue(value);
		CsmCommands.domNodeCreationHandlerCV.getCreateVariableNodeRequestNotifier().execute();
	};

	/**
	 * Commands flushing buffered text and creating associated Text-DOM-Node
	 */
	public static ICommand flushJavaCommand = () -> {
		var sb = CsmCommands.parserCV.getStringBuffer();
		var s = sb.toString();
		if (s.length() > 0) {
			CsmCommands.domNodeCreationHandlerCV.setValue(s);
			CsmCommands.domNodeCreationHandlerCV.getCreateJavaNodeRequestNotifier().execute();
		}
	};

	/**
	 * Commands flushing buffered text and creating associated Text-DOM-Node
	 */
	public static ICommand flushTextCommand = () -> {
		// Get String from Stringbuffer:
		var sb = CsmCommands.parserCV.getStringBuffer();
		var s = sb.toString();

		// Create a Text-DOM-Node:
		CsmCommands.domNodeCreationHandlerCV.setValue(s);
		CsmCommands.domNodeCreationHandlerCV.getCreateTextNodeRequestNotifier().execute();
	};

	public static ICommand startJavaCommand = () -> {
		CsmCommands.parserCV.setStringBuffer(new StringBuffer());
	};

	public static ICommand startTextCommand = () -> {
		CsmCommands.parserCV.setStringBuffer(new StringBuffer());
	};

	private static final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private static final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
	private static final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
}

