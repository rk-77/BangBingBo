package com.example.bangbingbo.data.chain;

public class VerticalChain extends Chain {

 private int topIndex;
 private int bottomIndex;

    public VerticalChain(int topIndex, int bottomIndex, int chainSize) {
        this.topIndex = topIndex;
        this.bottomIndex = bottomIndex;
        setChainSize(chainSize);
    }

    public int getTopIndex() {
        return topIndex;
    }

    public void setTopIndex(int topIndex) {
        this.topIndex = topIndex;
    }

    public int getBottomIndex() {
        return bottomIndex;
    }

    public void setBottomIndex(int bottomIndex) {
        this.bottomIndex = bottomIndex;
    }
}
