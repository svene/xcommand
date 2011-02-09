package org.xcommand.struts;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

public interface IStrutsCV
{
	ActionForward getActionForward();
	ActionMapping getActionMapping();
	ActionForm getActionForm();
	void setActionForward(ActionForward aActionForward);
	void setActionMapping(ActionMapping aActionMapping);
	void setActionForm(ActionForm aActionForm);
}
