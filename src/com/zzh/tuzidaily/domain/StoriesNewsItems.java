package com.zzh.tuzidaily.domain;

/**
 * 知乎日报对应的内容
 * 
 * "stories":[{"images":[
 * "http:\/\/pic1.zhimg.com\/d77edc0caab0b042ca62cf95cf011a70.jpg"], "type":0,
 * "id":8118244, "ga_prefix":"041415", "title":"你经常吃的这种贝，竟然是躲过五次大灭绝的活化石" }
 * 
 * @author Administrator
 * 
 */
public class StoriesNewsItems {
	// 图片
	String images;
	int type;
	int id;
	String ga_prefix;
	String title;

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGa_prefix() {
		return ga_prefix;
	}

	public void setGa_prefix(String ga_prefix) {
		this.ga_prefix = ga_prefix;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
