package cn.fishmaple.logsight.service;

import cn.fishmaple.logsight.dao.dto.LogFieldTreeDTO;
import cn.fishmaple.logsight.dao.mapper.LogFieldTreeMapper;
import cn.fishmaple.logsight.object.FileNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileZoneService {
    @Autowired
    private LogFieldTreeMapper logFieldTreeMapper;
    public FileNode getLocalFileTree(Integer fieldId){
        FileNode fileNode = new FileNode();
        List<LogFieldTreeDTO> list = logFieldTreeMapper.selectDetail(fieldId,0L);
        List<FileNode> nodes = getNodes(list,fieldId);
        fileNode.setChildren(nodes);
        return fileNode;
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
