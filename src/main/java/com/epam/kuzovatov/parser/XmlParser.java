package com.epam.kuzovatov.parser;

import com.epam.kuzovatov.entity.Bouquet;

import java.io.InputStream;

public interface XmlParser {
    Bouquet parse(InputStream inputStream);
    Bouquet parse(String filePath);
}
