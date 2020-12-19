public class Question {
    String fileName;
    String country;

    public Question(String fileName, String country) {
        this.fileName = fileName;
        this.country = country;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
