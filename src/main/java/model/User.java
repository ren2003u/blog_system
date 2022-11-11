package model;

public class User {
    private String username;
    private int userId;
    private String password;
    private int isYourBlog;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsYourBlog() {
        return isYourBlog;
    }

    public void setIsYourBlog(int isYourBlog) {
        this.isYourBlog = isYourBlog;
    }
}
