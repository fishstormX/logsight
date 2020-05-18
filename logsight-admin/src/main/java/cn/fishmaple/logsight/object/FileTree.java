package cn.fishmaple.logsight.object;

public class FileTree {
    private Integer depth;
    private Integer maxWidth;
    private FileNode root;

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    public FileNode getRoot() {
        return root;
    }

    public void setRoot(FileNode root) {
        this.root = root;
    }
}
