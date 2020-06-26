package cn.fishmaple.logsight.object;

import java.util.List;

public class SearchResult {
    String file;
    List<String> result;

    public SearchResult(String file, List<String> result) {
        this.file = file;
        this.result = result;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
