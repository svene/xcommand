package org.collage.parser;

import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;

public class TemplateNotifiers {

	public final INotifier startNotifier = new BasicNotifier();
	public final INotifier varStartNotifier = new BasicNotifier();
	public final INotifier varNameNotifier = new BasicNotifier();
	public final INotifier varEndNotifier = new BasicNotifier();
	public final INotifier eolNotifier = new BasicNotifier();
	public final INotifier javaStartNotifier = new BasicNotifier();
	public final INotifier javaEndNotifier = new BasicNotifier();
	public final INotifier javaCodeNotifier = new BasicNotifier();
	public final INotifier textNotifier = new BasicNotifier();
	public final INotifier eofNotifier = new BasicNotifier();
}
