package io.github.rothes.coreprotectskaddon.expressions;

import io.github.rothes.coreprotectskaddon.COResult;

import java.util.List;

public abstract class BaseCOLookupExpr extends BaseCOExpr<COResult> {

    @Override
    public Class<? extends COResult> getReturnType() {
        return COResult.class;
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    protected COResult[] processResults(List<String[]> strings) {
        if (strings == null) {
            return null;
        }
        COResult[] result = new COResult[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            result[i] = new COResult(strings.get(i));
        }
        return result;
    }

}
