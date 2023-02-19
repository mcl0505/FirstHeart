package com.konglianyuyin.mx.event;

public class RankSwitchEvent {
    private boolean isGxb;  // 是否是贡献榜

    public RankSwitchEvent(boolean isGxb) {
        this.isGxb = isGxb;
    }

    public boolean isGxb() {
        return isGxb;
    }
}
