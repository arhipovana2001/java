public class Account {
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthDate;
    private String email;
    private String password;
    private boolean blocked = true;
    private int count = -1;

    public Account() {

    }

    public Account(String firstName, String middleName, String lastName, String birthDate, String email, String password) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.blocked = false;
        this.count = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBlocked() {
        return blocked;
    }

    public void setBlocked() {
        this.blocked = !this.blocked;
    }

    public int getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void countPlus() {
        //System.out.println("Count setMethod before: " + count);
        this.count++;
        //System.out.println("Count setMethod after: " + count);
    }

    @Override
    public String toString() {

        return firstName + "," + middleName + "," + lastName + "," + birthDate +
                "," + email + "," + password + "," + blocked + "," + count + "\n";
    }
}