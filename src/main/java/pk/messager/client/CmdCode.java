package pk.messager.client;

public enum CmdCode {
    LOGIN("login"),
    SEND("send"),
    READ("read"),
    REPLY("reply"),
    FORWARD("forward"),
    BROADCAST("broadcast");

    private String code;
    CmdCode(String code) {
        this.code = code;
    }

    public static CmdCode getCmdByCode(String code) {
        for (CmdCode cmd : CmdCode.values()) {
            if (cmd.getCode().equals(code)) {
                return cmd;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
