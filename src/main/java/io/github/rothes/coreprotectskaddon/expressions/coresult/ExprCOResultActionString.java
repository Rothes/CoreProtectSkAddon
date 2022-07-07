package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.event.Event;

public final class ExprCOResultActionString extends BaseCOResultExpr<String> {

    @Override
    protected String[] get(Event event) {
        return new String[]{getParseResult(event).getActionString()};
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
