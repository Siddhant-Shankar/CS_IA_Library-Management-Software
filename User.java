public class User {
    private String name;
    private String title;
    private String username = "";
    private String password = "";

    public User(String name, String title) {
        this.name = name;
        this.title = title;
    }

    // Getter and setter methods for name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and setter methods for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and setter methods for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter methods for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String toCSV() {
        StringBuilder csvString = new StringBuilder();
        csvString.append(name).append(",");
        csvString.append(title).append(",");
        csvString.append(username).append(",");
        csvString.append(password);
        return csvString.toString();
    }


}
