package com.regisoc.shared.infrastructure

import com.regisoc.shared.domain.FileStorageService
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileStorageServiceStub : FileStorageService {
    override fun store(file: MultipartFile, path: String): String {
        return "https://storage.stub/${path}/${file.originalFilename ?: "file"}"
    }

    override fun delete(url: String) {
    }
}
