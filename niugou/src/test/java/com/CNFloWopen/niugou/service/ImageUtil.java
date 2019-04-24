package com.CNFloWopen.niugou.service;


import com.CNFloWopen.niugou.dto.ImageHolder;
import com.CNFloWopen.niugou.util.PathUtil;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 用户上传文件时的处理方式
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageUtil {
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    //获取当前目录的
    private static String basePath = "/Users/argeszy/resources/image";
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();
    @Test
    public void test() throws IOException {
        //测试用的=======能测试成功，~~
        Thumbnails.of(new File("/users/argeszy/resources/test.jpg")).size(500,313)
                .outputQuality(0.8f).toFile("/users/argeszy/resources/testNew.jpg");
    }
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr)
    {
        String realFileName = getRandomFileName();//新名字
        String extension = getFileExtension(thumbnail.getImageName());//获取后缀名
        makeDirPath(targetAddr);//创建目录
        //targetAddr这个是存放的子目录
        String relativeAddr = targetAddr + realFileName + extension;//获取总名字
        logger.debug("current complete relativeAddr is"+relativeAddr);
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);//创建文件
        logger.debug("current complete addr is"+PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("basePath is"+basePath);
        try {
            /** Thumbnails.of传入相关的文件
             *thumbnail.getInputStream()输入流，得到输入的图片
             * size输出图片的大小
             * watermark添加水印（图片等） ----
             *                     1：水印的位置
             *                     2：图片的路径
             *                     3：水印的透明度
             * outputQuality压缩图片大小
             *  toFile输出改变后的新图片位置
             */
            Thumbnails.of(thumbnail.getImage()).size(500,313)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.8f).toFile(dest);
        }catch (IOException e)
        {
            logger.error(e.toString());
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及的所有目录
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String mk = PathUtil.getImgBasePath() + targetAddr;
        File dealPath = new File(mk);
        if (!dealPath.exists())
        {
            dealPath.mkdirs();
        }

    }

    /**
     * 获取文件的后缀名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获取随机目录====当前时间加上5个随机数字
     * @return
     */
    public static String getRandomFileName() {
//        获取5位随机数
        int rannum = r.nextInt(89999)+10000;
        String nowTimeStr = sDateFormat.format(new Date());

        return nowTimeStr+rannum;
    }

    /**
     * storePath是文件路径还是目录路径
     * 如果storePath是文件路径则删除该文件
     * 如果storePath是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath)
    {
        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if (fileOrPath.exists())
        {
            if (fileOrPath.isDirectory())
            {
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    /**
     * 处理详情图并返回新生成图片的相对路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImgs(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();//新名字
        String extension = getFileExtension(thumbnail.getImageName());//获取后缀名
        makeDirPath(targetAddr);//创建目录
        //targetAddr这个是存放的子目录
        String relativeAddr = targetAddr + realFileName + extension;//获取总名字
        logger.debug("current complete relativeAddr is"+relativeAddr);
        File dest = new File(PathUtil.getImgBasePath()+relativeAddr);//创建文件
        logger.debug("current complete addr is"+PathUtil.getImgBasePath()+relativeAddr);
        logger.debug("basePath is"+basePath);
        try {
            /** Thumbnails.of传入相关的文件
             *thumbnail.getInputStream()输入流，得到输入的图片
             * size输出图片的大小
             * watermark添加水印（图片等） ----
             *                     1：水印的位置
             *                     2：图片的路径
             *                     3：水印的透明度
             * outputQuality压缩图片大小
             *  toFile输出改变后的新图片位置
             */
            Thumbnails.of(thumbnail.getImage()).size(337,640)
                    .watermark(Positions.BOTTOM_RIGHT,ImageIO.read(new File(basePath+"/watermark.jpg")),0.25f)
                    .outputQuality(0.9f).toFile(dest);
        }catch (IOException e)
        {
            logger.error(e.toString());
        }
        return relativeAddr;
    }
}