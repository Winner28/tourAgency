package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.TourTypeDAO;
import com.epam.lab.ultimatewebservice.entity.TourType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TourTypeService {

    private TourTypeDAO tourTypeDAO;

    public String getTourTypeNameById(int id) {
        switch (id) {
            case 1:
                return "sightseeing";
            case 2:
                return "transfer";
            case 3:
                return "shopping";
            case 4:
                return "excursion";
            default:
                return "sightseeing";
        }

    }
}
