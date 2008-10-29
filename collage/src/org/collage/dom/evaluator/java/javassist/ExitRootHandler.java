package org.collage.dom.evaluator.java.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.TemplateSource;
import org.collage.template.TextTemplateCompiler;
import org.xcommand.core.*;

import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

class ExitRootHandler implements ICommand
{
	public void execute()
	{
		CtClass cc = null;
		try
		{
			ClassPool pool = ClassPool.getDefault();
			String className = getClassName();

			CtClass ccXcommand = pool.get("org.xcommand.core.ICommand");
			cc = pool.makeClass(className);
			cc.addInterface(ccXcommand);

			// Add method 'appendVar()':
			TCP.pushContext(new HashMap());
			addMethod(cc, "org/collage/dom/evaluator/java/javassist/appendvar.txt");
			TCP.popContext();

			// Add method 'execute()':
			StringBuffer sb = (StringBuffer) TCP.getContext().get("methodbody");
//System.out.println("**methodbody\n" + sb.toString());
			TCP.pushContext(new HashMap());
			TCP.getContext().put("execute_method_body", sb.toString());
			addMethod(cc, "org/collage/dom/evaluator/java/javassist/execute_method.txt");
			TCP.popContext();

			Class clazz = cc.toClass();
			TCP.getContext().put("clazz", clazz);

			// Create instance and put it on context:
			Object obj = clazz.newInstance();
			javaTemplateCmdCV.setTemplateComand((ICommand) obj);
		}
		catch (Exception e)
		{
			if (cc != null)
			{
				System.err.println("class source:\n" + cc.toString());
			}
			throw new RuntimeException(e);
		}
	}

	private void addMethod(CtClass aCtClass, String aFilename) throws Exception
	{
		domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(aFilename);

		ICommand cmd = new TextTemplateCompiler().newTemplateCommand(new TemplateSource(is));
		cmd.execute();
		String s = stringHandlerCV.getString();
		is.close();
//		System.out.println("methodstring: " + s);
		CtMethod ctm = CtNewMethod.make(s, aCtClass);
		aCtClass.addMethod(ctm);
	}

	private String getClassName()
	{
		Calendar cal = new GregorianCalendar();
		String s = "";
		s += "_" + cal.get(Calendar.YEAR);
		s += "-" + cal.get(Calendar.MONTH);
		s += "-" + cal.get(Calendar.DAY_OF_MONTH);
		s += "_" + cal.get(Calendar.HOUR_OF_DAY);
		s += "-" + cal.get(Calendar.MINUTE);
		s += "-" + cal.get(Calendar.SECOND);
		s += "-" + cal.get(Calendar.MILLISECOND);
		String className = "JavassistTemplate" + s;
//		System.out.println(className);
		return className;
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IDomNodeCreationHandlerCV domNodeCreationHandlerCV = (IDomNodeCreationHandlerCV) dbp.newBeanForInterface(
		IDomNodeCreationHandlerCV.class);
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
	IJavaTemplateCmdCV javaTemplateCmdCV = (IJavaTemplateCmdCV) dbp.newBeanForInterface(IJavaTemplateCmdCV.class);
}
