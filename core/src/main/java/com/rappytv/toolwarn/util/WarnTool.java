package com.rappytv.toolwarn.util;

public class WarnTool {

    private final ToolType type;
    private final WarnSound sound;
    private final int warnAt;
    private final boolean openChat;
    private final boolean lastHitWarn;

    public WarnTool(ToolType type, WarnSound sound, int warnAt, boolean openChat, boolean lastHitWarn) {
        this.type = type;
        this.sound = sound;
        this.warnAt = warnAt;
        this.openChat = openChat;
        this.lastHitWarn = lastHitWarn;
    }
}
