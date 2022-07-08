package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import io.github.rothes.coreprotectskaddon.CoreProtectSkAddon;
import io.github.rothes.coreprotectskaddon.COResult;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.List;

public final class ExprCOBlockLookup extends BaseCOLookupExpr {

    private Expression<Block> block;
    private Expression<Timespan> time;

    @Override
    protected COResult[] get(Event event) {
        Block block = this.block.getSingle(event);
        int time = this.time == null ? 3 * 24 * 60 * 60 : ((int) this.time.getSingle(event).getMilliSeconds() / 1000);
        List<String[]> strings = CoreProtectSkAddon.getCoreProtectAPI().blockLookup(block, time);
        return processResults(strings);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        block = (Expression<Block>) expressions[1];
        time = (Expression<Timespan>) expressions[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "COBlockLookup expression with expressions { block: " + toString(event, debug, block) +
                ", time: " + toString(event, debug, time) + " }";
    }

}
