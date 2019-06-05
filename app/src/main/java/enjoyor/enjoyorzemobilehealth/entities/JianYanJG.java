package qdh.jyhd;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/12.
 */

public class JianYanJG implements Serializable {
//<DC Name="Blood_name" Num="0">滤白悬浮红细胞</DC>
//      <DC Name="dosage" Num="0">1.00</DC>
//      <DC Name="unit" Num="0">U</DC>
//      <DC Name="pack_blood_type" Num="0">A</DC>
//      <DC Name="pack_rh_type" Num="0">阳(+)</DC>
//      <DC Name="" Num="0" />
//      <DC Name="" Num="0">542495</DC>
//      <DC Name="" Num="0">马守仁</DC>
//      <DC Name="" Num="0">男</DC>
//      <DC Name="" Num="0">80岁</DC>
//      <DC Name="" Num="0">WG0036</DC>
//      <DC Name="" Num="0">61~460</DC>
//      <DC Name="" Num="0">2019/3/5 19:22:52</DC>
//      <DC Name="" Num="0">2019/2/22 14:49:00</DC>
//      <DC Name="" Num="0">2019/3/29 14:49:00</DC>
//      <DC Name="" Num="0">A</DC>
//      <DC Name="" Num="0">阳(+)</DC>
//      <DC Name="" Num="0">相合</DC>
    public String Blood_name;
    public String dosage;
    public String unit;
    public String pack_blood_type;
    public String pack_rh_type;
    public String patient_id;
    public String mrn;
    public String patient_name;
    public String sex;
    public String age;
    public String bed_no;
    public String Ward_id;
    public String Grant_time;
    public String Make_time;
    public String Effective_time;
    public String patient_blood_type;
    public String rh_type;
    public String crossresult;


    public String getBlood_name() {
        return Blood_name == null ? "" : Blood_name;
    }

    public void setBlood_name(String blood_name) {
        Blood_name = blood_name;
    }

    public String getDosage() {
        return dosage == null ? "" : dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getUnit() {
        return unit == null ? "" : unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getPack_blood_type() {
        return pack_blood_type == null ? "" : pack_blood_type;
    }

    public void setPack_blood_type(String pack_blood_type) {
        this.pack_blood_type = pack_blood_type;
    }

    public String getPack_rh_type() {
        return pack_rh_type == null ? "" : pack_rh_type;
    }

    public void setPack_rh_type(String pack_rh_type) {
        this.pack_rh_type = pack_rh_type;
    }

    public String getPatient_id() {
        return patient_id == null ? "" : patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getMrn() {
        return mrn == null ? "" : mrn;
    }

    public void setMrn(String mrn) {
        this.mrn = mrn;
    }

    public String getPatient_name() {
        return patient_name == null ? "" : patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getSex() {
        return sex == null ? "" : sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age == null ? "" : age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBed_no() {
        return bed_no == null ? "" : bed_no;
    }

    public void setBed_no(String bed_no) {
        this.bed_no = bed_no;
    }

    public String getWard_id() {
        return Ward_id == null ? "" : Ward_id;
    }

    public void setWard_id(String ward_id) {
        Ward_id = ward_id;
    }

    public String getGrant_time() {
        return Grant_time == null ? "" : Grant_time;
    }

    public void setGrant_time(String grant_time) {
        Grant_time = grant_time;
    }

    public String getMake_time() {
        return Make_time == null ? "" : Make_time;
    }

    public void setMake_time(String make_time) {
        Make_time = make_time;
    }

    public String getEffective_time() {
        return Effective_time == null ? "" : Effective_time;
    }

    public void setEffective_time(String effective_time) {
        Effective_time = effective_time;
    }

    public String getPatient_blood_type() {
        return patient_blood_type == null ? "" : patient_blood_type;
    }

    public void setPatient_blood_type(String patient_blood_type) {
        this.patient_blood_type = patient_blood_type;
    }

    public String getRh_type() {
        return rh_type == null ? "" : rh_type;
    }

    public void setRh_type(String rh_type) {
        this.rh_type = rh_type;
    }

    public String getCrossresult() {
        return crossresult == null ? "" : crossresult;
    }

    public void setCrossresult(String crossresult) {
        this.crossresult = crossresult;
    }
}
