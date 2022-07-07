package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.event.Event;

public final class ExprCOResultPlayer extends BaseCOResultExpr<String> {

    @Override
    protected String[] get(Event event) {
        return new String[]{getParseResult(event).getPlayer()};
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

}
