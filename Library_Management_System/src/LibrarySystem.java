public class LibrarySystem{


    private Student searchStudent(String studentId){
        //will look for a student in corresponding data structure. If found then it will return the instance of the student
        return null;
    }

    private Book searchBook(String bookId){
        //if book is found in the data structure then it will return the book
        return null;
    }

    public Book acceptrejectbookrequest (String studentID,String bookID) {
        Student student=searchStudent(studentID);
        Book book=searchBook(bookID);
        if( student != null && book != null && book.isAvailable() && student.getBookCounter() < 5 ){
           book.changeStatus();
           student.setBookCounter(student.getBookCounter()+1);
           return book;
        }
        return null;
    }


}