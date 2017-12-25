package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.TourTypeDAO;
import com.epam.lab.ultimatewebservice.entity.TourType;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TourTypeService {

    private TourTypeDAO tourTypeDAO;

    public TourType getTourTypeById(int id){return tourTypeDAO.getTourTypeById(id).orElse(null);}
}
