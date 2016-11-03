/**
 * 
 */
package it.dontesta.liferay.symposium.jaxrsws.user.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author amusarra
 *
 */
@XmlRootElement
public class PersonList {

	@XmlElement
    private List<Person> persons;

	/**
	 * @return the persons
	 */
	public List<Person> getPersonList() {
		if (persons == null) {
			persons = new ArrayList<Person>();
		}
		return this.persons;
	}

	/**
	 * @param persons the persons to set
	 */
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}
