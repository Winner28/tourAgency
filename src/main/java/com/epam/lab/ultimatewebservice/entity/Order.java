package com.epam.lab.ultimatewebservice.entity;import lombok.Data;import lombok.experimental.Accessors;@Data@Accessors(chain = true)public class Order {    private int id;    private String date;    private boolean active;    private int tourId;    private int userId;}