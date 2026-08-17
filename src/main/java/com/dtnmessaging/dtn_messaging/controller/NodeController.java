package com.dtnmessaging.dtn_messaging.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dtnmessaging.dtn_messaging.model.Node;
import com.dtnmessaging.dtn_messaging.repository.NodeRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/nodes")

public class NodeController {

    private final NodeRepository nodeRepository;

    @PostMapping()
    public Node createNode(@RequestParam String name) {
        // create node
        Node node = new Node(null, name, false);
        // save in repository and reutn
        return nodeRepository.save(node);
    }

    @PutMapping("{id}/toggle")
    public Node toggleConnectivity(@PathVariable UUID id) {
        // fetch node by id
        Node fetchedNode = nodeRepository.findById(id).orElseThrow();
        // flip connectivity
        fetchedNode.setConnectivity(!fetchedNode.isConnectivity());
        return nodeRepository.save(fetchedNode);
    }

    public NodeController(NodeRepository nodeRepository) {
        this.nodeRepository = nodeRepository;
    }
}
