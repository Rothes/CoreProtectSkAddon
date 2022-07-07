package io.github.rothes.coreprotectskaddon.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.rothes.coreprotectskaddon.CoreProtectSkAddon;
import org.bukkit.event.Event;

public final class EffCOPerformPurge extends Effect {

    private Expression<Timespan> time;

    @Override
    protected void execute(Event event) {
        CoreProtectSkAddon.getCoreProtectAPI().performPurge(((int) time.getSingle(event).getMilliSeconds() / 1000));
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "COPerformPurge effect with expression time: " + time.toString(event, debug);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        time = (Expression<Timespan>) expressions[0];
        return true;
    }

}
