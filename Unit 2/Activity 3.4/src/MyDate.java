public class MyDate {
    private int day;
    private int month;
    private int year;


    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        if (this.month >= 1 && this.month <= 12) {
            if (this.month == 2 && (day >= 1 && day <= 28))
                this.day = day;
            else if (this.month % 2 == 0 && (day >= 1 && day <= 30) && this.month != 2)
                this.day = day;
            else if (this.month % 2 != 0 && (day >= 1 && day <= 31))
                this.day = day;
        }
        else
            this.day = 0;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month >= 1 && month <= 12)
            this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year > 0)
            this.year = year;
    }

    public void printDate() {
        System.out.println(String.format("Your date is %d/%d/%d and %d %s", this.day, this.month, this.year, this.year, isLeapYear()));
    }
    public String isLeapYear() {
        if (this.year > 0 && this.year % 4 == 0)
            return "is a leap year";
        else
            return "is not a leap year";
    }
}
