package pk.messager.client;

import java.util.List;

/**
 * Class holding command details
 */
public class Cmd {
    private CmdCode cmdCode;
    private List<String> params;
    private String message;

    public Cmd() {
    }

    public Cmd(CmdCode cmdCode, List<String> params, String message) {
        this.cmdCode = cmdCode;
        this.params = params;
        this.message = message;
    }

    public CmdCode getCmdCode() {
        return cmdCode;
    }

    public void setCmdCode(CmdCode cmdCode) {
        this.cmdCode = cmdCode;
    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
