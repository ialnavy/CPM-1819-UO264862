package logic;

/**
 * 'Article' class. Article class performs the abstract concept of the product
 * sold in our application. Here the parameters are checked when created, being
 * established every one as an attribute significantly named.
 * 
 * @author Iván Álvarez López - 71741444M
 * @version January 2019
 *
 */
public class Article {

	private String code; // 'null' code means it's bad stated in the file.
	private String type;
	private String name;
	private String description;
	private float priceUd;
	private float priceGr;

	/**
	 * Article class constructor.
	 * 
	 * @param code
	 *            Code attribute.
	 * @param type
	 *            Type attribute.
	 * @param name
	 *            Name attribute
	 * @param description
	 *            Description attribute.
	 * @param priceUd
	 *            Unit price attribute.
	 * @param priceGr
	 *            Group price attribute.
	 */
	public Article(String code, String type, String name, String description, float priceUd, float priceGr) {
		this.setCode(code);
		this.setType(type);
		this.setName(name);
		this.setDescription(description);
		this.setPriceUd(priceUd);
		this.setPriceGr(priceGr);
	}

	// ##############
	// Setter methods
	// ##############

	/**
	 * 'setCode(String code)' setter method. Verifies the code is compounded by two
	 * letters, followed by four numbers.
	 * 
	 * @param code
	 *            Code attribute.
	 */
	private void setCode(String code) {
		if (code.length() != 5)
			this.code = null;
		else
			this.code = code;
	}

	/**
	 * 'setType(String type)' setter method. Rewrites the parameter, for formal
	 * aspects.
	 * 
	 * @param type
	 *            Type attribute.
	 */
	private void setType(String type) {
		String[] typeSplit = type.split("");
		StringBuilder localType = new StringBuilder().append(typeSplit[0].toUpperCase());
		for (int i = 1; i < typeSplit.length; i++) {
			localType.append(typeSplit[i].toLowerCase());
		}
		this.type = localType.toString();
	}

	/**
	 * 'setName(String name)' setter method. Nothing is checked, for now.
	 * 
	 * @param name
	 *            Name attribute.
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * 'setDescription(String name)' setter method. Nothing is checked, for now.
	 * 
	 * @param description
	 *            Description attribute.
	 */
	private void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 'setPriceUd(float priceUd)' setter method. Must be zero or positive.
	 * 
	 * @param priceUd
	 *            Unit price attribute.
	 */
	private void setPriceUd(float priceUd) {
		if (priceUd >= 0)
			this.priceUd = priceUd;
		else
			this.priceUd = 0;
	}

	/**
	 * 'setPriceGr(float priceGr)' setter method. Must be zero or positive.
	 * 
	 * @param priceGr
	 *            Group price attribute.
	 */
	private void setPriceGr(float priceGr) {
		if (priceGr >= 0)
			this.priceGr = priceGr;
		else
			this.priceGr = 0;
	}

	// ##############
	// Getter methods
	// ##############

	/**
	 * 'getCode()' getter method.
	 * 
	 * @return Code attribute.
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 'getType()' getter method.
	 * 
	 * @return Type attribute.
	 */
	public String getType() {
		return type;
	}

	/**
	 * 'getName()' getter method.
	 * 
	 * @return Name attribute.
	 */
	public String getName() {
		return name;
	}

	/**
	 * 'getDescription()' getter method.
	 * 
	 * @return Description attribute.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 'getPriceUd()' getter method.
	 * 
	 * @return Unit price attribute.
	 */
	public float getPriceUd() {
		return priceUd;
	}

	/**
	 * 'getPriceGr()' getter method.
	 * 
	 * @return Group price attribute.
	 */
	public float getPriceGr() {
		return priceGr;
	}

}
