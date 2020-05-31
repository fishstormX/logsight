package cn.fishmaple.logsight.service.filezone;

import cn.fishmaple.logsight.dao.dto.LogFieldTreeDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldTreeMapper;
import cn.fishmaple.logsight.object.FileNode;
import cn.fishmaple.logsight.object.FileTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileZoneService {
    @Autowired
    private LogFieldTreeMapper logFieldTreeMapper;
    public FileTree getLocalFileTree(Integer fieldId){
        FileNode fileNode = new FileNode();
        List<LogFieldTreeDTO> list = logFieldTreeMapper.selectDetail(fieldId,0L);
        FileTree fileTree = new FileTree();
        fileTree.setDepth(logFieldTreeMapper.getDepth(fieldId));
        Integer fileCount = 0;

        List<LogFieldTreeDTO> logFieldTreeDTOS = logFieldTreeMapper.getWidth(fieldId);
        int level=logFieldTreeDTOS.get(0).getLevel();
        for(LogFieldTreeDTO logFieldTreeDTO:logFieldTreeDTOS){
            if(logFieldTreeDTO.getLevel()==level){
                fileCount+=logFieldTreeDTO.getCount()<20?logFieldTreeDTO.getCount():20;
            }else{
                break;
            }
        }
        fileTree.setMaxWidth(fileCount);
        List<FileNode> nodes = getNodes(list,fieldId);
        if(1==nodes.size()){
            fileTree.setRoot(nodes.get(0));
        }else{
            fileNode.setChildren(nodes);
            fileTree.setRoot(fileNode);
        }
        FileNode fileNode1=fileTree.getRoot();
        Integer depth = fileTree.getDepth();
        for(int i =0;i<depth;i++){
            if(fileNode1.getChildren().size()==1){
                String name = fileNode1.getName();
                fileNode1=fileNode1.getChildren().get(0);
                fileNode1.setName(name+"/"+fileNode1.getName());
                fileTree.setDepth(fileTree.getDepth()-1);
            }else {
                break;
            }
        }
        fileTree.setDepth(fileTree.getDepth()+1);
        fileTree.setRoot(fileNode1);
        return fileTree;
    }

    public String getNodePath(Integer id){
        return logFieldTreeMapper.selectPathById(id);
    }

    private List<FileNode> getNodes(List<LogFieldTreeDTO> list,Integer fieldId){
        List<FileNode>  fileTree = new ArrayList<>();
        for(LogFieldTreeDTO logFieldTreeDTO:list){
            FileNode fileNode = new FileNode();
            fileNode.setId(logFieldTreeDTO.getId());
            fileNode.setName(logFieldTreeDTO.getName());
            fileNode.setChildren(getNodes(logFieldTreeMapper.selectDetailLimit(fieldId,logFieldTreeDTO.getId(),20),fieldId));
            fileTree.add(fileNode);
        }
        return fileTree;
    }
}
