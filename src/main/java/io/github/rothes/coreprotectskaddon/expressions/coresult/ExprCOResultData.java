package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.event.Event;

public final class ExprCOResultData extends BaseCOResultExpr<Integer> {

    @Override
    protected Integer[] get(Event event) {
        return new Integer[]{getParseResult(event).getData()};
    }

    @Override
    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }


}
