package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.coreprotect.CoreProtect;
import org.bukkit.Location;
import org.bukkit.event.Event;

public final class ExprCOLogInteraction extends BaseCOLogExpr {

    private Expression<String> user;
    private Expression<Location> location;

    @Override
    protected Boolean[] get(Event event) {
        return new Boolean[]{CoreProtect.getInstance().getAPI().logInteraction(user.getSingle(event), location.getSingle(event))};
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        user = (Expression<String>) expressions[0];
        location = (Expression<Location>) expressions[1];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "COLogInteraction expression with expressions { user: " + toString(event, debug, user) +
                ", location: " + toString(event, debug, location) + " }";
    }

}
