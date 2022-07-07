package io.github.rothes.coreprotectskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.event.Event;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseCOPerformExpr extends BaseCOLookupExpr {

    protected Expression<Timespan> time;
    protected Expression<String> restrictUsers;
    protected Expression<String> excludeUsers;
    protected Expression<Object> restrictBlocks;
    protected Expression<Object> excludeBlocks;
    protected Expression<Integer> actionList;
    protected Expression<Integer> radius;
    protected Expression<Location> radiusLocation;

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        time = (Expression<Timespan>) expressions[0];
        restrictUsers = (Expression<String>) expressions[1];
        excludeUsers = (Expression<String>) expressions[2];
        restrictBlocks = (Expression<Object>) expressions[3];
        excludeBlocks = (Expression<Object>) expressions[4];
        actionList = (Expression<Integer>) expressions[5];
        radius = (Expression<Integer>) expressions[6];
        radiusLocation = (Expression<Location>) expressions[7];
        return true;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return this.getClass().getSimpleName().substring(4) + " expression with expressions { time: " + toString(event, debug, time) +
                ", restrict_users: " + toString(event, debug, restrictUsers) +
                ", exclude_users: " + toString(event, debug, excludeUsers) +
                ", restrict_blocks: " + toString(event, debug, restrictBlocks) +
                ", exclude_blocks: " + toString(event, debug, excludeBlocks) +
                ", action_list: " + toString(event, debug, actionList) +
                ", radius: " + toString(event, debug, radius) +
                ", radius_location: " + toString(event, debug, radiusLocation) + " }";
    }

    protected static class COParam {

        final protected int time;
        final protected List<String> restrictUsers;
        final protected List<String> excludeUsers;
        final protected List<Object> restrictBlocks;
        final protected List<Object> excludeBlocks;
        final protected List<Integer> actionList;
        final protected int radius;
        final protected Location radiusLocation;

        protected COParam(int time, List<String> restrictUsers, List<String> excludeUsers, List<Object> restrictBlocks, List<Object> excludeBlocks, List<Integer> actionList, int radius, Location radiusLocation) {
            this.time = time;
            this.restrictUsers = restrictUsers;
            this.excludeUsers = excludeUsers;
            this.restrictBlocks = restrictBlocks;
            this.excludeBlocks = excludeBlocks;
            this.actionList = actionList;
            this.radius = radius;
            this.radiusLocation = radiusLocation;
        }

        protected static COParam parseParams(BaseCOPerformExpr expression, Event event) {
            int time = expression.time == null ? 3 * 24 * 60 * 60 : ((int) expression.time.getSingle(event).getMilliSeconds() / 1000);
            List<String> restrictUsers = expression.restrictUsers == null ? new ArrayList<>() : Arrays.asList(expression.restrictUsers.getArray(event));
            List<String> excludeUsers = expression.excludeUsers == null ? new ArrayList<>() : Arrays.asList(expression.excludeUsers.getArray(event));
            List<Object> restrictBlocks = expression.restrictBlocks == null ? new ArrayList<>() : Arrays.asList(expression.restrictBlocks.getArray(event));
            List<Object> excludeBlocks = expression.excludeBlocks == null ? new ArrayList<>() : Arrays.asList(expression.excludeBlocks.getArray(event));
            List<Integer> actionList = expression.actionList == null ? new ArrayList<>() : Arrays.asList(expression.actionList.getArray(event));
            int radius = expression.radius.getSingle(event);
            Location radiusLocation = expression.radiusLocation.getSingle(event);
            return new COParam(time, restrictUsers, excludeUsers, restrictBlocks, excludeBlocks, actionList, radius, radiusLocation);
        }

    }

}
