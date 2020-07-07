package cn.fishmaple.logsight.object;

public class LogFile {
    private String name;
    private String size;
    private String lastModified;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public LogFile setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getLastModified() {
        return lastModified;
    }

    public LogFile setLastModified(String lastModified) {
        this.lastModified = lastModified;
        return this;
    }

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
