package mia.utils;

import java.io.File;

/**
 * Created by liudehua on 2017/10/23.
 */
public class ResourceUtil {

    private ResourceUtil resourceUtil;


    public ResourceUtil getResourceUtil() {
        return resourceUtil;
    }

    public void setResourceUtil(ResourceUtil resourceUtil) {
        this.resourceUtil = resourceUtil;
    }

    public File getFile (String filePath) {
        ClassLoader cl = getClass().getClassLoader();

        String path = cl.getResource(filePath).getPath();
        File retFile  = new File(cl.getResource(filePath).getFile());

        return retFile;
    }

    public static synchronized File loadFile(String filePath) {

        ResourceUtil resourceUtil = new ResourceUtil();
        if (resourceUtil.getResourceUtil() == null) {
            resourceUtil.setResourceUtil(new ResourceUtil());
        }

        return resourceUtil.getFile(filePath);
    }

    public static String getResource(String fileName) {
        File curDirectory = new File(new File("").getAbsolutePath());
        String sourcePath = curDirectory.getAbsolutePath() + "/src/main/resources/" + fileName;

        return sourcePath;
    }
}
