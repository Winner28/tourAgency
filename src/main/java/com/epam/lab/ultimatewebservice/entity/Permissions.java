package com.epam.lab.ultimatewebservice.entity;import lombok.Data;import lombok.experimental.Accessors;@Data@Accessors(chain = true)public class Permissions {    private int userId;    private int permissionNameId;}