package lxqtpr.ecommerce.linda.entities.FileService.service


import lxqtpr.ecommerce.linda.entities.FileService.FileEntity
import lxqtpr.ecommerce.linda.entities.FileService.FileRepository
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

@Service
class FileServiceImpl(
    val fileRepository: FileRepository
) : FileService {
    private val uploadsFolderPath: Path = Paths.get("C:\\Users\\admin\\Desktop\\linda\\src\\main\\resources\\static")
    override fun upload(files: List<MultipartFile>): ArrayList<FileEntity> {
        val resFiles = ArrayList<FileEntity>()
        for (file in files) {
            val dbFile = FileEntity(0, StringUtils.cleanPath(file.originalFilename!!), file.contentType!!)
            Files.copy(file.inputStream, uploadsFolderPath.resolve(file.originalFilename))
            resFiles.add(fileRepository.save(dbFile))
        }
        return resFiles
    }

}

