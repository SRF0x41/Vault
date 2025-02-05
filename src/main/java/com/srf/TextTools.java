package com.srf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TextTools {
    private static final HashSet<String> stopWords = new HashSet<>();

    final int MINIMUM_KEYWORD_LENGTH = 3;
    final int MAXIMUM_KEYWORD_LE