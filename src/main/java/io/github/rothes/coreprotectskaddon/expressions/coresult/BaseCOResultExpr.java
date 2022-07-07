package io.github.rothes.coreprotectskaddon.expressions.coresult;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import io.github.rothes.coreprotectskaddon.COResult;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.event.Event;

public abstract class BaseCOResultExpr<T> extends SimpleExpression<T> {

    protected Expression<COResult> coResult;

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        coResult = (Expression<COResult>) expressions[0];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return this.getClass().getSimpleName().substring(4) + " expression with expression co_result: " + coResult.toString(event, debug);
    }

    protected CoreProtectAPI.ParseResult getParseResult(Event event) {
        return coResult.getSingle(event).parseResult;
    }

}
