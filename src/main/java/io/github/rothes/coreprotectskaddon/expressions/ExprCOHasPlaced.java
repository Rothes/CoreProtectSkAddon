package io.github.rothes.coreprotectskaddon.expressions;


import io.github.rothes.coreprotectskaddon.CoreProtectSkAddon;
import org.bukkit.event.Event;

public final class ExprCOHasPlaced extends BaseCOHasExpr {

    @Override
    protected Boolean[] get(Event event) {
        return new Boolean[]{CoreProtectSkAddon.getCoreProtectAPI().hasPlaced(user.getSingle(event), block.getSingle(event), getTime(time, event), getTime(offset, event))};
    }

}
