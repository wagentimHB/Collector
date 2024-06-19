package de.wagentim.collector.tools;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.mpatric.mp3agic.*;
import de.wagentim.collector.entity.KeyValuePair;
import de.wagentim.collector.sites.MusicSiteHandler;
import de.wagentim.collector.utils.FileUtils;
import de.wagentim.collector.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class MP3TagUpdater
{
    public static final Logger logger = LoggerFactory.getLogger(MP3TagUpdater.class);

    public void update() throws IOException {

        Path source = Paths.get(MusicSiteHandler.MUSIC_SOURCE_DIR);

        Files.walkFileTree(source, new SimpleFileVisitor<Path>()
                {
                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                            throws IOException
                    {
                        String fileName = file.getFileName().toString();
                        KeyValuePair pair = StringUtils.parserKeyValue(FileUtils.removeFileExtension(fileName, true), "-");
                        Path rename = Paths.get(file.getParent().normalize().toAbsolutePath() + File.separator + "temp.mp3");

                        if(pair.isComplete())
                        {
                            Files.move(file, rename);
                            Mp3File mp3File = null;

                            try {
                                mp3File = new Mp3File(rename);
                            } catch (UnsupportedTagException e) {
                                return FileVisitResult.CONTINUE;
                            } catch (InvalidDataException e) {
                                return FileVisitResult.CONTINUE;
                            }

                            if(mp3File != null)
                            {
                                String singer = ZhConverterUtil.toSimple(StringUtils.toUTF8(pair.getKey()));
                                String songName = ZhConverterUtil.toSimple(StringUtils.toUTF8(pair.getValue()));

                                ID3v2 tagV2 = mp3File.getId3v2Tag();
                                if(tagV2 == null)
                                {
                                    tagV2 = new ID3v22Tag();
                                    mp3File.setId3v2Tag(tagV2);
                                }
                                tagV2.setArtist(singer);
                                tagV2.setAlbumArtist(singer);
                                tagV2.setTitle(songName);

                                ID3v1 tagV1 = mp3File.getId3v1Tag();
                                if(tagV1 == null)
                                {
                                    tagV1 = new ID3v1Tag();
                                    mp3File.setId3v1Tag(tagV1);
                                }
                                tagV1.setArtist(singer);
                                tagV1.setTitle(songName);

                                try {
                                    Path target = Paths.get(MusicSiteHandler.MUSIC_TARGET_DIR + File.separator + fileName);
                                    mp3File.save(target.toAbsolutePath().toString());
                                    logger.info("Update Music: {}", fileName);
                                } catch (NotSupportedException e) {
                                    e.printStackTrace();
                                }


//                                Files.copy(file, target, StandardCopyOption.REPLACE_EXISTING);
                                Files.delete(rename);
                            }

                        }
                        return FileVisitResult.CONTINUE;
                    }

                }
        );
    }
}