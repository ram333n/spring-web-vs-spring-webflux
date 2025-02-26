package com.prokopchuk.fileservice.service;

public interface MkdirService {

    String mkdirCurrentYearDirectory(String root);

    String mkdirCurrentMonthDirectory(String root);

    String mkdirCurrentDayDirectory(String root);

}
