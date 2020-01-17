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
 
    @Column(name = "name", length = 255, nullable = false)
    private String name;
 
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "Create_Date", nullable = false)
    private Date createDate;
    
    @ManyToMany
    @JoinTable(name = "product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;
    
    @ManyToOne
    @JoinColumn(name = "user_owner", nullable = false, updatable = false)
    private User userOwner;
    
    @Column(name = "shipping_weight", nullable = false)
    private double shippingWeight;
    
    @Column(name = "list_price", nullable = false)
    private double listPrice;
    
    @Column(name = "our_price", nullable = false)
    private double ourPrice;
    
    @Column(name = "active", nullable = false)
    private boolean active=true;
	
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
    private String sise;
    @Column(name = "brand")
    private String brand;
    @Column(name = "gender")
    private String gender;

    
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

    public String getSise() {
        return sise;
    }

    public void setSise(String sise) {
        this.sise = sise;
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
    
}

