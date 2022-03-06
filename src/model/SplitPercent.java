package model;

public class SplitPercent extends Split{
    private double percent;

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public SplitPercent(User user, double percent) {
        super(user);
        this.percent = percent;
    }
}
