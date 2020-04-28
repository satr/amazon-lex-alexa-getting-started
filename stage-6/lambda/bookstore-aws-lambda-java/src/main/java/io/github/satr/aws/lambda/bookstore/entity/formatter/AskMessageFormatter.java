package io.github.satr.aws.lambda.bookstore.entity.formatter;

import io.github.satr.aws.lambda.bookstore.entity.Book;

public class AskMessageFormatter extends MessageFormatter {
    @Override
    public String getBookFullDescription(Book book, String newLineDelimiter) {
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append(String.format("\"%s\"%s", book.getTitle(), newLineDelimiter));
        messageBuilder.append(getShortBreak());
        messageBuilder.append(String.format(" by %s%s", book.getAuthor(), newLineDelimiter));
        messageBuilder.append(getShortBreak());
        messageBuilder.append(String.format(" issued in %s%s", book.getIssueYear(), newLineDelimiter));
        messageBuilder.append(getShortBreak());
        messageBuilder.append(String.format(" costs %s", getPriceText(book.getPrice())));
        messageBuilder.append(getShortBreak());
        return messageBuilder.toString();
    }

    @Override
    protected void addBookDescriptionToList(StringBuilder messageBuilder, Book book, boolean showBookNumber, int bookNumber, boolean withPrices) {
        messageBuilder.append(String.format("\"%s\" by %s.", book.getTitle(), book.getAuthor()));
        messageBuilder.append(getShortBreak());
        if(withPrices)
            messageBuilder.append(String.format("Costs %s", getPriceText(book.getPrice())));
        messageBuilder.append(getShortBreak());
    }

    private String getBreakSeconds(double seconds) {
        return String.format("<break time=\"%.1fs\"/>", seconds);
    }

    @Override
    public String getPriceText(double price) {
        String priceMainText = String.format("%d euro", (int)price);
        int priceFraction = (int)((price - ((int)price)) * 100);
        return priceFraction <= 0 ? priceMainText : priceMainText + String.format(" and %d cents.", priceFraction);
    }

    @Override
    public String getShortBreak() {
        return getBreakSeconds(0.5f);
    }
    public String getNormalBreak() {
        return getBreakSeconds(1.0f);
    }
}
