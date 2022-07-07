package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

public abstract class BaseCOHasExpr extends BaseCOLogExpr {

    protected Expression<String> user;
    protected Expression<Block> block;
    protected Expression<Timespan> time;
    protected Expression<Timespan> offset;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<String>) expressions[0];
        block = (Expression<Block>) expressions[1];
        time = (Expression<Timespan>) expressions[2];
        offset = (Expression<Timespan>) expressions[3];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return this.getClass().getSimpleName().substring(4) + " expression with expressions { user: " + toString(event, debug, user) +
                ", location: " + toString(event, debug, block) + " }";
    }

    protected int getTime(Expression<Timespan> timespan, Event event) {

        return ((int) timespan.getSingle(event).getMilliSeconds() / 1000);
    }

}
