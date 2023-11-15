package com.example.backendguateempleos.controller;


import com.example.backendguateempleos.querys.QueryFiles;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;

public class FileService {
    private final QueryFiles queryFiles = new QueryFiles();

    public boolean filesService(int cui, Part filePart)throws IOException {
        if (!filePart.getContentType().equalsIgnoreCase("application/pdf")){
            return false;
        } else {
            if (queryFiles.insertFileCv(cui, filePart)){
                return true;
            } else {
                return false;
            }
        }
    }
}
