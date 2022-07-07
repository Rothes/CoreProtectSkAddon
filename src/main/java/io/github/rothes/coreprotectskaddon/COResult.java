package io.github.rothes.coreprotectskaddon;

import net.coreprotect.CoreProtectAPI;

public final class COResult {

    final public String[] result;
    final public CoreProtectAPI.ParseResult parseResult;

    public COResult(String[] result) {
        this.result = result;
        this.parseResult = CoreProtectSkAddon.getCoreProtectAPI().parseResult(result);
    }

}
