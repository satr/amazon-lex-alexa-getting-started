package io.github.satr.aws.lambda.bookstore.response;
// Copyright © 2020, github.com/satr, MIT License

public class Response {
    private DialogAction dialogAction;

    public Response(DialogAction dialogAction) {

        this.dialogAction = dialogAction;
    }

    public DialogAction getDialogAction() {
        return dialogAction;
    }
}
