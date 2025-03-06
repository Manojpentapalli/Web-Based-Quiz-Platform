package com.cdac.project.dto;

import java.util.List;

public class CsvResponse 
{
    private String message;
    private int validCount;
    private int skippedCount;
    private int duplicateCount;
    private List<String> skippedLines;

    public CsvResponse(String message, int validCount, int skippedCount, int duplicateCount, List<String> skippedLines) 
    {
        this.message = message;
        this.validCount = validCount;
        this.skippedCount = skippedCount;
        this.duplicateCount = duplicateCount;
        this.skippedLines = skippedLines;
    }

    // Getters & Setters
    public String getMessage() 
    {
        return message;
    }

    public void setMessage(String message) 
    {
        this.message = message;
    }

    public int getValidCount() 
    {
        return validCount;
    }

    public void setValidCount(int validCount) 
    {
        this.validCount = validCount;
    }

    public int getSkippedCount() 
    {
        return skippedCount;
    }

    public void setSkippedCount(int skippedCount) 
    {
        this.skippedCount = skippedCount;
    }

    public int getDuplicateCount() 
    {
        return duplicateCount;
    }

    public void setDuplicateCount(int duplicateCount) 
    {
        this.duplicateCount = duplicateCount;
    }

    public List<String> getSkippedLines() 
    {
        return skippedLines;
    }

    public void setSkippedLines(List<String> skippedLines) 
    {
        this.skippedLines = skippedLines;
    }
}
