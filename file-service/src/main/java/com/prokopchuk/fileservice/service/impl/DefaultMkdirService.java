package com.prokopchuk.fileservice.service.impl;

import com.prokopchuk.fileservice.service.MkdirService;
import java.io.File;
import java.nio.file.FileSystems;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class DefaultMkdirService implements MkdirService {

    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();

    @Override
    public String mkdirCurrentYearDirectory(String root) {
        int currentYear = LocalDate.now().getYear();

        return mkdirInternal(root, Integer.toString(currentYear));
    }

    private String mkdirInternal(String root, String path) {
        File dir = new File(root + SEPARATOR + path);

        if (!dir.exists()) {
            boolean dirCreated = dir.mkdir();

            if (dirCreated) {
                log.info("Directory created: {}", dir.getAbsolutePath());
            } else {
                log.warn("Directory not created: {}", dir.getAbsolutePath());
            }
        }

        return root + SEPARATOR + path;
    }

    @Override
    public String mkdirCurrentMonthDirectory(String root) {
        String currentYearDirectory = mkdirCurrentYearDirectory(root);
        String currentMonthStr = getMonthNumberStr(LocalDateTime.now());

        return mkdirInternal(currentYearDirectory, SEPARATOR + currentMonthStr);
    }

    private String getMonthNumberStr(LocalDateTime date) {
        int monthValue = date.getMonth().getValue();

        return monthValue < 10 ? "0" + monthValue : Integer.toString(monthValue);
    }

    @Override
    public String mkdirCurrentDayDirectory(String root) {
        String currentMonthDirectory = mkdirCurrentMonthDirectory(root);
        String currentDayStr = getDayNumberStr(LocalDateTime.now());

        return mkdirInternal(currentMonthDirectory, SEPARATOR + currentDayStr);
    }

    private String getDayNumberStr(LocalDateTime date) {
        int dayNumber = date.getDayOfMonth();

        return dayNumber < 10 ? "0" + dayNumber : Integer.toString(dayNumber);
    }

}
