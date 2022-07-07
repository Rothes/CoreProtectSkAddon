package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.event.Event;

public final class ExprCOResultZ extends BaseCOResultExpr<Integer> {

    @Override
    protected Integer[] get(Event event) {
        return new Integer[]{getParseResult(event).getZ()};
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

}
