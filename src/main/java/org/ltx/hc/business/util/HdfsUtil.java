package org.ltx.hc.business.util;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.GenericOptionsParser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;

/**
 * @author ltxlouis
 * @since 4/3/2018
 */
public class HdfsUtil {

    private Configuration conf;

    public HdfsUtil(Configuration conf) {
        this.conf = conf;
    }

    public void uploadFile(String... args) throws IOException {

        GenericOptionsParser optionsParser = new GenericOptionsParser(conf, args);

        String[] remainingArgs = optionsParser.getRemainingArgs();
        if (remainingArgs.length < 2) {
            System.err.println("Usage: upload <source> <dest>");
            throw new IOException("remainingArgs error");
            //            System.exit(2);
        }

        Path source = new Path(args[0]);
        Path dest = new Path(args[1]);

        FileSystem fs = FileSystem.get(conf);

        fs.copyFromLocalFile(true, false, source, dest);
    }

    public void deleteFile(String arg) throws IOException {
        Path dest = new Path(arg);
        FileSystem fs = FileSystem.get(conf);
        fs.delete(dest, true);
    }

    public void downloadFile(HttpServletResponse res, String sourcePath) throws IOException {

        res.reset();
        res.setCharacterEncoding("utf-8");
        res.setContentType("application/x-download");
        res.setHeader("Accept-Ranges", "bytes");
        System.out.println("#######gcontenttpy######" + res.getContentType());

        String fileName = sourcePath.substring(sourcePath.lastIndexOf("/") + 1);

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        res.setHeader(headerKey, headerValue);

        // Copy the stream to the response's output stream.
        try (FileSystem inFs = FileSystem.get(URI.create(sourcePath), conf);
             FSDataInputStream is = inFs.open(new Path(sourcePath));
             OutputStream os = res.getOutputStream()) {

            byte[] buffer = new byte[1024 * 10];
            int len = 0;
            long fileSize = 0;
            while ((len = is.read(buffer, 0, buffer.length)) != -1) {
                os.write(buffer, 0, len);
                fileSize += len;
            }
            res.setHeader("Content-Length", String.valueOf(fileSize));
            res.flushBuffer();
        } catch (IOException e) {
            System.out.println("what the fuck!!!!!!!!!!");
            e.printStackTrace();
        }
    }

    public ArrayList<String> getFileList(String arg) throws IOException {
        Path dest = new Path(arg);
        FileSystem fs = FileSystem.get(conf);
        FileStatus[] fileStatus = fs.listStatus(dest);
        ArrayList<String> strings = new ArrayList<>();
        for (FileStatus status : fileStatus) {
            System.out.println();
            strings.add(status.getPath().toString());
        }
        return strings;
    }
}
