package io.github.satr.aws.lambda.bookstore.repositories.database;
// Copyright © 2022, github.com/satr, MIT License

public final class Table {
    public static class Basket {
        public static final String Name = "Basket";
        public static class Attr {
            public static final String Isbn = "isbn";
        }
    }
    public static class BookSearchResult {
        public static final String Name = "BookSearchResult";
        public static class Attr {
            public static final String Isbn = "isbn";
        }
    }
}
