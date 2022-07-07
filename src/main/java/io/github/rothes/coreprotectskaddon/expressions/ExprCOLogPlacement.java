package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.coreprotect.CoreProtect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.Event;

public final class ExprCOLogPlacement extends BaseCOLogExpr {

    private Expression<String> user;
    private Expression<Location> location;
    private Expression<Material> material;
    private Expression<BlockData> blockData;

    @Override
    protected Boolean[] get(Event event) {
        return new Boolean[]{CoreProtect.getInstance().getAPI().logPlacement(user.getSingle(event), location.getSingle(event), material.getSingle(event), blockData.getSingle(event))};
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<String>) expressions[0];
        location = (Expression<Location>) expressions[3];
        material = (Expression<Material>) expressions[1];
        blockData = (Expression<BlockData>) expressions[2];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "COLogPlacement expression with expressions { user: " + toString(event, debug, user) +
                ", location: " + toString(event, debug, location) +
                ", material: " + toString(event, debug, material) +
                ", blockData: " + toString(event, debug, blockData) + " }";
    }

}
