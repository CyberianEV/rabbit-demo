package subscriber;

import interfaces.Application;
import interfaces.ParsedInput;
import utils.ConsoleListener;
import utils.InputParser;

import java.util.List;

public class SubscriberApp implements Application {
    private Receiver receiver;

    public SubscriberApp() throws Exception {
        receiver = Receiver.getInstance();
        new ConsoleListener(this);
        receiver.close();
    }

    public static void main(String[] args) throws Exception {
        new SubscriberApp();
    }

    @Override
    public ParsedInput parseInput(String input) {
        List<String> parts = InputParser.getTwoArgsResult(input);
        return new Command(parts.get(0), parts.get(1));
    }

    @Override
    public void handleCommand(ParsedInput parsedInput) throws Exception {
        if (parsedInput instanceof Command command) {
            switch (command.getDirective()) {
                case "set_topic":
                    receiver.subscribeOnTopic(command.getArgument());
                    break;
                case "remove_topic":
                    receiver.unsubscribeFromTopic(command.getArgument());
                    break;
                default:
                    throw new IllegalArgumentException("--- Unknown command received ---");
            }
            return;
        }
        throw new IllegalArgumentException("--- Illegal Command format ---");
    }
}
