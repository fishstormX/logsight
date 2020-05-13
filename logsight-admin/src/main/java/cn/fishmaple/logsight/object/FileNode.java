package cn.fishmaple.logsight.object;

import java.util.List;

public class FileNode {
    private String name;
    private Long id;
    private List<FileNode> children;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FileNode> getChildren() {
        return children;
    }

    public void setChildren(List<FileNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "FileNode{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", children=" + children +
                '}';
    }
}
