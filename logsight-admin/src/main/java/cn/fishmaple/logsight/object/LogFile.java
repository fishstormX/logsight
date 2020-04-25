package cn.fishmaple.logsight.object;

public class LogFile {
    private String name;
    private String size;

    public String getName() {
        return name;
    }

    public LogFile setName(String name) {
        this.name = name;
        return this;
    }

    public String getSize() {
        return size;
    }

    public LogFile setSize(String size) {
        this.size = size;
        return this;
    }
}
