package com.laattre.backen.persistence.model;


import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A Product is an entity that represents an article for sale.
 * Products are associated with categories either directly or indirectly.
 * For instance, for a given child category B, if it's associated to a
 * parent category A, then the Product is associated with
 * category B (directly) and A (indirectly).
 *
 * @author dlm
 */
@Entity
@Table(name = "product")
public class Product {

//    public static final String CURRENCY = "EUR";
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    @Column(name = "product_code")
    private String productCode;
    
    @Column(name = "barcode", unique = true)
    private long barcode;
 
    @Column(name = "name", length = 255, nullable = false)
    private String name;
 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;
    
    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    @JsonIgnore
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    @JsonIgnore
    private User createdBy;
    
    @ManyToOne
    @JoinColumn(name = "user_owner", nullable = false, updatable = false)
    @JsonIgnore
    private User userOwner;
    
    @Column(name = "shipping_weight", nullable = false)
    private double shippingWeight;
    
    @Column(name = "list_price", nullable = false)
    private double listPrice;
    
    @Column(name = "our_price", nullable = false)
    private double ourPrice;
    
    @Column(name = "active", nullable = false)
    private boolean active = true;
	
    @Column(columnDefinition="text")
    private String description;
    
    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ProductToCartItem> productToCartItemList;
    
    @Column(name = "in_stock_number", nullable = false)
    private int inStockNumber;
    
    @Column(name = "sold_number", nullable = false)
    private int soldNumber;
    
    @Transient
    private MultipartFile productImage;
    
    @Column(name = "type")
    private String type;
    @Column(name = "color")
    private String color;
    @Column(name = "size")
    private String size;
    @Column(name = "brand")
    private String brand;
    @Column(name = "gender")
    private String gender;
    @Column(name = "fabric")
    private String fabric;
    @Column(name = "flash_sale")
    private boolean flashSale = false; //generate task to disable offer when sale end.
    @Column(name= "delivery_delay")
    private int deliveryDelay;
   
    /*@Column(name = "product_rating")
    private ProductRating productRating;*/
    
    @Column(name = "sale_type")
    private String saleType; //Stock Sale, Voucher Sale
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "formula")
    private String formula;
    @Column(name = "with_transport")
    private boolean withTransport = false;
    @Column(name = "sale_start_date")
    private Date saleStartDate;
    @Column(name = "sale_end_date")
    private Date saleEndDate;
    @Column(name = "check_in")
    private Date checkIn;
    @Column(name = "check_out")
    private Date checkOut;
    @Column(name = "cancellation_fees")
    private double cancellationFees;
    @Column(name = "max_occupants")
    private int maxOccupants;
    @Column(name = "location_latitude ")
    private String locationLatitude;
    @Column(name = "location_longitude")
    private String locationLongitude;
    

    
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public double getShippingWeight() {
        return shippingWeight;
    }

    public void setShippingWeight(double shippingWeight) {
        this.shippingWeight = shippingWeight;
    }

    public double getListPrice() {
        return listPrice;
    }

    public void setListPrice(double listPrice) {
        this.listPrice = listPrice;
    }

    public double getOurPrice() {
        return ourPrice;
    }

    public void setOurPrice(double ourPrice) {
        this.ourPrice = ourPrice;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductToCartItem> getProductToCartItemList() {
        return productToCartItemList;
    }

    public void setProductToCartItemList(List<ProductToCartItem> productToCartItemList) {
        this.productToCartItemList = productToCartItemList;
    }

    public int getInStockNumber() {
        return inStockNumber;
    }
    
    public void setInStockNumber(int inStockNumber) {
        this.inStockNumber = inStockNumber;
    }
    
    public int getSoldNumber() {
        return soldNumber;
    }

    public void setSoldNumber(int soldNumber) {
        this.soldNumber = soldNumber;
    }

    public MultipartFile getProductImage() {
        return productImage;
    }

    public void setProductImage(MultipartFile productImage) {
        this.productImage = productImage;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	public String getFabric() {
		return fabric;
	}

	public void setFabric(String fabric) {
		this.fabric = fabric;
	}

	public boolean isFlashSale() {
		return flashSale;
	}

	public void setFlashSale(boolean flashSale) {
		this.flashSale = flashSale;
	}

	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public boolean isWithTransport() {
		return withTransport;
	}

	public void setWithTransport(boolean withTransport) {
		this.withTransport = withTransport;
	}

	public Date getSaleStartDate() {
		return saleStartDate;
	}

	public void setSaleStartDate(Date saleStartDate) {
		this.saleStartDate = saleStartDate;
	}

	public Date getSaleEndDate() {
		return saleEndDate;
	}

	public void setSaleEndDate(Date saleEndDate) {
		this.saleEndDate = saleEndDate;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public double getCancellationFees() {
		return cancellationFees;
	}

	public void setCancellationFees(double cancellationFees) {
		this.cancellationFees = cancellationFees;
	}

	public int getMaxOccupants() {
		return maxOccupants;
	}

	public void setMaxOccupants(int maxOccupants) {
		this.maxOccupants = maxOccupants;
	}

	public String getLocationLatitude() {
		return locationLatitude;
	}

	public void setLocationLatitude(String locationLatitude) {
		this.locationLatitude = locationLatitude;
	}

	public String getLocationLongitude() {
		return locationLongitude;
	}

	public void setLocationLongitude(String locationLongitude) {
		this.locationLongitude = locationLongitude;
	}

	public int getDeliveryDelay() {
		return deliveryDelay;
	}

	public void setDeliveryDelay(int deliveryDelay) {
		this.deliveryDelay = deliveryDelay;
	}
}

