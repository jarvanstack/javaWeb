package com.mbft.smbms.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Bill {
	private Integer id;   //id 
	private String billCode; //账单编码 
	private String productName; //商品名称 
	private String productDesc; //商品描述 
	private String productUnit; //商品单位
	private BigDecimal productCount; //商品数量 
	private BigDecimal totalPrice; //总金额
	private Integer isPayment; //是否支付 
	private Integer providerId; //供应商ID 
	private Integer createdBy; //创建者
	private Date creationDate; //创建时间
	private Integer modifyBy; //更新者
	private Date modifyDate;//更新时间 12
	
	private String providerName;//供应商名称
	private void init(){
		//初始化必要值
		billCode = "";
		productName = "";
		productDesc = "";
		productUnit = "";
		billCode = "";
		productCount = new BigDecimal("0");
		totalPrice = new BigDecimal("0");
		isPayment = 0;//1默认未支付
		providerId = 0;
		createdBy = 0;
		creationDate = new Date();
		modifyBy = 0;
		modifyDate = new Date();
		providerName = "";
	}
	public Bill() {
		init();
	}

	public String getProviderName() {
		//new ProviderServiceImpl().getProviderById(""+id).getProName()
		//不要在pojo里调用Service层，
		//这个值在Service层调用DAO层来实现
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getBillCode() {
		return billCode;
	}
	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public BigDecimal getProductCount() {
		return productCount;
	}
	public void setProductCount(BigDecimal productCount) {
		this.productCount = productCount;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Integer getIsPayment() {
		return isPayment;
	}
	public void setIsPayment(Integer isPayment) {
		this.isPayment = isPayment;
	}
	
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Integer getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Integer modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
	
}
