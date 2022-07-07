package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.event.Event;

public final class ExprCOResultRolledBack extends BaseCOResultExpr<Boolean> {

    @Override
    protected Boolean[] get(Event event) {
        return new Boolean[]{getParseResult(event).isRolledBack()};
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

}
