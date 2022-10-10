package noob.NoobTeam.settings;

public class NumberSetting extends Setting {

    public double value, min, max, inc;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        double precision = 1 / inc;
        this.value = Math.round(Math.max(min, Math.min(max, value) * precision)) / precision;
    }

    public void inc(boolean positive) {
        setValue(getValue() + (positive ? 1 : -1) * inc);
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getInc() {
        return inc;
    }

    public void setInc(double inc) {
        this.inc = inc;
    }

    public NumberSetting(String name, double value, double min, double max, double inc) {
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
    }
}
