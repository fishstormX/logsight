package cn.fishmaple.logsight.analyser.object;

import java.util.List;

public class SearchAction {
    private List<String> files;
    private String target;

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
