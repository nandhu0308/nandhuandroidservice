package com.limitless.services.engage.entertainment;

import java.util.ArrayList;
import java.util.List;

public class ChannelFilterResponseBean {
	private List<FilterResponseBean> categories;
	private List<FilterResponseBean> languages;
	private List<FilterResponseBean> broadcasters;

	public ChannelFilterResponseBean() {
		categories = new ArrayList<FilterResponseBean>();
		languages = new ArrayList<FilterResponseBean>();
		broadcasters = new ArrayList<FilterResponseBean>();
	}

	public List<FilterResponseBean> getCategories() {
		return categories;
	}

	public void setCategories(List<FilterResponseBean> categories) {
		this.categories = categories;
	}

	public List<FilterResponseBean> getLanguages() {
		return languages;
	}

	public void setLanguages(List<FilterResponseBean> languages) {
		this.languages = languages;
	}

	public List<FilterResponseBean> getBroadcasters() {
		return broadcasters;
	}

	public void setBroadcasters(List<FilterResponseBean> broadcasters) {
		this.broadcasters = broadcasters;
	}

}
