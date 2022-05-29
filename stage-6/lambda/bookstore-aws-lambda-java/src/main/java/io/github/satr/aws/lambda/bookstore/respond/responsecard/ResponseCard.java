package io.github.satr.aws.lambda.bookstore.respond.responsecard;
// Copyright © 2022, github.com/satr, MIT License

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ResponseCard {
    private int version = 1;
    private String contentType = "application/vnd.amazonaws.card.generic";
    private List<Attachment> genericAttachments = new LinkedList<>();

    public void setVersion(int version) {
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public Attachment[] getGenericAttachments() {
        return genericAttachments.stream().toArray(Attachment[]::new);
    }

    public void setGenericAttachments(Attachment[] genericAttachments) {
        this.genericAttachments = Arrays.asList(genericAttachments);
    }

    public Attachment addAttachment() {
        Attachment attachment = new Attachment();
        genericAttachments.add(attachment);
        return attachment;
    }
}
