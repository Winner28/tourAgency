package com.epam.lab.ultimatewebservice.entitys;import lombok.Data;import lombok.experimental.Accessors;@Data@Accessors(chain = true)public class Permissions {    private int user_id;    private int permission_name_id;}