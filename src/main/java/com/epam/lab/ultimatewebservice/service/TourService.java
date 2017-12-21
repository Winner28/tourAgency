package com.epam.lab.ultimatewebservice.service;

import com.epam.lab.ultimatewebservice.db.dao.TourDAO;
import com.epam.lab.ultimatewebservice.entity.Tour;
import com.epam.lab.ultimatewebservice.entity.User;

import java.util.List;

public class TourService {

    private TourDAO tourDAO;
    public Tour getTourById(int id) { return tourDAO.getTourById(id).orElse(null);
    }

    public Tour createTour(Tour tour) { return tourDAO.addTour(tour).orElse(null);
    }

    public boolean deleteTourById(int id) {return tourDAO.deleteTourById(id);
    }

    public Tour updateTour(Tour tour) { return tourDAO.updateUser(tour).orElse(null);
    }

    public List<Tour> getTourList() { return tourDAO.getAllTours();
    }
}
