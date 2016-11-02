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
    private List<Person> personList;

	/**
	 * @return the personList
	 */
	public List<Person> getUserList() {
		if (personList == null) {
			personList = new ArrayList<Person>();
		}
		return this.personList;
	}

	/**
	 * @param personList the personList to set
	 */
	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
}
