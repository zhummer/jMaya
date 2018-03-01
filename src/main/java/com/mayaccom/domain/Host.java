package com.mayaccom.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Host.
 */
@Entity
@Table(name = "host")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "host")
public class Host implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "first_contact_name")
    private String firstContactName;

    @Column(name = "last_contact_name")
    private String lastContactName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "phone_1")
    private String phone1;

    @Column(name = "phone_2")
    private String phone2;

    @Column(name = "phone_3")
    private String phone3;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "jhi_password")
    private String password;

    @OneToMany(mappedBy = "host")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Accomodation> accomodations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Host name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstContactName() {
        return firstContactName;
    }

    public Host firstContactName(String firstContactName) {
        this.firstContactName = firstContactName;
        return this;
    }

    public void setFirstContactName(String firstContactName) {
        this.firstContactName = firstContactName;
    }

    public String getLastContactName() {
        return lastContactName;
    }

    public Host lastContactName(String lastContactName) {
        this.lastContactName = lastContactName;
        return this;
    }

    public void setLastContactName(String lastContactName) {
        this.lastContactName = lastContactName;
    }

    public String getAddress() {
        return address;
    }

    public Host address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Host city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone1() {
        return phone1;
    }

    public Host phone1(String phone1) {
        this.phone1 = phone1;
        return this;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public Host phone2(String phone2) {
        this.phone2 = phone2;
        return this;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getPhone3() {
        return phone3;
    }

    public Host phone3(String phone3) {
        this.phone3 = phone3;
        return this;
    }

    public void setPhone3(String phone3) {
        this.phone3 = phone3;
    }

    public String getEmail() {
        return email;
    }

    public Host email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public Host username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public Host password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Accomodation> getAccomodations() {
        return accomodations;
    }

    public Host accomodations(Set<Accomodation> accomodations) {
        this.accomodations = accomodations;
        return this;
    }

    public Host addAccomodation(Accomodation accomodation) {
        this.accomodations.add(accomodation);
        accomodation.setHost(this);
        return this;
    }

    public Host removeAccomodation(Accomodation accomodation) {
        this.accomodations.remove(accomodation);
        accomodation.setHost(null);
        return this;
    }

    public void setAccomodations(Set<Accomodation> accomodations) {
        this.accomodations = accomodations;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Host host = (Host) o;
        if (host.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), host.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Host{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", firstContactName='" + getFirstContactName() + "'" +
            ", lastContactName='" + getLastContactName() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", phone1='" + getPhone1() + "'" +
            ", phone2='" + getPhone2() + "'" +
            ", phone3='" + getPhone3() + "'" +
            ", email='" + getEmail() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
