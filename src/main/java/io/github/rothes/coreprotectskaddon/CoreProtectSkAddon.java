package io.github.rothes.coreprotectskaddon;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import io.github.rothes.coreprotectskaddon.effects.EffCOPerformPurge;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOBlockLookup;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOHasPlaced;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOHasRemoved;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOLogChat;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOLogCommand;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOLogContainerTransaction;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOLogInteraction;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOLogPlacement;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOLogRemoval;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOPerformLookup;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOPerformRestore;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOPerformRollback;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOQueueLookup;
import io.github.rothes.coreprotectskaddon.expressions.ExprCOSessionLookup;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultActionId;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultActionString;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultBlockData;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultData;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultPlayer;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultRolledBack;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultTimestamp;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultType;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultWorldName;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultX;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultY;
import io.github.rothes.coreprotectskaddon.expressions.coresult.ExprCOResultZ;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.logging.Level;

public final class CoreProtectSkAddon extends JavaPlugin {

    private static CoreProtectAPI coreProtectAPI;

    public static CoreProtectAPI getCoreProtectAPI() {
        return coreProtectAPI;
    }

    @Override
    public void onEnable() {
        coreProtectAPI = getCoreProtect();
        if (coreProtectAPI == null) {
            getLogger().log(Level.SEVERE, "Disabling plugin due to cannot get CoreProtectAPI. Is it enabled?");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!Skript.isAcceptRegistrations()) {
            getLogger().log(Level.SEVERE, "Disabling plugin due to Skript doesn't accept registrations now. Please restart the whole server.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        registerClasses();
        registerExpressions();
        Skript.registerEffect(EffCOPerformPurge.class, "[co] perform purge before %timespan%");
        Skript.registerAddon(this);
    }

    private void registerClasses() {
        Classes.registerClass(new ClassInfo<>(COResult.class, "coresult")
                .name("COResult")
                .description("Represents a result of CoreProtect action.")
                .defaultExpression(new EventValueExpression<>(COResult.class))
                .parser(new Parser<COResult>() {
                    @Override
                    @Nonnull
                    public String toString(COResult coResult, int flags) {
                        return toVariableNameString(coResult);
                    }

                    @Override
                    public boolean canParse(@Nonnull ParseContext context) {
                        return false;
                    }

                    @Override
                    public COResult parse(@Nonnull String s, @Nonnull ParseContext context) {
                        return null;
                    }

                    @Override
                    @Nonnull
                    public String toVariableNameString(COResult coResult) {
                        StringBuilder builder = new StringBuilder("COResult:");
                        for (String s : coResult.result) {
                            builder.append(s).append(',');
                        }
                        builder.setLength(builder.length() - 1);

                        return builder.toString();
                    }

                    @Override
                    @Nonnull
                    public String getVariableNamePattern() {
                        return "COResult:[\\s\\S]+";
                    }
                }));
    }

    private void registerExpressions() {
        Skript.registerExpression(ExprCOPerformLookup.class, COResult.class, ExpressionType.COMBINED,
                "[co] perform lookup " +
                        "[in %-timespan%] " +
                        "[with restrict user[s] %-strings%] " +
                        "[with exclude user[s] %-strings%] " +
                        "[with restrict block[s] %-objects%] " +
                        "[with exclude block[s] %-objects%] " +
                        "[with action[s] %-integers%] " +
                        "in %integer% " +
                        "(of|at) %location%");
        Skript.registerExpression(ExprCOPerformRollback.class, COResult.class, ExpressionType.COMBINED,
                "[co] perform rollback " +
                        "[in %-timespan%] " +
                        "[with restrict user[s] %-strings%] " +
                        "[with exclude user[s] %-strings%] " +
                        "[with restrict block[s] %-objects%] " +
                        "[with exclude block[s] %-objects%] " +
                        "[with action[s] %-integers%] " +
                        "in %integer% " +
                        "(of|at) %location%");
        Skript.registerExpression(ExprCOPerformRestore.class, COResult.class, ExpressionType.COMBINED,
                "[co] perform restore " +
                        "[in %-timespan%] " +
                        "[with restrict user[s] %-strings%] " +
                        "[with exclude user[s] %-strings%] " +
                        "[with restrict block[s] %-objects%] " +
                        "[with exclude block[s] %-objects%] " +
                        "[with action[s] %-integers%] " +
                        "in %integer% " +
                        "(of|at) %location%");
        Skript.registerExpression(ExprCOBlockLookup.class, COResult.class, ExpressionType.COMBINED,
                "[co] block lookup " +
                        "[in %-timespan%] " +
                        "(of|at) %block%");
        Skript.registerExpression(ExprCOSessionLookup.class, COResult.class, ExpressionType.COMBINED,
                "[co] session lookup " +
                        "[in %-timespan%] " +
                        "(of|at) %string%");
        Skript.registerExpression(ExprCOQueueLookup.class, COResult.class, ExpressionType.COMBINED,
                "[co] queue lookup " +
                        "of %block%");
        Skript.registerExpression(ExprCOLogChat.class, Boolean.class, ExpressionType.COMBINED,
                "[co] log %player%('[s] chat| chat[ted]) %string%");
        Skript.registerExpression(ExprCOLogCommand.class, Boolean.class, ExpressionType.COMBINED,
                "[co] log %player%('[s] command| command[ed]) %string%");
        Skript.registerExpression(ExprCOLogPlacement.class, Boolean.class, ExpressionType.COMBINED,
                "[co] log %string%('[s] placement| place[d]) %material% with %blockdata% (of|at) %location%");
        Skript.registerExpression(ExprCOLogRemoval.class, Boolean.class, ExpressionType.COMBINED,
                "[co] log %string%('[s] removal| remove[d]) %material% with %blockdata% (of|at) %location%");
        Skript.registerExpression(ExprCOLogContainerTransaction.class, Boolean.class, ExpressionType.COMBINED,
                "[co] log %string%('[s] container transaction| transact[ed] container) (of|at) %location%");
        Skript.registerExpression(ExprCOLogInteraction.class, Boolean.class, ExpressionType.COMBINED,
                "[co] log %string%('[s] interaction| interact[ed]) (of|at) %location%");
        Skript.registerExpression(ExprCOHasPlaced.class, Boolean.class, ExpressionType.COMBINED,
                "[co] (have|has) %string% placed block %block% in %timespan% [before %-timespan%]");
        Skript.registerExpression(ExprCOHasRemoved.class, Boolean.class, ExpressionType.COMBINED,
                "[co] (have|has) %string% removed block %block% in %timespan% [before %-timespan%]");

        Skript.registerExpression(ExprCOResultActionId.class, Integer.class, ExpressionType.COMBINED,
                "[the] action[ ]id of %coresult%");
        Skript.registerExpression(ExprCOResultActionString.class, String.class, ExpressionType.COMBINED,
                "[the] action[ ]string of %coresult%");
        Skript.registerExpression(ExprCOResultBlockData.class, BlockData.class, ExpressionType.COMBINED,
                "[the] block[ ]data of %coresult%");
        Skript.registerExpression(ExprCOResultData.class, Integer.class, ExpressionType.COMBINED,
                "[the] data of %coresult%");
        Skript.registerExpression(ExprCOResultPlayer.class, String.class, ExpressionType.COMBINED,
                "[the] player of %coresult%");
        Skript.registerExpression(ExprCOResultRolledBack.class, Boolean.class, ExpressionType.COMBINED,
                "[the] roll(ed |ed|ed-)back of %coresult%");
        Skript.registerExpression(ExprCOResultTimestamp.class, Long.class, ExpressionType.COMBINED,
                "[the] time[ ]stamp of %coresult%");
        Skript.registerExpression(ExprCOResultType.class, Material.class, ExpressionType.COMBINED,
                "[the] type of %coresult%");
        Skript.registerExpression(ExprCOResultWorldName.class, String.class, ExpressionType.COMBINED,
                "[the] world[ ]name of %coresult%");
        Skript.registerExpression(ExprCOResultX.class, Integer.class, ExpressionType.COMBINED,
                "[the] x of %coresult%");
        Skript.registerExpression(ExprCOResultY.class, Integer.class, ExpressionType.COMBINED,
                "[the] y of %coresult%");
        Skript.registerExpression(ExprCOResultZ.class, Integer.class, ExpressionType.COMBINED,
                "[the] z of %coresult%");
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        if (plugin == null) {
            return null;
        }

        // Check that CoreProtect is loaded
        if (!(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI coreProtect = ((CoreProtect) plugin).getAPI();
        if (!coreProtect.isEnabled()) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (coreProtect.APIVersion() < 9) {
            return null;
        }

        return coreProtect;
    }

}
