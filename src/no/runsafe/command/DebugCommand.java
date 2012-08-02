package no.runsafe.command;

import no.runsafe.framework.command.RunsafeCommand;
import no.runsafe.framework.server.player.RunsafePlayer;
import org.apache.commons.lang.StringUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class DebugCommand extends RunsafeCommand
{
	public DebugCommand()
	{
		super("debug", null);
	}

	@Override
	public String requiredPermission()
	{
		return "runsafe.debug.unsafe";
	}

	@Override
	public String OnExecute(RunsafePlayer executor, String[] args)
	{
		try
		{
			engine.eval(StringUtils.join(args, " "));
		}
		catch (ScriptException e)
		{
			e.printStackTrace();
			return e.getMessage();
		}
		return "Code executed";
	}

	private ScriptEngineManager mgr = new ScriptEngineManager();
	private ScriptEngine engine = mgr.getEngineByName("JavaScript");
}
