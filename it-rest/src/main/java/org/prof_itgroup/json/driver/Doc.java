package org.prof_itgroup.json.driver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "FIO",
        "Date of birth",
        "Birthplace",
        "Passport (series, number)",
        "Date of issue of the passport",
        "Who issued the passport",
        "The address on a registration",
        "Fact. address",
        "Number of waters. certificates",
        "Date of issue of waters. certificates",
        "Status"
  /*      "Scan copy of waters. certificates",
        "Scan copy of the passport"*/
})
public class Doc {

    @JsonProperty("id")
    private Long driverId;
    @JsonProperty("FIO")
    private String fIO;
    @JsonProperty("Date of birth")
    private String date_of_birth;
    @JsonProperty("Birthplace")
    private String birthplace;
    @JsonProperty("Passport (series, number)")
    private Integer passport_ser_numb;
    @JsonProperty("Date of issue of the passport")
    private String date_of_issue_passport;
    @JsonProperty("Who issued the passport")
    private String who_issued_passport;
    @JsonProperty("The address on a registration")
    private String adress_reg;
    @JsonProperty("Fact. address")
    private String fact_address;
    @JsonProperty("Number of waters. certificates")
    private Integer number_wat_certificates;
    @JsonProperty("Date of issue of waters. certificates")
    private String date_issue_wat_certificates;
    @JsonProperty("Status")
    private String status;
  /*  @JsonProperty("Scan copy of waters. certificates")
    private String scan_copy_wat_certificates;
    @JsonProperty("Scan copy of the passport")
    private  String scan_copy_passport;*/

    @JsonProperty("id")
    public Long getDriverId() {
        return driverId;
    }

    @JsonProperty("id")
    public void setId (Long driverId) {
        this.driverId=driverId;
    }

    @JsonProperty("FIO")
    public String getfIO() {
        return fIO;
    }

    @JsonProperty("FIO")
    public void setfIO (String fIO) {
        this.fIO=fIO;
    }

    @JsonProperty("Date of birth")
    public  String getDate_of_birt() {
        return date_of_birth;
    }

    @JsonProperty("Date of birth")
    public void setDate_of_birth (String date_of_birth) {
        this.date_of_birth=date_of_birth;
    }

    @JsonProperty("Birthplace")
    public String getBirthplace() {
        return birthplace;
    }

    @JsonProperty("Birthplace")
    public void setBirthplace (String birthplace) {
        this.birthplace=birthplace;
    }

    @JsonProperty("Passport (series, number)")
    public Integer getPassport_ser_numb() {
        return passport_ser_numb;
    }

    @JsonProperty("Passport (series, number)")
    public void setPassport_ser_numb (Integer passport_ser_numb) {
        this.passport_ser_numb=passport_ser_numb;
    }

    @JsonProperty("Date of issue of the passport")
    public String getDate_of_issue_passport() {
        return date_of_issue_passport;
    }

    @JsonProperty("Date of issue of the passport")
    public void setDate_of_issue_passport (String date_of_issue_passport) {
        this.date_of_issue_passport=date_of_issue_passport;
    }

    @JsonProperty("Who issued the passport")
    public String getWho_issued_passport() {
        return who_issued_passport;
    }

    @JsonProperty("Who issued the passport")
    public void setWho_issued_passport (String who_issued_passport) {
        this.who_issued_passport=who_issued_passport;
    }

    @JsonProperty("The address on a registration")
    public String getAdress_reg() {
        return adress_reg;
    }

    @JsonProperty("The address on a registration")
    public void setAdress_reg (String adress_reg) {
        this.adress_reg=adress_reg;
    }

    @JsonProperty("Fact. address")
    public String getFact_address() {
        return fact_address;
    }

    @JsonProperty("Fact. address")
    public void setFact_address (String fact_address) {
        this.fact_address=fact_address;
    }

    @JsonProperty("Number of waters. certificates")
    public Integer getNumber_wat_certificates() {
        return number_wat_certificates;
    }

    @JsonProperty("Number of waters. certificates")
    public void setNumber_wat_certificates (Integer number_wat_certificates) {
        this.number_wat_certificates=number_wat_certificates;
    }

    @JsonProperty("Date of issue of waters. certificates")
    public String getDate_issue_wat_certificates() {
        return date_issue_wat_certificates;
    }

    @JsonProperty("Date of issue of waters. certificates")
    public void setDate_issue_wat_certificates (String date_issue_wat_certificates) {
        this.date_issue_wat_certificates=date_issue_wat_certificates;
    }

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus (String status) {
        this.status=status;
    }
}
