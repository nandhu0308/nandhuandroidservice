package com.limitless.services.engage.dao;

import java.util.Comparator;

import com.limitless.services.engage.CustomerCoordsBean;

class DistanceFromMeComparator implements Comparator<EngageSeller> {
	CustomerCoordsBean me;

	public DistanceFromMeComparator(CustomerCoordsBean me) {
		this.me = me;
	}

	private Double distanceFromMe(EngageSeller seller) {
		double theta = seller.getSellerLocationLongitude() - me.getLongitude();
		double dist = Math.sin(deg2rad(seller.getSellerLocationLatitude())) * Math.sin(deg2rad(me.getLatitude()))
				+ Math.cos(deg2rad(seller.getSellerLocationLatitude())) * Math.cos(deg2rad(me.getLatitude()))
						* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		return dist;
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public int compare(EngageSeller o1, EngageSeller o2) {
		return distanceFromMe(o1).compareTo(distanceFromMe(o2));
	}
}
