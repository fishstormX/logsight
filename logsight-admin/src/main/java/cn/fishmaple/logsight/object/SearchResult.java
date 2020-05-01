package cn.fishmaple.logsight.object;

public class SearchResult {
    String file;
    String result;

    public SearchResult(String file, String result) {
        this.file = file;
        this.result = result;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
