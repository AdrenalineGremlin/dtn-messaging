package com.dtnmessaging.dtn_messaging.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dtnmessaging.dtn_messaging.model.Node;

public interface NodeRepository extends JpaRepository<Node, UUID> {
    // optional - the node can only have one name
    Optional<Node> findByName(String name);
}
