package blog;

import interfaces.Application;
import interfaces.ParsedInput;
import utils.ConsoleListener;
import utils.InputParser;

import java.util.List;

public class BlogApp implements Application {
    private Sender sender;

    public BlogApp() throws Exception {
        new ConsoleListener(this);
    }

    public static void main(String[] args) throws Exception {
        new BlogApp();
    }

    @Override
    public ParsedInput parseInput(String input) {
        List<String> parts = InputParser.getTwoArgsResult(input);
        return new BlogArticle(parts.get(0), parts.get(1));
    }

    @Override
    public void handleCommand(ParsedInput parsedInput) throws Exception {
        sender = Sender.getInstance();
        if (parsedInput instanceof BlogArticle article) {
            sender.sendArticle(article);
            System.out.println("Article on topic: " + article.getTopic() + " successfully sent");
            return;
        }
        throw new IllegalArgumentException("--- Illegal article format ---");
    }
}
