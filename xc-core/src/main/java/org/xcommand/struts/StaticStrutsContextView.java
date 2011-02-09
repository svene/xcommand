package org.xcommand.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xcommand.core.TCP;

public class StaticStrutsContextView
{

// --- Access ---

	public static ActionForward getActionForward()
	{
		return (ActionForward) TCP.getContext().get(StaticStrutsContextView.KEY_ACTION_FORWARD);
	}

	public static ActionMapping getActionMapping()
	{
		return (ActionMapping) TCP.getContext().get(StaticStrutsContextView.KEY_ACTION_MAPPING);
	}

	public static ActionForm getActionForm()
	{
		return (ActionForm) TCP.getContext().get(StaticStrutsContextView.KEY_ACTION_FORM);
	}

// --- Setting ---

	public static void setActionForward(ActionForward aActionForward)
	{
		TCP.getContext().put(StaticStrutsContextView.KEY_ACTION_FORWARD, aActionForward);
	}

	public static void setActionMapping(ActionMapping aActionMapping)
	{
		TCP.getContext().put(StaticStrutsContextView.KEY_ACTION_MAPPING, aActionMapping);
	}

	public static void setActionForm(ActionForm aActionForm)
	{
		TCP.getContext().put(StaticStrutsContextView.KEY_ACTION_FORM, aActionForm);
	}

// --- Implementation ---

	private static final String KEY_ACTION_FORWARD = "actionforward";
	private static final String KEY_ACTION_MAPPING = "actionmapping";
	private static final String KEY_ACTION_FORM = "actionform";
}
