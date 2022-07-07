package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.Material;
import org.bukkit.event.Event;

public final class ExprCOResultType extends BaseCOResultExpr<Material> {

    @Override
    protected Material[] get(Event event) {
        return new Material[]{getParseResult(event).getType()};
    }

    @Override
    public Class<? extends Material> getReturnType() {
        return Material.class;
    }

}
