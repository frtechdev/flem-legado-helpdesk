/*
 * UtilZip.java
 *
 * Created on 3 de Julho de 2007, 14:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package br.org.flem.helpdesk.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author mjpereira
 */
public class UtilZip {
    
    /** Creates a new instance of UtilZip */
    public UtilZip() {
    }
    // ------------------------------------------------ Métodos públicos
    public static byte[] compactar(FormFile arquivo) throws FileNotFoundException, IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(arquivo.getFileSize());
        byte[] buf = new byte[arquivo.getFileSize()];
        ZipOutputStream out = new ZipOutputStream(baos);
        out.setComment("Compactado pelo HelpDesk Flem");
        out.setLevel(9);
        InputStream in = arquivo.getInputStream();
        out.putNextEntry(new ZipEntry(arquivo.getFileName()));
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        out.closeEntry();
        in.close();
        out.close();
        return baos.toByteArray();
    }//fim compactar()
    
    
    
}
