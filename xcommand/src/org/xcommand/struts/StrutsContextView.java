package org.xcommand.struts;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;

/**
 * Dynamic versin of StaticStrutsContextView
 */
public class StrutsContextView implements IStrutsContextView
{

// --- Initialization ---

	public StrutsContextView(Map aContext)
	{
		context = aContext;
	}

// --- Access ---

	public ActionForward getActionForward()
	{
		return StaticStrutsContextView.getActionForward(context);
	}

	public ActionMapping getActionMapping()
	{
		return StaticStrutsContextView.getActionMapping(context);
	}

	public ActionForm getActionForm()
	{
		return StaticStrutsContextView.getActionForm(context);
	}

// --- Setting ---

	public void setActionForward(ActionForward aActionForward)
	{
		StaticStrutsContextView.setActionForward(context, aActionForward);
	}

	public void setActionMapping(ActionMapping aActionMapping)
	{
		StaticStrutsContextView.setActionMapping(context, aActionMapping);
	}

	public void setActionForm(ActionForm aActionForm)
	{
		StaticStrutsContextView.setActionForm(context, aActionForm);
	}

// --- Implementation ---

	private Map context;

}
