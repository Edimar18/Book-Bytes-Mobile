public class Book {
    private String title;
    private String author;
    private String publicationYear;
    private String description;
    private boolean isBorrowed;
    
    public Book(String title, String author, String publicationYear, String description) {
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.description = description;
        this.isBorrowed = false;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getPublicationYear() {
        return publicationYear;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isBorrowed() {
        return isBorrowed;
    }
    
    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }
} 