package cn.fishmaple.logsight.object.fileAnalyse;

public class BaseFile {
    private String name;
    private String size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public BaseFile(String name, String size) {
        this.name = name;
        this.size = size;
    }
}
