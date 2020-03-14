package io.github.satr.aws.lambda.bookstore.respond.responsecard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Attachment {
    private List<Button> buttons = new LinkedList<>();
    private String title;
    private String subTitle;
    private String imageUrl;
    private String attachmentLinkUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Button[] getButtons() {
        return buttons.stream().toArray(Button[]::new);
    }

    public void setButtons(Button[] buttons) {
        this.buttons = Arrays.asList(buttons);
    }

    public Attachment withButton(String text, String value) {
        buttons.add(new Button(text, value));
        return this;
    }

    public Attachment withTitle(String value) {
        setTitle(value);
        return this;
    }

    public Attachment withSubTitle(String value) {
        setSubTitle(value);
        return this;
    }

    public Attachment withImageUrl(String url) {
        setImageUrl(url);
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Attachment withAttachmentLinkUrl(String url) {
        setAttachmentLinkUrl(url);
        return this;
    }

    public void setAttachmentLinkUrl(String url) {
        this.attachmentLinkUrl = url;
    }

    public String getAttachmentLinkUrl() {
        return attachmentLinkUrl;
    }
}
