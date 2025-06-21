package com.giotto.bean;
//"ID", "姓名", "性别", "年龄", "电话", "职位", "入职时间", "薪水", "部门信息"

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emoloyee {
    private int id;
    private String name;
    private String sex;
    private int age;
    private String phone;
    private String job;
    private String joinTime;
    private double salary;
    private String department;


}
