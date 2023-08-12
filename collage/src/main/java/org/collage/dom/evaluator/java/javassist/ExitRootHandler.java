package org.collage.dom.evaluator.java.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import lombok.extern.slf4j.Slf4j;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;

import java.lang.invoke.MethodHandles;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

@Slf4j
class ExitRootHandler implements ICommand {
	@Override
	public void execute() {
		CtClass cc = null;
		try {
			var pool = ClassPool.getDefault();
			String className = getClassName();

			var ccXcommand = pool.get("org.xcommand.core.ICommand");
			cc = pool.makeClass(getClass().getPackage().getName() + "." + className);
			cc.addInterface(ccXcommand);

			// Add method 'appendVar()':
			TCP.pushContext(new HashMap<>());
			addMethod(cc, appendVar);
			TCP.popContext();

			// Add method 'execute()':
			var sb = (StringBuffer) TCP.getContext().get("methodbody");
			log.debug("**methodbody\n" + sb.toString());
			TCP.pushContext(new HashMap<>());
			TCP.getContext().put("execute_method_body", sb.toString());
			addMethod(cc, executeMethod);
			TCP.popContext();

			var clazz = cc.toClass(MethodHandles.lookup());
			TCP.getContext().put("clazz", clazz);

			// Create instance and put it on context:
			var obj = clazz.getDeclaredConstructor().newInstance();
			javaTemplateCmdCV.setTemplateComand((ICommand) obj);
		} catch (Exception e) {
			if (cc != null) {
				System.err.println("class source:\n" + cc);
			}
			throw new RuntimeException(e);
		}
	}

	private void addMethod(CtClass aCtClass, String content) throws javassist.CannotCompileException {
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		var cmd = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(content));
		cmd.execute();
		var s = stringHandlerCV.getString();
		log.debug("methodstring: " + s);
		var ctm = CtNewMethod.make(s, aCtClass);
		aCtClass.addMethod(ctm);

	}

	private String getClassName() {
		var cal = new GregorianCalendar();
		var s = "";
		s += "_" + cal.get(Calendar.YEAR);
		s += "-" + cal.get(Calendar.MONTH);
		s += "-" + cal.get(Calendar.DAY_OF_MONTH);
		s += "_" + cal.get(Calendar.HOUR_OF_DAY);
		s += "-" + cal.get(Calendar.MINUTE);
		s += "-" + cal.get(Calendar.SECOND);
		s += "-" + cal.get(Calendar.MILLISECOND);
		return "JavassistTemplate" + s;
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
	IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
	IJavaTemplateCmdCV javaTemplateCmdCV = dbp.newBeanForInterface(IJavaTemplateCmdCV.class);

	private final String appendVar = """
		private static final void appendVar(java.util.Map aCtx, String aVarName, java.io.Writer aWriter) {
			Object obj = org.xcommand.core.TCP.getContext().get(aVarName);
			if (obj != null)
			{
				aWriter.write(obj.toString());
			}
			else
			{
				aWriter.write("$\\{");
				aWriter.write(aVarName);
				aWriter.write("}");
			}
		}
		""";

	private final String executeMethod = """
		public void execute()
		{
			java.io.Writer _writer = (java.io.Writer)org.xcommand.core.TCP.getContext().get("writer");
			if (_writer == null) _writer = new java.io.PrintWriter(System.out);
		
			${execute_method_body}
		
			_writer.flush();
		}
		""";
}
