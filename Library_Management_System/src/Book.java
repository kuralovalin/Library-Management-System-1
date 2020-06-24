import java.util.Objects;

public class Book extends Inventory implements Comparable<Book> {

        /**
         * Holds category of the book
         */
        private String category;

        /**
         * Holds name of the book
         */
        private String name;
        /**
         * Holds author of the book
         */
        private String author;


        public Book(String id, String category, String name, String author) {
            this.setID(id);
            this.category = category;
            this.name = name;
            this.author = author;
        }


    @Override
    public void changeStatus(String newStatus) {
        if(isAvailable())
            setStatus(newStatus);
        else
            System.out.println("Book has already taken");
    }

    /**
         * Updates time of the book, for 15 days
         */
        @Override
        public void updateTime() {
            // setTime(getTime().plusDays(15));
        }

        /**
         * Getters and setters
         */
        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }



        @Override
        public int compareTo(Book o) {
            String num1 = o.getID().substring(1);
            String num2 = this.getID().substring(1);

            return (Integer.valueOf(num1).compareTo(Integer.valueOf(num2)));

        }

        @Override
        public String toString() {
            return "Book{" +
                    "category='" + category + '\'' +
                    ", name='" + name + '\'' +
                    ", author='" + author + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Book book = (Book) o;
            return Objects.equals(category, book.category) &&
                    Objects.equals(name, book.name) &&
                    Objects.equals(author, book.author);
        }

        @Override
        public int hashCode() {
            return Objects.hash(category, name, author);
        }

}
