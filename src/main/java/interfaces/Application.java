package interfaces;

public interface Application {
    ParsedInput parseInput(String input);
    void handleCommand(ParsedInput parsedInput) throws Exception;
}
