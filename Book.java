
public class Book{

    private String name;
    private String authorName;
    private String publicationDate;
    private String isbn;
    private String borrowerName; // Added borrowerName attribute
    private boolean isIssued; //Set false as default

    public Book(String name, String authorName, String publicationDate, String isbn, boolean isIssued, String borrowerName) {
        this.name = name;
        this.authorName = authorName;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.borrowerName = null; // Default borrowerName to null
        this.isIssued = false;//Default to false
    }

    public String getName() {
        return name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
    }

    public boolean getIssued() {
        return isIssued; 
    }
    public void setIssued(boolean isissued){
        this.isIssued = isissued;
    }
    public String toCSV() {
        // Convert the attributes to a CSV-formatted string
        return String.join(",", name, authorName, publicationDate, isbn, String.valueOf(isIssued), borrowerName);
    }

}
