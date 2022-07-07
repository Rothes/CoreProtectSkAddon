package io.github.rothes.coreprotectskaddon.expressions.coresult;

import org.bukkit.block.data.BlockData;
import org.bukkit.event.Event;

public final class ExprCOResultBlockData extends BaseCOResultExpr<BlockData> {

    @Override
    protected BlockData[] get(Event event) {
        return new BlockData[]{getParseResult(event).getBlockData()};
    }

    @Override
    public Class<? extends BlockData> getReturnType() {
        return BlockData.class;
    }

}
