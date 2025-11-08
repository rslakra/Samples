package com.rslakra.springbootsamples.emailservice.service.impl;

import com.rslakra.springbootsamples.emailservice.service.AnalysisService;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rohtash Lakra
 * @created 1/6/22 4:20 PM
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Override
    public List<String> getAllSerializedFiles() {
        // TODO: Implement file listing logic
        return new ArrayList<>();
    }

    @Override
    public void getSerializedFile(String fileName, HttpServletResponse servletResponse) {
        // TODO: Implement file download logic
    }

    @Override
    public boolean deleteSerializedFile(String fileName) {
        // TODO: Implement file deletion logic
        return false;
    }
}

