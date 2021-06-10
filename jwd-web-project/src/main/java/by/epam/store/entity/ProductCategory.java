package by.epam.store.entity;

import java.io.Serializable;

/**
 * Describes the entity ProductCategory
 * 
 * @author Mariya Kuksar
 */
public class ProductCategory implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long categoryId;
	private String categoryName;
	private String imageName;

	/**
	 * Constructs a new ProductCategory
	 */
	public ProductCategory() {
	}

	/**
	 * Constructs a new ProductCategory with the specified category id
	 * 
	 * @param categoryId {@link Long} category id
	 */
	public ProductCategory(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Constructs a new ProductCategory with the specified category id and category
	 * name
	 * 
	 * @param categoryId   {@link Long} category id
	 * @param categoryName {@link String} category name
	 */
	public ProductCategory(Long categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String pictureName) {
		this.imageName = pictureName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((categoryId == null) ? 0 : categoryId.hashCode());
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((imageName == null) ? 0 : imageName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductCategory other = (ProductCategory) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (imageName == null) {
			if (other.imageName != null)
				return false;
		} else if (!imageName.equals(other.imageName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ProductCategory [categoryId=");
		builder.append(categoryId);
		builder.append(", categoryName=");
		builder.append(categoryName);
		builder.append(", imageName=");
		builder.append(imageName);
		builder.append("]");
		return builder.toString();
	}
}