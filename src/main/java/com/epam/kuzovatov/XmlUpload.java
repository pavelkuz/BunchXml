package com.epam.kuzovatov;

import com.epam.kuzovatov.parser.SaxBouquetParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Pavel on 09.07.2015.
 */
@WebServlet("/upload")
@MultipartConfig
public class XmlUpload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        InputStream inputStream = filePart.getInputStream();
        SaxBouquetParser bouquetSaxParser = new SaxBouquetParser();
        bouquetSaxParser.parse(inputStream);
        inputStream.close();
    }
}
