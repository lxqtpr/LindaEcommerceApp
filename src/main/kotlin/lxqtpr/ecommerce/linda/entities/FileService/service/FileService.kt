package lxqtpr.ecommerce.linda.entities.FileService.service

import lxqtpr.ecommerce.linda.entities.FileService.FileEntity
import org.springframework.web.multipart.MultipartFile

interface FileService {
    fun upload(files: List<MultipartFile>): ArrayList<FileEntity>
}