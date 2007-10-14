package org.collage.dom.ast;

import java.util.Map;

public class VariableCV
{

// --- Access ---

	public static Variable getVariable(Map aCtx)
	{
		return (Variable) aCtx.get(KEY_VARIABLE);
	}

// --- Setting ---

	public static void setVariable(Map aCtx, Variable aVariable)
	{
		aCtx.put(KEY_VARIABLE, aVariable);
	}

// --- Implementation ---

	private static final String NS = "org.collage.dom.ast.VariableCV.";
	private static final String KEY_VARIABLE = NS + "VARIABLE";
}
