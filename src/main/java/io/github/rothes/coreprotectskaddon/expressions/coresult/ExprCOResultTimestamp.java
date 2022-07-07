package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.event.Event;

public final class ExprCOResultTimestamp extends BaseCOResultExpr<Long> {

    @Override
    protected Long[] get(Event event) {
        return new Long[]{getParseResult(event).getTimestamp()};
    }

    @Override
    public Class<? extends Long> getReturnType() {
        return Long.class;
    }

}
