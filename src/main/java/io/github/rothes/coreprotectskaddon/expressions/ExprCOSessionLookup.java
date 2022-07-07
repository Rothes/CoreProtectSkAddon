package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.rothes.coreprotectskaddon.CoreProtectSkAddon;
import io.github.rothes.coreprotectskaddon.COResult;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.List;

public final class ExprCOSessionLookup extends BaseCOLookupExpr {

    private Expression<String> user;
    private Expression<Timespan> time;

    @Override
    protected COResult[] get(Event event) {
        String user = this.user.getSingle(event);
        int time = this.time == null ? 3 * 24 * 60 * 60 : ((int) this.time.getSingle(event).getMilliSeconds() / 1000);
        List<String[]> strings = CoreProtectSkAddon.getCoreProtectAPI().sessionLookup(user, time);
        return processResults(strings);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<String>) expressions[0];
        time = (Expression<Timespan>) expressions[1];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "COSessionLookup expression with expressions { user = " + toString(event, debug, user) +
                ", time = " + toString(event, debug, time) + " }";
    }

}
