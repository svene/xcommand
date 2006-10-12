package org.xcommand.misc.statemachine;

import org.xcommand.core.IXCommand;
import org.xcommand.core.ResultContextView;
import org.xcommand.misc.statemachine.IState;

import java.util.Map;
import java.util.Iterator;

public class StateMachine implements IXCommand
{

// --- Access ---

	public IXCommand getEventDispatcher()
	{
		return eventDispatcher;
	}

// --- Status report ---

	public boolean isFinished()
	{
		return currentState.getTransitions().size() > 0;
	}

// --- Setting ---

	public void setStartState(IState aStartState)
	{
		startState = aStartState;
	}

	public void setEventDispatcher(IXCommand aEventDispatcher)
	{
		eventDispatcher = aEventDispatcher;
	}

// --- Element change ---

	public void reset()
	{
		currentState = startState;
	}

// --- Processing ---

	/**
	 * Change state according to `aCtx'
	 */
	public void execute(Map aCtx)
	{
		// Stop execution if current state has no outgoing transitions. Otherwise continue:
		Transition t = nextTransitionToBeExecuted(aCtx);
		if (t == null)
		{
			// No transition needs to be executed at the moment.
			// So just execute current state:
			StateCV.setMode(aCtx, StateCV.EXECUTE);
			currentState.execute(aCtx);
		}
		else
		{
			// exit current state:
			StateCV.setMode(aCtx, StateCV.EXIT);
			currentState.execute(aCtx);

			// exit transition and change current state:
			t.execute(aCtx);
			currentState = t.getToState();

			// enter new current state:
			StateCV.setMode(aCtx, StateCV.ENTER);
			currentState.execute(aCtx);

			// execute current state:
			StateCV.setMode(aCtx, StateCV.EXECUTE);
			currentState.execute(aCtx);
		}
	}

// --- Implementation ---

	/**
	 * First transition of `currentState' for which it's entryCondition is True;
	 * null otherwise
	 */
	private Transition nextTransitionToBeExecuted(Map aCtx)
	{
		Transition result = null;
		if (currentState == null || currentState.getTransitions() == null)
		{
			return null;
		}
		Iterator it = currentState.getTransitions().iterator();
		while (result == null && it.hasNext())
		{
			Transition t = (Transition) it.next();
			IXCommand cmd = t.getEntryCondition();
			cmd.execute(aCtx);
//			System.out.println("ResultContextView.getResult(aCtx) = " + ResultContextView.getResult(aCtx));
			if (ResultContextView.getResult(aCtx) == Boolean.TRUE)
			{
//				System.out.println("->TRUE");
//				System.out.println("assigning t");
				result = t;
			}
			else
			{
//				System.out.println("->FALSE");
			}
		}
		return result;
	}

	private IState currentState;
	private IState startState;
	private IXCommand eventDispatcher;

}
