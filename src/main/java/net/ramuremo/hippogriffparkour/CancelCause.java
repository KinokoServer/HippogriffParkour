package net.ramuremo.hippogriffparkour;

public enum CancelCause {
    QUIT("サーバーから退出しました"),
    FLIGHT("飛行しました");

    private final String message;

    CancelCause(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}