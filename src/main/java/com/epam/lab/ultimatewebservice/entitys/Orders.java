package com.epam.lab.ultimatewebservice.entitys;import lombok.Data;import lombok.experimental.Accessors;@Data@Accessors(chain = true)public class Orders {    private int id;    private String date;    private String active;    private int tour_id;    private int user_id;}