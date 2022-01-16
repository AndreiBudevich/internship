package com.game.entity;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
@Entity
@Table(name = "player")
public class Player implements Serializable {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "name")
    public String name;
    @Column(name = "title")
    public String title;
    @Column(name = "race")
    public Race race;
    @Column(name = "profession")
    public Profession profession;
    @Column(name = "birthday")
    public Long birthday;
    @Column(name = "banned")
    public Boolean banned;
    @Column(name = "experience")
    public Integer experience;
    @Column(name = "level")
    public Integer level;
    @Column(name = "untilNextLevel")
    public Integer untilNextLevel;

    public Player() {
    }

    public Player(Long id, String name, String title, Race race, Profession profession, Long birthday,
                          Boolean banned, Integer experience, Integer level, Integer untilNextLevel) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.experience = experience;
        this.level = level;
        this.untilNextLevel = untilNextLevel;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Long getBirthday() {
        return birthday;
    }

    public void setBirthday(Long birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player that = (Player) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(title, that.title) &&
                race == that.race &&
                profession == that.profession &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(banned, that.banned) &&
                Objects.equals(experience, that.experience) &&
                Objects.equals(level, that.level) &&
                Objects.equals(untilNextLevel, that.untilNextLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title, race, profession, birthday, banned, experience, level, untilNextLevel);
    }

    @Override
    public String toString() {
        LocalDate birthday = Instant.ofEpochMilli(this.birthday).atZone(ZoneId.systemDefault()).toLocalDate();
        return "PlayerInfoTest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                '}';
    }

}