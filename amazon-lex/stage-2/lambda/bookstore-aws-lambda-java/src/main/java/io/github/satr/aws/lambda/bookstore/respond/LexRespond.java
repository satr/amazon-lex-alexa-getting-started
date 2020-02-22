package io.github.satr.aws.lambda.bookstore.respond;
// Copyright © 2020, github.com/satr, MIT License

public class LexRespond {
    private DialogAction dialogAction;

    public LexRespond(DialogAction dialogAction) {

        this.dialogAction = dialogAction;
    }

    public DialogAction getDialogAction() {
        return dialogAction;
    }
}
