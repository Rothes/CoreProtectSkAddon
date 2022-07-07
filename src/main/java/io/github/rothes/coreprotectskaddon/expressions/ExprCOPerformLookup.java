package io.github.rothes.coreprotectskaddon.expressions;

import io.github.rothes.coreprotectskaddon.CoreProtectSkAddon;
import io.github.rothes.coreprotectskaddon.COResult;
import org.bukkit.event.Event;

import java.util.List;

public final class ExprCOPerformLookup extends BaseCOPerformExpr {

    @Override
    protected COResult[] get(Event event) {
        COParam coParam = COParam.parseParams(this, event);
        List<String[]> strings = CoreProtectSkAddon.getCoreProtectAPI().performLookup(coParam.time, coParam.restrictUsers, coParam.excludeUsers, coParam.restrictBlocks, coParam.excludeBlocks, coParam.actionList, coParam.radius, coParam.radiusLocation);
        return processResults(strings);
    }

}
