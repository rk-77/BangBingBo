package com.example.bangbingbo.data.chain;

public class HorizontalChain extends Chain {

    private int leftIndex;
    private int rightIndex;

    public HorizontalChain(int leftIndex, int rightIndex, int chainSize) {
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
        this.setChainSize(chainSize);
    }

    public int getLeftIndex() {
        return leftIndex;
    }

    public void setLeftIndex(int leftIndex) {
        this.leftIndex = leftIndex;
    }

    public int getRightIndex() {
        return rightIndex;
    }

    public void setRightIndex(int rightIndex) {
        this.rightIndex = rightIndex;
    }
}
