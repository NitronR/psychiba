package com.bhanu.psychiba.repository;

import com.bhanu.psychiba.model.ContentWriter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentWriterRepository extends JpaRepository<ContentWriter, Long> {

}