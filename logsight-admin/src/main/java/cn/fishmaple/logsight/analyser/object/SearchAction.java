package cn.fishmaple.logsight.analyser.object;

import java.util.Collection;

public class SearchAction {
    private Collection<String> files;
    private String target;

    public SearchAction(Collection<String> files, String target) {
        this.files = files;
        this.target = target;
    }

    public Collection<String> getFiles() {
        return files;
    }

    public void setFiles(Collection<String> files) {
        this.files = files;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
