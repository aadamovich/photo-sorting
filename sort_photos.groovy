
@Grab('com.drewnoakes:metadata-extractor:2.7.0')
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Metadata
import com.drew.metadata.exif.ExifSubIFDDirectory

new File('.').eachFile { file ->
  if (file.isFile() && ['jpg', 'jpeg'].any { file.name.toLowerCase().endsWith(it) }) {
    Metadata metadata = ImageMetadataReader.readMetadata(file)
    ExifSubIFDDirectory directory = metadata?.getDirectory(ExifSubIFDDirectory)
    Date dateTimeTaken = directory?.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL)
    if (dateTimeTaken) {
      def parentDir = new File(file.parentFile, dateTimeTaken.format('YYYY-MM-dd'))
      parentDir.mkdirs()
      file.renameTo(new File(parentDir, file.name))
    }
  }
}