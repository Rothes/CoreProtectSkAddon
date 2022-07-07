package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.coreprotect.CoreProtect;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public final class ExprCOLogCommand extends BaseCOLogExpr {

    private Expression<Player> player;
    private Expression<String> command;

    @Override
    protected Boolean[] get(Event event) {
        return new Boolean[]{CoreProtect.getInstance().getAPI().logCommand(player.getSingle(event), command.getSingle(event))};
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        player = (Expression<Player>) expressions[0];
        command = (Expression<String>) expressions[1];
        return true;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "COLogCommand expression with expressions { player = " + toString(event, debug, player) +
                ", command = " + toString(event, debug, command) + " }";
    }

}
