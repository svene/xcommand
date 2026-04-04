package org.xcommand.template.jst;

import org.xcommand.pattern.observer.INotifier;
import org.xcommand.pattern.observer.BasicNotifier;

public class JSTNotifiers {

	public final INotifier startNotifier = new BasicNotifier();
	public final INotifier eolInCommentNotifier = new BasicNotifier();
	public final INotifier eolInJavaNotifier = new BasicNotifier();
	public final INotifier eofNotifier = new BasicNotifier();

	public final INotifier commentStartNotifier = new BasicNotifier();
	public final INotifier commentTextNotifier = new BasicNotifier();
	public final INotifier commentEndNotifier = new BasicNotifier();

	public final INotifier javaTextNotifier = new BasicNotifier();
	public final INotifier javaVarStartNotifier = new BasicNotifier();
	public final INotifier javaVarNotifier = new BasicNotifier();
	public final INotifier javaVarEndNotifier = new BasicNotifier();
}
