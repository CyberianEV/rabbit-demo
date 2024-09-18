package subscriber;

import interfaces.ParsedInput;

public class Command implements ParsedInput {
    private String directive;
    private String argument;

    public Command(String directive, String argument) {
        this.directive = directive;
        this.argument = argument;
    }

    public String getDirective() {
        return directive;
    }

    public void setDirective(String directive) {
        this.directive = directive;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
