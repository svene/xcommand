package org.collage.template;

import java.io.InputStream;
import org.collage.dom.creationhandler.DefaultDomNodeCreationHandlerInitializer;
import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.independent.IJavaTemplateCmdCV;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;
import org.xcommand.template.parser.IParserCV;

public class JavassistTemplateCompiler {

    private final ICommand traverser;

    public JavassistTemplateCompiler(ICommand traverser) {
        this.traverser = traverser;
    }

    public ICommand newTemplateCommandFromString(String aString) {
        return newTemplateCommand(new TemplateSource(aString));
    }

    public ICommand newTemplateCommand(TemplateSource aTemplateSource) {
        // Compile template:
        return TCP.get(() -> {
            domNodeCreationHandlerCV.setProduceJavaSource(Boolean.TRUE);
            // !!		ctx.putAll(aTemplateSource.getContext());
            new DefaultDomNodeCreationHandlerInitializer().execute();

            parserCV.setInputStream(aTemplateSource.getInputStream());
            new TemplateCompiler().execute();
            // Now we have the root node: `TreeNodeCV.getTreeNode(ctx)'
            // Use String based text evaluation. Since this is only for template compilation and not template usage
            // it is not performance/memory relevant and thus OK:
            stringHandlerCV.setString("");
            traverser.execute();
            // Return dynamically (by javassist) created template command (ICommand)
            return javaTemplateCmdCV.getTemplateComand();
        });
    }

    public ICommand newTemplateCommandFromStream(InputStream aInputStream) {
        return newTemplateCommand(new TemplateSource(aInputStream));
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IParserCV parserCV = dbp.newBeanForInterface(IParserCV.class);
    IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
    IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
    IJavaTemplateCmdCV javaTemplateCmdCV = dbp.newBeanForInterface(IJavaTemplateCmdCV.class);
}
