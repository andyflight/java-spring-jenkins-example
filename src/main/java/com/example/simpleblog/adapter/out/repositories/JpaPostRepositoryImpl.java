package com.example.simpleblog.adapter.out.repositories;

import com.example.simpleblog.adapter.out.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JpaPostRepositoryImpl extends JpaRepository<PostEntity, Long> {

}
