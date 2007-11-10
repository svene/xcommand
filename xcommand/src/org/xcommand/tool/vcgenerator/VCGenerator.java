package org.xcommand.tool.vcgenerator;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.File;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.*;

/**
 * ViewContext Generator
 */
public class VCGenerator
{
	public static void main(String[] args) throws Exception
	{
		new VCGenerator().execute(args);
	}

	private void execute(String[] aArgs) throws Exception
	{
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("."));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		Map root = new HashMap();
		MetaCV mcv = new WebCVMetaCV();
		root.put("mcv", mcv);
		Template temp = cfg.getTemplate("vc.ftl");
		Writer out = new OutputStreamWriter(System.out);
		temp.process(root, out);
		out.flush();
	}
}
