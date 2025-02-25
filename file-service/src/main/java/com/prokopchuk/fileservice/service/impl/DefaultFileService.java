package com.prokopchuk.fileservice.service.impl;

import com.prokopchuk.fileservice.repository.FileDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultFileService {

    private final FileDataRepository fileDataRepository;

}
