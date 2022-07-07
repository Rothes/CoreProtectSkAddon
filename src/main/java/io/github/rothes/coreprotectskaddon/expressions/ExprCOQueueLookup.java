package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import io.github.rothes.coreprotectskaddon.CoreProtectSkAddon;
import io.github.rothes.coreprotectskaddon.COResult;
import org.bukkit.block.Block;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.List;

public final class ExprCOQueueLookup extends BaseCOLookupExpr {

    private Expression<Block> block;

    @Override
    protected COResult[] get(Event event) {
        Block block = this.block.getSingle(event);
        List<String[]> strings = CoreProtectSkAddon.getCoreProtectAPI().queueLookup(block);
        return processResults(strings);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        block = (Expression<Block>) expressions[0];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "COQueueLookup expression with expression block: " + toString(event, debug, block);
    }

}
