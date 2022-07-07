package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.util.SimpleExpression;
import org.bukkit.event.Event;

public abstract class BaseCOExpr<T> extends SimpleExpression<T> {

    protected String toString(Event event, boolean debug, Expression<?> expression) {
        if (expression == null) {
            return "null";
        }
        if (event == null) {
            return "null event";
        }
        return expression.toString(event, debug);
    }

}
