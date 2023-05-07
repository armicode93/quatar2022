package com.example.qatar2022.entities;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

@Entity
@Data
@Table(name="partie")
public class Partie implements Serializable {



    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long idPartie;

    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(cascade = CascadeType.PERSIST)//(cascade = {CascadeType.ALL})

    private Equipe eq1;


   // @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(
            cascade = CascadeType.PERSIST)//(cascade = {CascadeType.ALL})

    private Equipe  eq2;

   // @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="stade_id_stade")
    private Stade stade;


    //@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="tour_id_tour" )
    private Tour tour ;

    @Nullable
    private Integer scoreEq1 ;

    @Nullable
    private Integer scoreEq2 ;

@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime dateTime;



    private String arbitre_principal;

    private String totalTime;

    private String prolongation;

    private Double prix;

    public Partie(Equipe eq1, Equipe eq2, Stade stade, Tour tour, int scoreEq1, int scoreEq2, LocalDateTime dateTime, String arbitre_principal, String totalTime, String prolongation, Double prix) {
        this.eq1 = eq1;
        this.eq2 = eq2;
        this.stade = stade;
        this.tour = tour;
        this.scoreEq1 = scoreEq1;
        this.scoreEq2 = scoreEq2;
        this.dateTime = dateTime;
        this.arbitre_principal = arbitre_principal;
        this.totalTime = totalTime;
        this.prolongation = prolongation;
        this.prix = prix;
    }

    public Partie(Long idPartie, Integer scoreEq1,  Integer scoreEq2) {
        this.idPartie = idPartie;
        this.scoreEq1 = scoreEq1;
        this.scoreEq2 = scoreEq2;
    }


    public Partie() {

    }
    public Partie(Equipe eq1, Equipe eq2, Stade stade, Tour tour, LocalDateTime dateTime, String arbitre_principal, String totalTime, String prolongation, Double prix) {
        this.eq1 = eq1;
        this.eq2 = eq2;
        this.stade = stade;
        this.tour = tour;

        this.dateTime = dateTime;
        this.arbitre_principal = arbitre_principal;
        this.totalTime = totalTime;
        this.prolongation = prolongation;
        this.prix = prix;
    }

    public Integer getScoreEq1() {
        return scoreEq1;
    }

    public void setScoreEq1(Integer scoreEq1) {
        this.scoreEq1 = scoreEq1;
    }

    public Integer getScoreEq2() {
        return scoreEq2;
    }

    public void setScoreEq2(Integer scoreEq2) {
        this.scoreEq2 = scoreEq2;
    }

    public Long getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(Long idPartie) {
        this.idPartie = idPartie;
    }

    public Equipe getEq1() {
        return eq1;
    }

    public void setEq1(Equipe eq1 ) {
        this.eq1 = eq1;
    }

    public Equipe getEq2() {
        return eq2;
    }

    public void setEq2(Equipe eq2) {
        this.eq2 = eq2;
    }


    public Stade getStade() {
        return stade;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }



    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getArbitre_principal() {
        return arbitre_principal;
    }

    public void setArbitre_principal(String arbitre_principal) {
        this.arbitre_principal = arbitre_principal;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getProlongation() {
        return prolongation;
    }

    public void setProlongation(String prolongation) {
        this.prolongation = prolongation;
    }

    public Double getPrix() {

        return prix.doubleValue();
    }

    public Equipe getVincitore() {
        if (scoreEq1 > scoreEq2) {
            return eq1;
        } else if (scoreEq2 > scoreEq1) {
            return eq2;
        }
        return null;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Partie{" +
                "eq1=" + eq1 +
                ", eq2=" + eq2 +
                ", stade=" + stade +
                ", tour=" + tour +
                ", scoreEq1=" + scoreEq1 +
                ", scoreEq2=" + scoreEq2 +
                ", dateTime=" + dateTime +
                ", arbitre_principal='" + arbitre_principal + '\'' +
                ", totalTime='" + totalTime + '\'' +
                ", prolongation='" + prolongation + '\'' +
                ", prix=" + prix +
                '}';
    }





}
