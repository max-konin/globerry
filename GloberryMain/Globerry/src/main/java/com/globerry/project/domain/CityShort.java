/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.globerry.project.domain;

import javax.persistence.*;

/**
 *
 * @author signal
 */
/*@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="isShort",
		discriminatorType= DiscriminatorType.INTEGER
)
@Table(name = "City")
@DiscriminatorValue(value="1")*/
@Embeddable
public class CityShort implements Cachable<CityShort> {

        @Transient
	private int id;
//	@Column(name = "name")
	private String name;
//	@Column(name = "ru_name")
	private String ru_name;
//	@Column(name = "latitude")
	private float latitude;
//	@Column(name = "longitude")
	private float longitude;
//	@Transient
	private float weight;
        @Transient
        private String countryName;
       
	
	public CityShort() {
		
	}
	
	public CityShort(String name, String ru_name, float latitude, float longitude, float weight, int id, String countryName) {
                this.countryName = countryName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.weight = weight;
		this.name = name;
		this.ru_name = ru_name;
		this.id = id;
	}
	
	@Override
	public CityShort fromJSON(String json) {
		String[] feilds = json.replaceAll("[{}]|\"[a-z]*\":", "").split(",");
		this.latitude = Float.parseFloat(feilds[0]);
		this.longitude = Float.parseFloat(feilds[1]);
		this.ru_name = feilds[3];
		this.name = feilds[4];
		this.weight = Float.parseFloat(feilds[5]);
                this.countryName = feilds[6];
		return this;
	}

	@Override
	public String toJSON() {
           
            StringBuilder str = new StringBuilder();
            str.append("{\"latitude\":");
            str.append(this.latitude);
            //str.append(",");
            str.append(",\"longitude\":");
            str.append(this.longitude);
            //str.append(",");
            str.append(",\"ru_name\":");
            str.append(this.ru_name);
            //str.append(",");
            str.append(",\"name\":");
            str.append(this.name);
            //str.append(",");
            str.append(",\"weight\":");
            str.append(this.weight);
            str.append(",\"countryName\":");
            str.append(this.countryName);
            str.append("}");
            return  str.toString();
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRu_name() {
		return ru_name;
	}

	public void setRu_name(String ru_name) {
		this.ru_name = ru_name;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    /**
     * @return the countryName
     */
    public String getCountryName()
    {
        return countryName;
    }

    /**
     * @param countryName the countryName to set
     */
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }
}
