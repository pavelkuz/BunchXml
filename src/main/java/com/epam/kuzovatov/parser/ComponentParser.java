package com.epam.kuzovatov.parser;

import com.epam.kuzovatov.entity.Bouquet;

import java.io.InputStream;

public interface ComponentParser {
    Bouquet parse(InputStream inputStream);
    Bouquet parse(String filePath);
}
