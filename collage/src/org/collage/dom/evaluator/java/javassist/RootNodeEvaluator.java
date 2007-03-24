package org.collage.dom.evaluator.java.javassist;

import javassist.*;

import java.util.Map;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.io.*;

import org.xcommand.core.IXCommand;
import org.collage.dom.creationhandler.DomNodeCreationHandlerContextView;
import org.collage.dom.evaluator.text.TextTemplate;
import org.collage.dom.evaluator.java.javassist.JavassistContextView;
import org.collage.template.Template;
import org.collage.template.TemplateSource;

public class RootNodeEvaluator implements IXCommand
{
	public void execute(Map aCtx)
	{
		String mode = (String) aCtx.get("mode");
		if ("enter".equals(mode))
		{
			StringBuffer methodBody = new StringBuffer(1024);
			aCtx.put("methodbody", methodBody);
		}
		else if ("exit".equals(mode))
		{

			try
			{
				ClassPool pool = ClassPool.getDefault();
				String className = getClassName();

				CtClass ccXcommand = pool.get("org.xcommand.core.IXCommand");
				CtClass cc = pool.makeClass(className);
				cc.addInterface(ccXcommand);

				// Add method 'appendVar()':
				addMethod(cc, "appendvar.txt", new HashMap());

				// Add method 'execute()':
				StringBuffer sb = (StringBuffer) aCtx.get("methodbody");
				HashMap ctx = new HashMap(); ctx.put("execute_method_body", sb.toString());
				addMethod(cc, "execute_method.txt", ctx);

				Class clazz = cc.toClass();
				aCtx.put("clazz", clazz);

				// Create instance and put it on context:
				Object obj = clazz.newInstance();
				JavassistContextView.setTemplateInstance(aCtx, (IXCommand) obj);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}

	private void addMethod(CtClass aCtClass, String aFilename, Map aCtx) throws Exception
	{
		DomNodeCreationHandlerContextView.setProduceJavaSource(aCtx, Boolean.FALSE);
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(aFilename);
		Template template = new TextTemplate(new TemplateSource(is, aCtx));
		is.close();
		String s = template.getStringResult(aCtx);
//		System.out.println("methodstring: " + s);//!!
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


}
