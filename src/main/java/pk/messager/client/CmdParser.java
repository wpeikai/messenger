package pk.messager.client;

import java.util.ArrayList;
import java.util.List;

public class CmdParser {
    public Cmd parse(String input) throws InvalidInputException {
        if (input == null) {
            throw new InvalidInputException("Input cannot be null");
        }
        input = input.trim();
        CmdCode cmdCode = null;
        List<String> params = new ArrayList<>();
        String message = null;
        String[] parts = input.split("\\s\"");
        if (parts.length > 2) {
            throw new InvalidInputException("Please check the command format: cmd params[] \"message\"");
        } else if (parts.length == 2) {
            if (!parts[1].endsWith("\"")) {
                throw new InvalidInputException("message is not enclosed with \" properly");
            }
            message = parts[1].substring(0, parts[1].length()-1);
        }
        String[] tokens = parts[0].split("\\s+");
        if (tokens.length == 0) {
            throw new InvalidInputException("Please check the command format: cmd params[] \"message\"");
        }
        cmdCode = CmdCode.getCmdByCode(tokens[0]);
        if (cmdCode == null) {
            throw new InvalidInputException("Unrecognised command");
        }
        for (int i=1; i<tokens.length; i++) {
            params.add(tokens[i]);
        }
        return new Cmd(cmdCode, params, message);
    }
}
