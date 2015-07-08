package com.epam.kuzovatov.parser;

import com.epam.kuzovatov.entity.Bouquet;

public interface XmlParser {
    Bouquet parse(String filePath);
}
