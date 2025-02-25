package com.prokopchuk.fileservice.repository;

import com.prokopchuk.fileservice.domain.FileData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

}
