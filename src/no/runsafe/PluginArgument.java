package no.runsafe;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import no.runsafe.framework.RunsafePlugin;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.CommandArgumentSpecification;
import no.runsafe.framework.api.command.argument.ITabComplete;
import no.runsafe.framework.api.command.argument.IValueExpander;
import no.runsafe.framework.api.player.IPlayer;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class PluginArgument extends CommandArgumentSpecification<Iterable<RunsafePlugin>> implements ITabComplete, IValueExpander
{
	public PluginArgument()
	{
		super("plugin");
	}

	@Override
	public boolean isRequired()
	{
		return true;
	}

	@Override
	public boolean isWhitespaceInclusive()
	{
		return false;
	}

	@Override
	public List<String> getAlternatives(IPlayer executor, String partial)
	{
		List<String> alternates = Lists.transform(
			RunsafePlugin.getPlugins("*"),
			new Function<RunsafePlugin, String>()
			{
				@Override
				public String apply(@Nullable RunsafePlugin plugin)
				{
					if (plugin == null)
						return null;
					return plugin.getName();
				}
			}
		);
		alternates.add("*");
		return alternates;
	}

	@Nullable
	@Override
	public String expand(ICommandExecutor context, String value)
	{
		if (value == null || value.equals("*"))
			return value;

		List<RunsafePlugin> alternatives = RunsafePlugin.getPlugins(value);
		if (alternatives.isEmpty())
			return null;

		if (alternatives.size() > 1)
			return value;

		return alternatives.get(0).getName();
	}

	@Override
	public Iterable<RunsafePlugin> getValue(IPlayer context, Map<String, String> params)
	{
		return RunsafePlugin.getPlugins(params.get(name));
	}
}
