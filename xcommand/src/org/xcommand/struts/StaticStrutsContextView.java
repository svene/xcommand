package org.xcommand.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;

public class StaticStrutsContextView
{

// --- Access ---

	public static ActionForward getActionForward(Map aContext)
	{
		return (ActionForward) aContext.get(StaticStrutsContextView.KEY_ACTION_FORWARD);
	}

	public static ActionMapping getActionMapping(Map aContext)
	{
		return (ActionMapping) aContext.get(StaticStrutsContextView.KEY_ACTION_MAPPING);
	}

	public static ActionForm getActionForm(Map aContext)
	{
		return (ActionForm) aContext.get(StaticStrutsContextView.KEY_ACTION_FORM);
	}

// --- Setting ---

	public static void setActionForward(Map aContext, ActionForward aActionForward)
	{
		aContext.put(StaticStrutsContextView.KEY_ACTION_FORWARD, aActionForward);
	}

	public static void setActionMapping(Map aContext, ActionMapping aActionMapping)
	{
		aContext.put(StaticStrutsContextView.KEY_ACTION_MAPPING, aActionMapping);
	}

	public static void setActionForm(Map aContext, ActionForm aActionForm)
	{
		aContext.put(StaticStrutsContextView.KEY_ACTION_FORM, aActionForm);
	}

// --- Implementation ---

	private static final String KEY_ACTION_FORWARD = "actionforward";
	private static final String KEY_ACTION_MAPPING = "actionmapping";
	private static final String KEY_ACTION_FORM = "actionform";
}
