package io.github.rothes.coreprotectskaddon.expressions;

public abstract class BaseCOLogExpr extends BaseCOExpr<Boolean> {

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

}
