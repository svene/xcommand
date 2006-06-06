package org.xcommand.struts;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: se
 * Date: 30.03.2006
 * Time: 17:48:47
 * To change this template use File | Settings | File Templates.
 */
public interface IStrutsContextView
{
	ActionForward getActionForward();
	ActionMapping getActionMapping();
	ActionForm getActionForm();
	void setActionForward(ActionForward aActionForward);
	void setActionMapping(ActionMapping aActionMapping);
	void setActionForm(ActionForm aActionForm);
}
