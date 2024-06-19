package de.wagentim.collector.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public final class FileDownloadUtil
{
    private Logger logger = LoggerFactory.getLogger(FileDownloadUtil.class);
    private CloseableHttpClient client = HttpClients.createDefault();

    public void simpleDownloadFile(String url, String location) throws IOException
    {
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        int code = response.getStatusLine().getStatusCode();
        if(code == HttpStatus.SC_OK)
        {
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            long length = entity.getContentLength();
            if(length <= 0)
            {
                logger.error("Download File does not exist!");
                return;
            }
            else
            {
                long total = length / 4096;
                long counter = 0;
                File file = new File(location);
                if(!file.exists())
                {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int readLength = 0;
                while((readLength = is.read(buffer)) > 0)
                {
                    byte[] bytes = new byte[readLength];
                    System.arraycopy(buffer, 0, bytes, 0, readLength);
                    fos.write(bytes);
                    counter++;
                    printProgress(0, total, counter);
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                System.out.println();
                fos.flush();

                is.close();
                fos.close();
            }
        }
        else
        {
            logger.info("Status Code: {}", code);
            return;
        }
    }

    private void printProgress(long startTime, long total, long current) {
        long eta = current == 0 ? 0 :
                (total - current) * (System.currentTimeMillis() - startTime) / current;

        String etaHms = current == 0 ? "N/A" :
                String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(eta),
                        TimeUnit.MILLISECONDS.toMinutes(eta) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(eta) % TimeUnit.MINUTES.toSeconds(1));

        StringBuilder string = new StringBuilder(140);
        int percent = (int) (current * 100 / total);
        string
                .append('\r')
                .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "=")))
                .append('>')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
                .append(String.format(" %d/%d, ETA: %s", current, total, etaHms));

        System.out.print(string);
    }

}
